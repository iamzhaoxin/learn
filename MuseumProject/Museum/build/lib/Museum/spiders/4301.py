import json
from ..items import *

# TODO 修改id和name
id = 4301
name = '湖南省博物馆'


class M4301(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '4301'
    start_urls = ['http://www.hnmuseum.com/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        for j in range(1, 100):
            if j==1:
                col_url='http://61.187.53.122/List.aspx?lang=zh-CN&page={0}'
            else:
                col_url = 'http://61.187.53.122/List.aspx?lang=zh-CN&page=' + str(j)
            col_id += 50
            # yield scrapy.Request(url=col_url, callback=self.cols_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页
        exh_url = 'http://www.hnmuseum.com/zh-hans/content/%E5%BD%93%E5%89%8D%E5%B1%95%E8%A7%88%EF%BC%8D%E5%9F%BA%E6%9C%AC%E9%99%88%E5%88%97'
        yield scrapy.Request(url=exh_url, callback=self.exhs_parse, meta={'exh_id': exh_id})

    # TODO 进入藏品详情页 working
    def cols_parse(self, response):
        col_lists = response.xpath('//*[@id="thumbnailUL"]//@href').extract()
        for col_url in col_lists:
            response.meta['col_id'] += 1
            col_url = 'http://61.187.53.122/' + col_url
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
        item['col_name'] = response.xpath('/html/body/div[2]/div/div[3]/div[1]/div[1]/text()')[0].extract().strip()
        item['col_info'] = response.xpath('//*[@id="divContent"]')[0].xpath('string(.)')[
            0].extract().strip()
        item['col_era'] = response.xpath('//div[@class="c-attr"]/text()')[0].extract().strip()
        item['col_picture'] = response.xpath('//*[@id="linkPicture"]/img/@src')[
            0].extract()
        yield item
        # print(item['col_name']+"  "+item['col_info']+"   "+item['col_picture']+"   "+item['col_era'])
        print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览列表
    def exhs_parse(self, response):
        # TODO 解析展览详情页网址
        url_list = list(set(response.xpath('//*[@id="block-views-a784821b4fd9f41563c7164fd2a2f96e"]/div/div/div//@href').extract()))
        for url in url_list:
            response.meta['exh_id'] += 1
            url = "http://www.hnmuseum.com" + url
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

        # # TODO 解析展览数据并持久化存储
        item['exh_name'] = response.xpath('//*[@id="page-title"]/text()')[0].extract().strip()
        item['exh_info'] = response.xpath('//*[@id="node-850"]/div/div/div/div/div[2]/div[2]')[0].xpath('string(.)')[
            0].extract().strip()
        item['exh_picture'] = response.xpath('//*[@id="node-850"]/div/div/div/div/div[2]/div[1]/img/@src')[0].extract().strip()
        item['exh_time'] = '常设'
        yield item
        # print(item['exh_picture'] + item['exh_name'] + item['exh_info'] + item['exh_time'])
        print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
