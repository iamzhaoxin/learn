from lxml import etree
from Db import DbConnection
import requests

mus_id = 5003
mus_name = "重庆自然博物馆"
header = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36'
}
db = DbConnection()


def exhibition():
    exh_id = 500310000
    exh_time = "常设"
    url = "https://www.cmnh.org.cn/list/?11_1.html"
    response = requests.get(url=url, headers=header).text
    tree = etree.HTML(response)
    li_list = tree.xpath('/html/body/div[2]/div[3]/div[1]/ul/li')
    for li in li_list:
        exh_id += 1
        exh_url = "https://www.cmnh.org.cn/" + li.xpath('.//p[@class="pm"]//@href')[0]
        exh_response = requests.get(url=exh_url, headers=header).text
        exh_tree = etree.HTML(exh_response)
        exh_name = exh_tree.xpath('//div[@class="newsxx_right"]/h1/text()')[0]
        print(exh_name)
        exh_picture = "https://www.cmnh.org.cn" + exh_tree.xpath('//div[@class="newsxx_right"]/div[2]/p[2]/img/@src')[0]
        exh_info = exh_tree.xpath('//div[@class="newsxx_nr"]')[0].xpath('string(.)').strip()
        db.insert_exhibition(exh_id, exh_name, mus_id, mus_name, exh_info, exh_picture, exh_time)
    print("finished!")


def collection():
    col_era = "活体动物"
    col_id = 500310000

    for i in range(1, 10):
        url = "https://www.cmnh.org.cn/list/?26_" + str(i) + ".html"
        response = requests.get(url=url, headers=header).text
        tree = etree.HTML(response)
        li_list = tree.xpath('//div[@class="xwListCon"]//li')
        for li in li_list:
            col_id += 1
            name = li.xpath('.//h6/text()')[0]
            print("爬取 " + name + " ing")
            picture = "https://www.cmnh.org.cn" + li.xpath('./p[@class="pm"]//@src')[0]

            info_url = "https://www.cmnh.org.cn/" + li.xpath('.//@href')[0]
            info_response = requests.get(url=info_url, headers=header).text
            info_tree = etree.HTML(info_response)
            info = info_tree.xpath('/html/body//div[@class="newsxx_nr"]')[0].xpath('string(.)').strip()

            db.insert_collection(col_id, mus_id, name, col_era, info, mus_name, picture)
    print("爬取藏品结束")


if __name__ == '__main__':
    collection()
    exhibition()
    db.close()
