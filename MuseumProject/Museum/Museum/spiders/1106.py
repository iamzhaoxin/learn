import scrapy
from scrapy import item

from ..items import *


class Gmc1103Spider(scrapy.Spider):
    name = '1106'
    start_urls = ['http://www.luxunmuseum.com.cn/']

    def parse(self, response, **kwargs):
        col_item = CollectionItem()
        col_item['mus_id'] = 1106

        col_item['mus_name'] = '北京鲁迅博物馆'
        col_url = 'http://www.luxunmuseum.com.cn/guancangjingpin/'
        yield scrapy.Request(url=col_url, callback=self.col_parse, meta={'item': col_item})

        exh_item=ExhibitionItem()
        exh_item['mus_id']=1106
        exh_item['mus_name'] = '北京鲁迅博物馆'
        exh_urls=['http://www.luxunmuseum.com.cn/jibenchenlie/','http://www.luxunmuseum.com.cn/zuixinzhanlan/','http://www.luxunmuseum.com.cn/zhanlanhuigu/']
        for exh_url in exh_urls:
            yield scrapy.Request(url=exh_url,callback=self.exh_parse,meta={'item':exh_item})

    def col_parse(self, response):
        print(response)
        return

    def exh_parse(self, response):
        print(response)
        return


