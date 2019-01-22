package com.myspark.app;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

import static org.apache.spark.sql.functions.col;

public class SparkAvroConsumer {

    public static void main(String[] args) {

    	SparkSession spark = SparkSession
                .builder()
                .appName("testSpark")
                // .setMaster("local[*]")
                .getOrCreate();

        Dataset<Row> df = spark.readStream()
		  .format("kafka")
		  .option("kafka.bootstrap.servers", "localhost:9092")
		  .option("subscribe", "transactions")
		  .load();
		
		// df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");
		df.printSchema();
    }

}