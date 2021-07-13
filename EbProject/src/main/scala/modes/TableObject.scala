package modes

case class TableObject (database:String, tableName:String, typeInfo: String, dataInfo: String) extends Serializable
