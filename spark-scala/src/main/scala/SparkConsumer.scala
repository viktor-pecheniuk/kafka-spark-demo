import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.hive.HiveContext


object SparkConsumer {

  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val logger = Logger.getLogger(getClass.getName)

    println("program started............")

    val sparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("SparkConsumerApp")
      .set("javax.jdo.option.ConnectionURL", "jdbc:mysql://localhost/metastore?createDatabaseIfNotExist=true")
      .set("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver")
      .set("javax.jdo.option.ConnectionUserName", "viktor")
      .set("javax.jdo.option.ConnectionPassword", "admin")
      .set("spark.sql.warehouse.dir", "/Users/vpec/apps/")

    val sc = new SparkContext(sparkConf)
    val hc = new HiveContext(sc)
    val spark = SparkSession
       .builder()
       .appName("SparkSessionApp")
       .enableHiveSupport()
       .getOrCreate()

    val df = spark
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "transactions")
      .load()

    df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)]

    logger.info("created DF.....")
    df.printSchema()

    df.registerTempTable("my_temp_table")
    // hc.sql("CREATE SCHEMA hive_schema")
    // hc.sql("USE hive_schema")
    hc.sql("CREATE TABLE IF NOT EXISTS first_hive_table STORED AS ORC AS SELECT * from my_temp_table")

    val dfHive = hc.sql("SELECT * from first_hive_table")

    logger.info("Reading hive table : OK")
    logger.info("-----------------------")
    logger.info("-----------------------")
    logger.info(dfHive.show())

  }
}