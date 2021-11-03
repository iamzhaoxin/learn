import json
from ..items import *

# TODO 修改id和name
id = 3201
name = '南京博物院'


class M3201(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '3201'
    start_urls = ['http://www.njmuseum.com/zh']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        for j in range(1, 115):
            col_url = 'http://www.njmuseum.com/api/collection/select'
            my_data = {
                'pageNum': str(j),
                'pageSize': '12',
                'category': '',
                'dynasty': '',
                'searchText': '',
                'exhibitionHall': '',
            }
            col_id += 100
            yield scrapy.FormRequest(url=col_url, formdata=my_data, callback=self.col_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页
        exh_id += 1000
        _my_data = {
            'modular': 'exIndex',
            'code': '',
            'value': '0',
            'pageSize': '6',
            'pageNum': '1',
            'isList': 'true',
        }
        exh_url = 'http://www.njmuseum.com/api/exhibition/list'
        yield scrapy.FormRequest(url=exh_url, formdata=_my_data, callback=self.exh_parse, meta={'exh_id': exh_id})

    # 藏品详情页
    def col_parse(self, response):
        col_id = response.meta['col_id']
        cols = json.loads(response.body_as_unicode())['data']['list']
        for col in cols:
            item = Item()
            item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
            item['mus_name'] = name
            item['mus_id'] = id
            item['col_id'] = col_id
            col_id += 1
            # print(response)

            # TODO 数据分析、持久化存储
            item['col_name'] = col['title']
            item['col_info'] = col['describe']
            item['col_era'] = col['categoryName']
            item['col_picture'] = "http://www.njmuseum.com" + col['imgSrc'][0]
            yield item
            # print(item['col_name']+"  "+item['col_info']+"   "+item['col_picture']+"   "+item['col_era'])
            print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览详情页
    def exh_parse(self, response):
        exh_id = response.meta['exh_id']
        exhs=json.loads(response.body_as_unicode())['data']['list']
        for exh in exhs:
            item = Item()
            item['col_id'] = item['col_name'] = item['col_info'] = item['col_era'] = item['col_picture'] = ''
            item['mus_name'] = name
            item['mus_id'] = id
            item['exh_id'] = exh_id
            exh_id += 1
            # print(response)

            # TODO 解析展览数据并持久化存储
            item['exh_name'] = exh['title']
            item['exh_info'] =exh['describe']
            item['exh_picture'] ="http://www.njmuseum.com/"+exh['imgSrc'][0]
            item['exh_time'] =exh['timedesc']
            yield item
            # print(item['exh_picture'] + item['exh_name'] + item['exh_info'] + item['exh_time'])
            print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
