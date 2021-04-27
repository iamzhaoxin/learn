import scrapy
from ..items import *

class Gmc1103Spider(scrapy.Spider):
    name = '1106'
    start_urls = ['http://www.luxunmuseum.com.cn']

    def parse(self, response, **kwargs):
        print(response)
