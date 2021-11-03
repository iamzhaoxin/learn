import json
from ..items import *

# TODO 修改id和name
id = 4403
name = '孙中山故居纪念馆'


class M4403(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '4403'
    start_urls = ['http://www.sunyat-sen.org/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        for j in range(3, 9):
            col_url = 'http://www.sunyat-sen.org/index.php?m=content&c=index&a=lists&catid=17' + str(j)
            col_id += 100
            for i in range(1, 5):
                _col_url = col_url + "&page=" + str(i)
                yield scrapy.Request(url=_col_url, callback=self.col_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页
        exh_url = 'http://www.sunyat-sen.org/index.php?m=content&c=index&a=lists&catid=53'
        yield scrapy.Request(url=exh_url, callback=self.exh_parse, meta={'exh_id': exh_id})

    # 藏品详情页
    def col_parse(self, response):
        cols = list(set(response.xpath('//div[@class="ng_box"]/div')))
        col_id = response.meta['col_id']
        for col in cols:
            item = Item()
            item['exh_id'] = item['exh_name'] = item['exh_info'] = item['exh_picture'] = item['exh_time'] = ''
            item['mus_name'] = name
            item['mus_id'] = id
            item['col_id'] = col_id
            col_id += 1

            # TODO 数据分析、持久化存储
            item['col_name'] = response.xpath('.//p[@class="ng_jsT"]//text()')[0].extract().strip()
            item['col_info'] = response.xpath('.//p[@class="ng_jsNr"]')[0].xpath('string(.)')[0].extract().strip()
            item['col_era'] = '见正文'
            item['col_picture'] ="http://www.sunyat-sen.org"+ response.xpath('//div[@class="ng_pic"]//@src')[0].extract()
            yield item
            # print(item['col_name']+"  "+item['col_info']+"   "+item['col_picture']+"   "+item['col_era'])
            print(str(item['col_id']) + "正在爬取藏品 " + item['col_name'] + " ing")
        return


    # 展览详情页
    def exh_parse(self, response):
        exhs=response.xpath('//*[@id="con_zzjs_1"]/ul/li')
        exh_id=response.meta['exh_id']
        for exh in exhs:
            item = Item()
            item['col_id'] = item['col_name'] = item['col_info'] = item['col_era'] = item['col_picture'] = ''
            item['mus_name'] = name
            item['mus_id'] = id
            item['exh_id'] = exh_id
            exh_id+=1
            # print(response)

            # TODO 解析展览数据并持久化存储
            item['exh_name'] = exh.xpath('.//text()')[0].extract().strip()
            item['exh_info'] = item['exh_name']
            item['exh_picture'] = "http://www.sunyat-sen.org/"+exh.xpath('.//@src')[0].extract().strip()
            item['exh_time'] = '基本陈列'
            yield item
            # print(item['exh_picture'] + item['exh_name'] + item['exh_info'] + item['exh_time'])
            print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
