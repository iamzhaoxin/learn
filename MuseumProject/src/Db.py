import pymysql as pymysql


class DbConnection:
    conn = pymysql.connect(
        host="123.56.13.242",
        port=3306,  # 端口号
        user="root",  # 数据库用户
        password="Aliyun2021",  # 数据库密码
        database="museum",  # 要连接的数据库名称
        charset="utf8"
    )
    cursor = conn.cursor()

    def insert_collection(self, col_id, mus_id, col_name, col_era, col_info, mus_name, col_picture):
        self.conn.ping(reconnect=True)
        # 存在col_id则更新其他信息，不存在则新建记录
        sql = "INSERT INTO museum.Collection (col_id, mus_id, col_name, col_era, col_info, mus_name, col_picture)VALUES (%s,%s,%s,%s,%s,%s,%s) ON DUPLICATE KEY UPDATE mus_id=%s, col_name=%s, col_era=%s, col_info=%s, mus_name=%s, col_picture=%s;"
        self.cursor.execute(sql, (
            col_id, mus_id, col_name, col_era, col_info, mus_name, col_picture, mus_id, col_name, col_era, col_info,
            mus_name, col_picture))
        self.conn.commit()

    def insert_exhibition(self, exh_id, exh_name, mus_id, mus_name, exh_info, exh_picture, exh_time):
        self.conn.ping(reconnect=True)
        sql = "INSERT INTO museum.Exhibition(exh_id, exh_name, mus_id, mus_name, exh_info, exh_picture, exh_time) VALUES (%s,%s,%s,%s,%s,%s,%s) ON DUPLICATE KEY UPDATE exh_name=%s, mus_id=%s, mus_name=%s, exh_info=%s, exh_picture=%s, exh_time=%s;"
        self.cursor.execute(sql, (
            exh_id, exh_name, mus_id, mus_name, exh_info, exh_picture, exh_time, exh_name, mus_id, mus_name, exh_info,
            exh_picture, exh_time))
        self.conn.commit()

    def close(self):
        self.conn.close()
