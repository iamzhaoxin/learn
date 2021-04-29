from scrapy.crawler import CrawlerProcess
from scrapy.utils.project import get_project_settings

process = CrawlerProcess(get_project_settings())

# myspd1是爬虫名
# process.crawl('1106')
# process.crawl('1112') error
process.crawl('1114')
process.start()
