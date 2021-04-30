import json
from ..items import *

# TODO 修改id和name
id = 1301
name = '河北博物院'


class M1301(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '1301'
    start_urls = ['http://www.hebeimuseum.org.cn/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        for j in range(1,9):
            col_url = 'http://api.bwy.hbdjdz.com:9903//primaryCollection/list4Page?id='+str(j)+'&relicsType='+str(j)+'&pageNum='
            for i in range(1, 9):
                _col_url = col_url + str(i)
                col_id += 1000
                yield scrapy.Request(url=_col_url, callback=self.cols_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页
        exh_urls = ['http://www.hebeimuseum.org.cn/channels/12.html']
        for exh_url in exh_urls:
            exh_id += 1000
            yield scrapy.Request(url=exh_url, callback=self.exhs_parse, meta={'exh_id': exh_id})

    # TODO 进入藏品详情页
    def cols_parse(self, response):
        col_datas = json.loads(response.body_as_unicode())['data']
        for col_data in col_datas:
            response.meta['col_id'] += 1
            col_url = 'http://api.bwy.hbdjdz.com:9903//primaryCollection/queryOneById?id=' + str(col_data['id'])
            yield scrapy.Request(url=col_url, callback=self.col_parse, meta={'col_id': response.meta['col_id']})
        return

    # 藏品详情页
    def col_parse(self, response):
        item = Item()
        item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
        item['mus_name'] = name
        item['mus_id'] = id
        item['col_id'] = response.meta['col_id']
        # print(response)

        # TODO 数据分析、持久化存储
        col = json.loads(response.body_as_unicode())
        item['col_name'] = col['name']
        item['col_info'] = col['introduce']
        item['col_era'] = col['age']
        item['col_picture'] = col['imgPath']
        yield item
        # print(item['col_name']+item['col_info']+item['col_picture'])
        print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览列表
    def exhs_parse(self, response):
        # TODO 解析展览详情页网址
        url_list = response.xpath('//div[@id="content"]//div[@class="list"]/ul//@href').extract()
        for url in url_list:
            response.meta['exh_id'] += 1
            url = "http://www.hebeimuseum.org.cn" + url
            # print(url)
            yield scrapy.Request(url=url, callback=self.exh_parse, meta={'exh_id': response.meta['exh_id']})
        return

    def exh_parse(self, response):
        item = Item()
        item['col_id'] = item['col_name'] = item['col_info'] = item['col_era'] = item['col_picture'] = ''
        item['mus_name'] = name
        item['mus_id'] = id
        item['exh_id'] = response.meta['exh_id']
        # TODO 解析展览数据并持久化存储
        item['exh_name'] = response.xpath('//*[@id="content"]/div[2]/div[3]/div[2]/h3/text()')[0].extract()
        item['exh_info'] = response.xpath('//*[@id="content"]/div[2]/div[3]/div[2]//div[@class="text"]')[0].xpath('string(.)')[0].extract().strip()
        item['exh_picture'] = "http://www.hebeimuseum.org.cn" + response.xpath('//*[@id="focus"]/ul/li[1]/img/@src')[0].extract().strip()
        item['exh_time'] = '常设'
        yield item
        print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return