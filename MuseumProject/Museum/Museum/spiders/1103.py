import scrapy


class Gmc1103Spider(scrapy.Spider):
    name = '1103'
    #start_urls = ['https://www.gmc.org.cn/']

    def parse(self, response, **kwargs):
        print(response)
