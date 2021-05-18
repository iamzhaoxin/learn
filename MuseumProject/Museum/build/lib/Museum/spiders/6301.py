import json
from ..items import *

# TODO 修改id和name
id = 6301
name = '青海省博物馆'


class M6301(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '6301'
    start_urls = ['http://www.qhmuseum.cn/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        for j in range(1, 5):
            col_url = 'http://www.qhmuseum.cn/qhm-webapi/api/v1/collection/getTextureAll?pageNumber=' + str(j) + "&pageSize=10&texture=104"
            col_id += 1000
            yield scrapy.Request(url=col_url, callback=self.col_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页
        exh_url = 'http://www.qhmuseum.cn/qhm-webapi/api/v1/permanent/permanentAll?pageNumber=1&pageSize=10'
        yield scrapy.Request(url=exh_url, callback=self.exhs_parse, meta={'exh_id': exh_id})

    # 藏品详情页
    def col_parse(self, response):
        col_lists = json.loads(response.body_as_unicode())['data']['list']
        col_id=response.meta['col_id']
        for col in col_lists:
            item = Item()
            item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
            item['mus_name'] = name
            item['mus_id'] = id
            item['col_id'] = col_id
            col_id+=1
            # print(response)

            # TODO 数据分析、持久化存储
            item['col_name'] = col['collectionname']
            item['col_info'] = col['collectiondescribe']
            item['col_era'] = col['category']
            item['col_picture'] = col['collectionimages']
            yield item
            # print(item['col_name']+"  "+item['col_info']+"   "+item['col_picture']+"   "+item['col_era'])
            print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览列表
    def exhs_parse(self, response):
        # TODO 解析展览详情页网址
        url_list = json.loads(response.body_as_unicode())['data']['list']
        for url in url_list:
            response.meta['exh_id'] += 1
            url = "http://www.qhmuseum.cn/qhm-webapi/api/v1/permanent/permanentDetails?id=" + str(url['id'])
            # print(url)
            yield scrapy.Request(url=url, callback=self.exh_parse, meta={'exh_id': response.meta['exh_id']})
        return

    # 展览详情页
    def exh_parse(self, response):
        exh=json.loads(response.body_as_unicode())['data']
        item = Item()
        item['col_id'] = item['col_name'] = item['col_info'] = item['col_era'] = item['col_picture'] = ''
        item['mus_name'] = name
        item['mus_id'] = id
        item['exh_id'] = response.meta['exh_id']
        # print(response)

        # TODO 解析展览数据并持久化存储
        item['exh_name'] = exh['title']
        item['exh_info'] = item['exh_name']
        item['exh_picture'] = '无'
        item['exh_time'] = '常设'
        yield item
        # print(item['exh_picture'] + item['exh_name'] + item['exh_info'] + item['exh_time'])
        print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
