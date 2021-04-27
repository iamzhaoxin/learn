# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class CollectionItem(scrapy.Item):
    # 藏品编号
    col_id = scrapy.Field()
    # 博物馆编号
    mus_id = scrapy.Field()
    # 藏品名称
    col_name = scrapy.Field()
    # 年代
    col_era = scrapy.Field()
    # 基本介绍
    col_info = scrapy.Field()
    # 博物馆名称
    mus_name = scrapy.Field()
    # 藏品图片
    col_picture = scrapy.Field()


class ExhibitionItem(scrapy.Item):
    # 展览编号
    exh_id = scrapy.Field()
    # 博物馆编号
    mus_id = scrapy.Field()
    # 展览名称
    exh_name = scrapy.Field()
    # 展览内容
    exh_info = scrapy.Field()
    # 博物馆名称
    mus_name = scrapy.Field()
    # 展览图片
    exh_picture = scrapy.Field()
    # 展览时间
    exh_time = scrapy.Field()
