import pymysql as pymysql


class DbConnection:
    conn = pymysql.connect(
        host="8.140.3.158",
        port=3306,  # 端口号
        user="buctcs1802",  # 数据库用户
        password="cs1802..",  # 数据库密码
        database="museum",  # 要连接的数据库名称
        charset="utf8"
    )
    cursor = conn.cursor()

    for i in range(1714, 1719):
        conn.ping(reconnect=True)
        sql = "insert into museum.comments (aid,uid,mid,exhibitionstar,servicestar,environmentstar,general_comment,comment,picture) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        cursor.execute(sql, (i, 1, i, 2, 3, 3,3, "暂无评价","null"))
        conn.commit()
    conn.close()
