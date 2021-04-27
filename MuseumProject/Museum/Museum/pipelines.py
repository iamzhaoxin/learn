# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
import pymysql
from itemadapter import ItemAdapter


class ExhibitionPipeline:
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
        self._sql = None

    def process_item(self, item, spider):
        # 运行
        self.cursor.execute(self.sql, (
            item["exh_id"], item["exh_name"], item["mus_id"], item["mus_name"], item["exh_info"], item["exh_picture"],
            item["exh_time"], item["exh_name"], item["mus_id"], item["mus_name"], item["exh_info"], item["exh_picture"],
            item["exh_time"]))
        # 存入数据库
        self.conn.commit()
        return item

    # 属性
    @property
    def sql(self):
        if not self._sql:
            self._sql = """
               INSERT INTO museum.Exhibition(exh_id, exh_name, mus_id, mus_name, exh_info, exh_picture, exh_time) VALUES (%s,%s,%s,%s,%s,%s,%s) ON DUPLICATE KEY UPDATE exh_name=%s, mus_id=%s, mus_name=%s, exh_info=%s, exh_picture=%s, exh_time=%s;
               """
            return self._sql
        return self._sql


class CollectionPipeline:
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
        self._sql = None

    def process_item(self, item, spider):
        # 运行
        self.cursor.execute(self.sql, (item['col_id'], item['mus_id'], item['col_name'], item['col_era'],
                                       item['col_info'], item['mus_name'], item['col_picture'], item['mus_id'],
                                       item['col_name'], item['col_era'],
                                       item['col_info'], item['mus_name'], item['col_picture']))
        # 存入数据库
        self.conn.commit()
        return item

    # 属性
    @property
    def sql(self):
        if not self._sql:
            self._sql = """
            INSERT INTO museum.Collection (col_id, mus_id, col_name, col_era, col_info, mus_name, col_picture)VALUES (%s,%s,%s,%s,%s,%s,%s) ON DUPLICATE KEY UPDATE mus_id=%s, col_name=%s, col_era=%s, col_info=%s, mus_name=%s, col_picture=%s;
            """
            return self._sql
        return self._sql
