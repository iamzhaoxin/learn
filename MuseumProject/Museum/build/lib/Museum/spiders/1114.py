import scrapy

from ..items import *


class M1114(scrapy.Spider):
    name = '1114'
    start_urls = ['http://www.pgm.org.cn/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start 文化部恭王府博物馆")
        # 藏品
        col_id = 111410000
        col_url = 'http://www.pgm.org.cn/pgm/gzjp/list'
        for i in range(0, 5):
            if i == 0:
                s = ".shtml"
            else:
                s = "_" + str(i) + ".shtml"
            _col_url = col_url + s
            col_id += 1000
            yield scrapy.Request(url=_col_url, callback=self.cols_parse, meta={'col_id': col_id})

        # 展览
        exh_id = 111410000
        exh_urls = ['http://www.pgm.org.cn/pgm/cszl/lm_list.shtml']
        for exh_url in exh_urls:
            exh_id += 1000
            yield scrapy.Request(url=exh_url, callback=self.exhs_parse, meta={'exh_id': exh_id})

    # 藏品列表
    def cols_parse(self, response):
        col_urls = response.xpath('/html/body/div/div[3]/div[2]/ul//@href').extract()
        for col_url in col_urls:
            response.meta['col_id'] += 1
            col_url = col_url.replace('../../', 'http://www.pgm.org.cn/')
            yield scrapy.Request(url=col_url, callback=self.col_parse, meta={'col_id': response.meta['col_id']})
        return

    # 单个藏品
    def col_parse(self, response):
        item = Item()
        item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
        item['mus_name'] = '文化部恭王府博物馆'
        item['mus_id'] = 1114
        item['col_id'] = response.meta['col_id']
        item['col_name'] = response.xpath('/html/body/div/div[2]/div/div[2]/div[@class="title"]/text()')[0].extract()
        item['col_info'] = \
            response.xpath('/html/body/div/div[2]/div/div[2]/div[@class="pages_content"]')[0].xpath('string(.)')[
                0].extract().strip()
        item['col_era'] = '不详'
        item['col_picture'] = "http://www.pgm.org.cn/" + \
                              response.xpath('/html/body/div/div[2]/div/div[2]/div[@class="pages_content"]//@src')[
                                  0].extract()
        yield item
        print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览列表
    def exhs_parse(self, response):
        url_list = response.xpath('//ul[@class="lmlist"]/li//@href').extract()
        for url in url_list:
            response.meta['exh_id'] += 1
            url = url.replace('../../', 'http://www.pgm.org.cn/')
            yield scrapy.Request(url=url, callback=self.exh_parse, meta={'exh_id': response.meta['exh_id']})
        return

    def exh_parse(self, response):
        item = Item()
        item['col_id'] = item['col_name'] = item['col_info'] = item['col_era'] = item['col_picture'] = ''
        item['mus_name'] = '文化部恭王府博物馆'
        item['mus_id'] = 1114
        item['exh_id'] = response.meta['exh_id']
        item['exh_name'] = response.xpath('/html/head/title/text()')[0].extract().replace('-文化和旅游部恭王府博物馆', '')
        item['exh_info'] = response.xpath('//dd[@class="fr"]/p')[0].xpath('string(.)')[0].extract().strip()
        item['exh_picture'] = "http://www.pgm.org.cn/" + response.xpath('//*[@class="fl"]//@src')[0].extract().strip()
        item['exh_time'] = '常设'
        yield item
        print("正在爬取展览 " + item['exh_name'] + " ing")
        return
