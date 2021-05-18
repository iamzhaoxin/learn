import json
from ..items import *

# TODO 修改id和name
id = 2102
name = '“九·一八”历史博物馆'


class M1502(scrapy.Spider):
    # TODO 修改spider_name和start_urls
    name = '2102'
    start_urls = ['http://www.918museum.org.cn/']
    custom_settings = {
        'ITEM_PIPELINES': {'Museum.pipelines.Pipeline': 300}
    }

    def parse(self, response, **kwargs):
        print("start " + name)
        col_id = int(str(id) + str(10000))
        # TODO 进入藏品目录页
        for j in range(1, 16):
            col_url = 'http://www.918museum.org.cn/index.php/article/listarticle/pid/126/rel/thumb/sidebar/sidebar?currentPage=' + str(
                j)
            col_id += 1000
            yield scrapy.Request(url=col_url, callback=self.cols_parse, meta={'col_id': col_id})

        exh_id = int(str(id) + str(10000))
        # TODO 进入展览目录页
        exh_urls = ['http://www.918museum.org.cn/index.php/article/listarticle/pid/118/rel/media/sidebar/sidebar']
        for exh_url in exh_urls:
            exh_id += 1000
            yield scrapy.Request(url=exh_url, callback=self.exhs_parse, meta={'exh_id': exh_id})

    # TODO 进入藏品详情页
    def cols_parse(self, response):
        col_lists = response.xpath('//div[@class="article row "]//@href').extract()
        for col_url in col_lists:
            response.meta['col_id'] += 1
            col_url = 'http://www.918museum.org.cn/' + col_url
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
        item['col_name'] = response.xpath('//div[@class="article_title"]/text()')[0].extract().strip()
        item['col_info'] = \
            response.xpath(
                '//div[@class="article_content"]//div[@class="article_content"] | //*[@id="frameContent"]/p[2]')[
                0].xpath('string(.)')[0].extract().strip()
        item['col_era'] = '近代'
        item['col_picture'] = "http://www.918museum.org.cn/"+response.xpath('//div[@class="article_content"]//@src')[0].extract()
        yield item
        # print(item['col_name']+"  "+item['col_info']+"   "+item['col_picture']+"   "+item['col_era'])
        print("正在爬取藏品 " + item['col_name'] + " ing")
        return

    # 展览列表
    def exhs_parse(self, response):
        # TODO 解析展览详情页网址
        url_list = response.xpath('/html/body/div[3]/div/div/div[1]/div/div[2]/div//@href').extract()
        for url in url_list:
            response.meta['exh_id'] += 1
            url = "http://www.918museum.org.cn/" + url
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
        # TODO 解析展览数据并持久化存储
        item['exh_name'] = response.xpath('//*[@id="article"]/div[1]/div[2]/div[1]/text()')[0].extract().strip()
        item['exh_info'] = response.xpath('//*[@id="frameContent"]')[0].xpath('string(.)')[0].extract().strip()
        item['exh_picture'] = "http://www.hebeimuseum.org.cn" + response.xpath('//*[@id="frameContent"]/p[1]/img/@src')[
            0].extract().strip()
        item['exh_time'] = '见正文'
        yield item
        print("                                                 正在爬取展览 " + item['exh_name'] + " ing")
        return
