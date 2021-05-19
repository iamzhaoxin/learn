from ..items import *

class M1106(scrapy.Spider):
    name = '1106'
    start_urls = ['http://www.luxunmuseum.com.cn/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response):
        print("start 北京鲁迅博物馆")
        # 藏品
        col_id = 110610000
        for i in range(1, 4):
            col_id += 1000
            col_url = 'http://www.luxunmuseum.com.cn/guancangjingpin/list_19_' + str(i) + '.htm'
            yield scrapy.Request(url=col_url, callback=self.cols_parse, meta={'col_id': col_id})

        # 展览
        exh_id = 110610000
        exh_urls = ['http://www.luxunmuseum.com.cn/jibenchenlie/', 'http://www.luxunmuseum.com.cn/zuixinzhanlan/',
                    'http://www.luxunmuseum.com.cn/zhanlanhuigu/']
        for exh_url in exh_urls:
            exh_id += 1000
            yield scrapy.Request(url=exh_url, callback=self.exh_parse, meta={'exh_id': exh_id})


    # 藏品列表
    def cols_parse(self, response):
        col_urls = response.xpath('/html/body/div[3]/div[2]/div[2]/div[1]/dl//@href').extract()
        for col_url in col_urls:
            response.meta['col_id'] += 1
            col_url = "http://www.luxunmuseum.com.cn/" + col_url
            yield scrapy.Request(url=col_url, callback=self.col_parse, meta={'col_id': response.meta['col_id']})
        return

    # 单个藏品
    def col_parse(self, response):
        item = Item()
        item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
        item['mus_name'] = '北京鲁迅博物馆'
        item['mus_id'] = 1106
        item['col_id'] = response.meta['col_id']
        item['col_name'] = response.xpath('/html/body/div[3]/div[2]/div[2]/div[1]/text()')[0].extract()
        item['col_info'] = response.xpath('/html/body/div[3]/div[2]/div[2]/div[3]')[0].xpath('string(.)')[
            0].extract().strip()
        item['col_era'] = '近代'
        item['col_picture'] = "http://www.luxunmuseum.com.cn/" + \
                              response.xpath('/html/body/div[3]/div[2]/div[2]//@src')[0].extract()
        yield item
        print("正在爬取藏品 "+item['col_name']+" ing")
        return

    # 展览列表
    def exh_parse(self, response):
        item = Item()
        item['col_id'] = item['col_name'] = item['col_info'] = item['col_era'] = item['col_picture'] = ''
        item['mus_name'] = '北京鲁迅博物馆'
        item['mus_id'] = 1106
        exh_list = response.xpath('/html/body/div[3]/div[2]/div[2]/div[1]/div[@class="list_chenlie"]')
        for exh in exh_list:
            response.meta['exh_id'] += 1
            item['exh_id'] = response.meta['exh_id']
            item['exh_name'] = exh.xpath('.//a/text()')[0].extract()
            item['exh_info'] = exh.xpath('.//dd/text()')[0].extract()
            item['exh_picture'] = "http://www.luxunmuseum.com.cn/"+exh.xpath('.//img/@src')[0].extract()
            item['exh_time'] = 'None'
            yield item
            print("正在爬取展览 "+item['exh_name']+" ing")
        return
