from scrapy.crawler import CrawlerProcess
from scrapy.utils.project import get_project_settings

process = CrawlerProcess(get_project_settings())

# myspd1是爬虫名
# process.crawl('1106')
# process.crawl('1112') error
# process.crawl('1114')
#process.crawl('1301')
#process.crawl('3603')
# process.crawl('2102')
# process.crawl('3102')
# process.crawl('3203')
# process.crawl('3205')
# process.crawl('3301')
# process.crawl('3401')
# process.crawl('3601')
# process.crawl('3702')
# process.crawl('3705')
# process.crawl('4202')
# process.crawl('4301')
# process.crawl('4403')
# process.crawl('4601')
process.crawl('6301')
process.start()
