import json
from ..items import *

# TODO 修改id和name
id = 3702
name = '青岛市博物馆'


class M3702(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '3702'
    start_urls = ['http://www.qingdaomuseum.com/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        for j in range(28, 46):
            col_url = 'http://www.qingdaomuseum.com/collection/category/' + str(j)
            col_id += 1000
            for i in range(1, 6):
                col_id += 100
                _col_url = col_url + "/" + str(i)
                yield scrapy.Request(url=_col_url, callback=self.cols_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页

        exh_id += 1000
        exh_url = 'http://www.qingdaomuseum.com/exhibition/category/16'
        yield scrapy.Request(url=exh_url, callback=self.exh_parse, meta={'exh_id': exh_id})

    # TODO 进入藏品详情页 working
    def cols_parse(self, response):
        col_lists = response.xpath('/html/body//div[@class="dc_img"]//@href').extract()
        for col_url in col_lists:
            response.meta['col_id'] += 1
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
        item['col_name'] = response.xpath('/html/body/div[6]/div[2]/div/h3/text()')[0].extract().strip()
        item['col_info'] = response.xpath('/html/body/div[6]/div[2]/div/div[4]/div')[0].xpath('string(.)')[
            0].extract().strip()
        item['col_era'] = response.xpath('/html/body/div[6]/div[2]/div/div[4]/span[1]/text()')[0].extract().strip()
        item['col_picture'] = response.xpath('/html/body/div[6]/div[2]/div/div[5]/div/div[2]/div/ul/li[1]/img//@src')[
            0].extract()
        yield item
        # print(item['col_name']+"  "+item['col_info']+"   "+item['col_picture']+"   "+item['col_era'])
        print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览详情页
    def exh_parse(self, response):
        exhs = response.xpath('/html/body/div[6]/div[2]/div[2]/div[1]/div')
        exh_id = response.meta['exh_id']
        for exh in exhs:
            item = Item()
            item['col_id'] = item['col_name'] = item['col_info'] = item['col_era'] = item['col_picture'] = ''
            item['mus_name'] = name
            item['mus_id'] = id
            item['exh_id'] = exh_id
            exh_id += 1
            # print(response)

            # TODO 解析展览数据并持久化存储
            item['exh_name'] = exh.xpath('./div//h4/text()')[0].extract().strip()
            item['exh_info'] = exh.xpath('./div//p')[0].extract().strip()
            item['exh_picture'] = exh.xpath('./div//@src')[0].extract().strip()
            item['exh_time'] = ''
            yield item
            # print(item['exh_picture'] + item['exh_name'] + item['exh_info'] + item['exh_time'])
            print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
