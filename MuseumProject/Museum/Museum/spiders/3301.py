import json

import scrapy

from ..items import *

# TODO 修改id和name
id = 3301
name = '浙江省博物馆'


class M3301(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '3301'
    start_urls = ['http://www.zhejiangmuseum.com/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        col_url = 'http://www.zhejiangmuseum.com/japi/sw-cms-zb/api/queryExhibitList'
        for j in range(1, 300):
            col_id += 100
            my_data = {
                "entity": {
                    "exhibitName": "",
                    "generalType": "",
                    "languageType": "CN",
                    "isOwn": 'true',
                    "portalId": "2341ijij234q_zb_portal"
                },
                "param": {
                    "pageSize": 18,
                    "pageNum": j
                }
            }
            # TODO 返回数据不正常，报错500 Internal Server Error
            yield scrapy.FormRequest(url=col_url, formdata=my_data, callback=self.cols_parse,
                                     meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页
        for j in range(1, 4):
            exh_id += 1000
            exh_url = 'http://www.luxunmuseum.cn/news/index/cid/5/request/yes/p/' + str(j) + '.html'
            # yield scrapy.Request(url=exh_url, callback=self.exhs_parse, meta={'exh_id': exh_id})

    # TODO 进入藏品详情页 working
    def cols_parse(self, response):
        print(response)
        col_lists = json.loads(response.body_as_unicode())
        print(col_lists)
        for col_url in col_lists:
            response.meta['col_id'] += 1
            col_url = 'http://www.zhejiangmuseum.com/japi/sw-cms-zb/api/queryExhibitById/' + col_url['exhibitId']
            print(col_url)
            yield scrapy.Request(url=col_url, callback=self.col_parse, meta={'col_id': response.meta['col_id']})
        return

    # 藏品详情页
    def col_parse(self, response):
        item = Item()
        item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
        item['mus_name'] = name
        item['mus_id'] = id
        item['col_id'] = response.meta['col_id']
        print(response)

        col = json.loads(response.body_as_unicode())['data']
        # TODO 数据分析、持久化存储
        item['col_name'] = col['exhibitName']
        item['col_info'] = item['col_name']
        item['col_era'] = col['age']
        item['col_picture'] = "https://www.zjmuex.com/UploadFiles/ZHEJIANG2/" + col['thumb']
        # yield item
        print(item['col_name'] + "  " + item['col_info'] + "   " + item['col_picture'] + "   " + item['col_era'])
        print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览列表
    def exhs_parse(self, response):
        # TODO 解析展览详情页网址
        url_list = response.xpath('//div[@class="am-article-list"]//@href').extract()
        for url in url_list:
            response.meta['exh_id'] += 1
            url = "http://www.luxunmuseum.cn/" + url
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
        item['exh_name'] = response.xpath('/html/body/div[3]/div[1]/h2/text()')[0].extract().strip()
        item['exh_info'] = response.xpath('/html/body/div[3]/div[1]/div[2]/div/div')[0].xpath('string(.)')[
            0].extract().strip()
        item['exh_picture'] = response.xpath('/html/body/div[3]/div[1]//@src')[0].extract().strip()
        item['exh_time'] = '见正文'
        yield item
        # print(item['exh_picture'] + item['exh_name'] + item['exh_info'] + item['exh_time'])
        print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
