
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HbaseUtil {

    //现在之间数据封闭，保证多线程情况下的数据安全
    private static ThreadLocal<Connection> connHolder = new ThreadLocal<>();

    private static ThreadLocal<HBaseAdmin> adminHolder = new ThreadLocal<>();

    private static Configuration conf = null;

    /**
     * 初始化adminHolder和connHolder
     */
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","linux121,linux123");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.setInt(HConstants.HBASE_CLIENT_OPERATION_TIMEOUT,30000);
        conf.setInt(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD,30000);
        try {
            adminHolder.set(new HBaseAdmin(conf));
            Connection conn = ConnectionFactory.createConnection(conf);
            connHolder.set(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看namespace是否存在
     * @param namespace namespace
     * @return boolean
     */
    public static boolean namespaceExists(String namespace) throws IOException {

        Admin admin = adminHolder.get();
        NamespaceDescriptor[] names = admin.listNamespaceDescriptors(); // 获取所有的命名空间
        for(NamespaceDescriptor desc : names) {
            if(namespace.equals(desc.getName()))
                return true;
        }
        return false;
    }

    /**
     * 創建命名空間
     * @param namespace namespace
     * @throws IOException IOException
     */
    public static void createNamespace(String namespace) throws IOException {
        Admin admin = adminHolder.get();
        admin.createNamespace(NamespaceDescriptor.create(namespace).build());
    }

    /**
     * 查看表是否存在
     * @param tableName tableName
     * @return boolean
     * @throws IOException IOException
     */
    public static boolean isTableExist(String tableName) throws IOException {
        Admin admin = adminHolder.get();
        return admin.tableExists(TableName.valueOf(tableName));
    }

    /**
     * 创建表
     * @param tableName tableName
     * @param columnFamily columnFamily
     * @throws IOException IOException
     */
    public static void createTable(String tableName, String... columnFamily) throws IOException {
        Admin admin = adminHolder.get();
        //判断表是否存在
        if(isTableExist(tableName)){
            System.out.println("表" + tableName + "已存在");
        }else{
            //创建表属性对象,表名需要转字节
            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
            //创建多个列族
            for(String cf : columnFamily){
                descriptor.addFamily(new HColumnDescriptor(cf));
            }
            //根据对表的配置，创建表
            admin.createTable(descriptor);
            System.out.println("表" + tableName + "创建成功！");
        }
    }

    /**
     * 删除表
     * @param tableName tableName
     * @throws IOException IOException
     */
    public static void dropTable(String tableName) throws IOException {
        HBaseAdmin admin = adminHolder.get();
        if(isTableExist(tableName)){
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("表" + tableName + "删除成功！");
        }else{
            System.out.println("表" + tableName + "不存在！");
        }
    }

    /**
     * 删除表的多行数据
     * @param tableName tableName
     * @param rows rows
     * @throws IOException IOException
     */
    public static void deleteMultiRow(String tableName, String... rows) throws IOException{
        HTable hTable = new HTable(conf, tableName);
        List<Delete> deleteList = new ArrayList<Delete>();
        for(String row : rows){
            Delete delete = new Delete(Bytes.toBytes(row));
            deleteList.add(delete);
        }
        hTable.delete(deleteList);
        hTable.close();
    }



    /**
     * 查询某张表下的所有数据
     * @param tableName tableName
     * @throws IOException IOException
     */
    public static void getAllRows(String tableName) throws IOException{
//        HTable table = new HTable(conf, tableName);
        Table table = connHolder.get().getTable(TableName.valueOf(tableName));
        //得到用于扫描region的对象
        Scan scan = new Scan();
        //使用HTable得到resultcanner实现类的对象
        ResultScanner resultScanner = table.getScanner(scan);
        for(Result result : resultScanner){
            Cell[] cells = result.rawCells();
            for(Cell cell : cells){
                //得到rowkey
                System.out.println("行键:" + Bytes.toString(CellUtil.cloneRow(cell)));
                //得到列族
                System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
    }

    /**
     * 获取某一行数据
     * @param tableName tableName
     * @param rowKey rowKey
     * @throws IOException IOException
     */
    public static void getRow(String tableName, String rowKey) throws IOException{
        HTable table = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        //get.setMaxVersions();显示所有版本
        //get.setTimeStamp();显示指定时间戳的版本
        Result result = table.get(get);
        for(Cell cell : result.rawCells()){
            System.out.println("行键:" + Bytes.toString(result.getRow()));
            System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
            System.out.println("时间戳:" + cell.getTimestamp());
        }
    }

    /**
     * 获取"列族:列"的数据
     * @param tableName tableName
     * @param rowKey rowKey
     * @param family family
     * @param qualifier qualifier
     * @throws IOException IOException
     */
    public static void getRowQualifier(String tableName, String rowKey, String family, String
            qualifier) throws IOException{
        HTable table = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        Result result = table.get(get);
        for(Cell cell : result.rawCells()){
            System.out.println("行键:" + Bytes.toString(result.getRow()));
            System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
        }
    }


    /**
     * 关闭Connection连接
     * @throws IOException IOException
     */
    public void close() throws IOException {

        Connection conn = connHolder.get();
        conn.close();
    }

    /**
     * 插入一条数据
     * @param tableName tableName
     * @param rowKey rowKey
     * @param family family
     * @param column column
     * @param value value
     * @throws IOException IOException
     */
    public static void insertData(String tableName,String rowKey,String family,String column,String value) throws IOException {

        Connection conn = connHolder.get();
        Table table = conn.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(family),Bytes.toBytes(column),Bytes.toBytes(value));
        table.put(put);
        table.close();
    }


}

