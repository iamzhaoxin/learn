package beans;

//database:String, tableName:String, typeInfo: String, dataInfo: String
public class MyBean {
    private String databases;
    private String tableName;
    private String typeInfo;
    private String dataInfo;

    public MyBean(String databases, String tableName, String typeInfo, String dataInfo) {
        this.databases = databases;
        this.tableName = tableName;
        this.typeInfo = typeInfo;
        this.dataInfo = dataInfo;
    }

    public String getDatabases() {
        return databases;
    }

    public void setDatabases(String databases) {
        this.databases = databases;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "databases='" + databases + '\'' +
                ", tableName='" + tableName + '\'' +
                ", typeInfo='" + typeInfo + '\'' +
                ", dataInfo='" + dataInfo + '\'' +
                '}';
    }
}
