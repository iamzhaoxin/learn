import json
from ..items import *

# TODO 修改id和name
id = 4601
name = '海南省博物馆'


class M4601(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '4601'
    start_urls = ['http://www.hainanmuseum.org/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        i = 0
        for j in range(1, 1814):
            col_url = 'http://www.hainanmuseum.org/hnbwgcms/hnswb/api/cultural/list/api?pagesize=12&start=' + str(
                i) + "&keyword="
            i += 12
            col_id += 20
            yield scrapy.Request(url=col_url, callback=self.col_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页
        exh_url = 'http://www.hainanmuseum.org/hnbwgcms/node/250'
        yield scrapy.Request(url=exh_url, callback=self.exhs_parse, meta={'exh_id': exh_id})

    # 藏品详情页
    def col_parse(self, response):
        cols = json.loads(response.body_as_unicode())['data']['data']
        col_id = response.meta['col_id']
        for col in cols:
            item = Item()
            item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
            item['mus_name'] = name
            item['mus_id'] = id
            item['col_id'] = col_id
            col_id += 1
            # print(response)

            # TODO 数据分析、持久化存储
            item['col_name'] = col['mingchen']
            item['col_info'] = col['zhidi']
            item['col_era'] = col['niandai']
            item['col_picture'] = "http://www.hainanmuseum.org/cms/1/image/public/wenwu/" + col['pics'][0] + ".png"
            yield item
            # print(item['col_name']+"  "+item['col_info']+"   "+item['col_picture']+"   "+item['col_era'])
            print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览列表
    def exhs_parse(self, response):
        # TODO 解析展览详情页网址
        url_list = list(set(response.xpath('//ul[@class="newsList"]//@href').extract()))
        for url in url_list:
            response.meta['exh_id'] += 1
            url = "http://www.hainanmuseum.org" + url
            # print(url)
            yield scrapy.Request(url=url, callback=self.exh_parse, meta={'exh_id': response.meta['exh_id']})
        return

    # 展览详情页
    def exh_parse(self, response):
        item = Item()
        item['col_id'] = item['col_name'] = item['col_info'] = item['col_era'] = item['col_picture'] = ''
        item['mus_name'] = name
        item['mus_id'] = id
        item['exh_id'] = response.meta['exh_id']
        # print(response)

        # TODO 解析展览数据并持久化存储
        item['exh_name'] = response.xpath('//div[@class="f-w-full"]//*[@class="f-left title"]//text()')[
            0].extract().strip()
        item['exh_info'] = response.xpath('//div[@class="public-info-word"]')[0].xpath('string(.)')[0].extract().strip()
        # item['exh_picture'] = "http://www.hainanmuseum.org/"+response.xpath('/html/body/div[1]//div[@class="public-info-word"]//img/@src')[0].extract().strip()
        item['exh_picture']='无法加载'
        item['exh_time'] = '常设'
        yield item
        # print(item['exh_picture'] + item['exh_name'] + item['exh_info'] + item['exh_time'])
        print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
