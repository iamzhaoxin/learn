import json
from ..items import *

# TODO 修改id和name
id = 3203
name = '南通博物苑'


class M3102(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '3203'
    start_urls = ['http://www.ntmuseum.com/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        col_url = 'http://www.ntmuseum.com/colunm2/col3/'
        # yield scrapy.Request(url=col_url, callback=self.cols_parse, meta={'col_id': col_id})
        for j in range(1, 45):
            col_url = 'http://www.ntmuseum.com/colunm2/col1/list_17_' + str(j) + ".html"
            col_id += 200
            # yield scrapy.Request(url=col_url, callback=self.cols_parse, meta={'col_id': col_id})
        for j in range(1, 7):
            col_url = 'http://www.ntmuseum.com/colunm2/col2/list_18_' + str(j) + ".html"
            col_id += 200
            # yield scrapy.Request(url=col_url, callback=self.cols_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页 未完成
        exh_url = 'http://www.ntmuseum.com/colunm3/col2/'
        yield scrapy.Request(url=exh_url, callback=self.exhs_parse, meta={'exh_id': exh_id})

    # TODO 进入藏品详情页 working
    def cols_parse(self, response):
        # print(response)
        col_lists = response.xpath('//div[@id="pub_right"]/div[@class="pic_list"]//@href | //div[@id="pub_right"]/div[@class="article_list"]//@href').extract()
        for col_url in col_lists:
            response.meta['col_id'] += 1
            yield scrapy.Request(url=col_url, callback=self.col_parse, meta={'col_id': response.meta['col_id']})
        return

    # 藏品详情页
    def col_parse(self, response):
        # print(response)
        item = Item()
        item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
        item['mus_name'] = name
        item['mus_id'] = id
        item['col_id'] = response.meta['col_id']
        # print(response)

        # TODO 数据分析、持久化存储
        item['col_name'] = response.xpath('//div[@class="list_cont"]//li[@class="list_title"]/text()')[0].extract().strip()
        item['col_info'] = response.xpath('//div[@class="list_cont"]//li[@class="list_all"]')[0].xpath('string(.)')[0].extract().strip().replace('\n','')
        item['col_era'] = '见正文'
        item['col_picture'] = response.xpath('//div[@class="list_cont"]//li[@class="list_all"]//@src')[0].extract()
        if(len(item['col_picture'])<50):
            item['col_picture']=" http://www.ntmuseum.com"+item['col_picture']
        yield item
        # print(item['col_name']+"  "+item['col_info']+"   "+item['col_picture']+"   "+item['col_era'])
        print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览列表
    def exhs_parse(self, response):
        # TODO 解析展览详情页网址
        url_list = response.xpath('//div[@id="pub_right"]/div[@class="article_list"]//@href').extract()
        for url in url_list:
            response.meta['exh_id'] += 1
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
        item['exh_name'] = response.xpath('//div[@class="list_cont"]//li[@class="list_title"]/text()')[0].extract().strip()
        item['exh_info'] = response.xpath('//div[@class="list_cont"]//li[@class="list_all"]')[0].xpath('string(.)')[
            0].extract().strip()
        item['exh_picture'] = "http://www.ntmuseum.com/"+response.xpath('//div[@class="list_cont"]//li[@class="list_all"]//@src')[0].extract().strip()
        item['exh_time'] = '当前展览'
        yield item
        # print(item['exh_picture'] + item['exh_name'] + item['exh_info'] + item['exh_time'])
        print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
