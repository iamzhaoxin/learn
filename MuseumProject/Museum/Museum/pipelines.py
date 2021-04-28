# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
import pymysql
from itemadapter import ItemAdapter

class Pipeline:
    def __init__(self):
        # 各种参数
        params = {
            'host': '123.56.13.242',
            'port': 3306,
            'user': 'root',
            'password': 'Aliyun2021',
            'database': 'museum',
            'charset': 'utf8'
        }
        # 连接
        self.conn = pymysql.connect(**params)
        # 调用cursor方法
        self.cursor = self.conn.cursor()
        self.sql=None

    def process_item(self, item, spider):
        # 运行
        if (item['col_id'] != ""):
            self.sql = """
                        INSERT INTO museum.Collection (col_id, mus_id, col_name, col_era, col_info, mus_name, col_picture)VALUES (%s,%s,%s,%s,%s,%s,%s) ON DUPLICATE KEY UPDATE mus_id=%s, col_name=%s, col_era=%s, col_info=%s, mus_name=%s, col_picture=%s;
                        """
            self.cursor.execute(self.sql, (item['col_id'], item['mus_id'], item['col_name'], item['col_era'],
                                       item['col_info'], item['mus_name'], item['col_picture'], item['mus_id'],
                                       item['col_name'], item['col_era'],
                                       item['col_info'], item['mus_name'], item['col_picture']))
            # 存入数据库
            self.conn.commit()
        return item