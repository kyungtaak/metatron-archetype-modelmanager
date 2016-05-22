package com.skt.metatron;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.junit.Test;

public class JavaSparkPiTest {

  @Test
  public void pi() {

    SparkConf conf = new SparkConf()
            .setMaster("local[*]")
            .setAppName("UnitTest")
            .set("spark.driver.host", "localhost");

    SparkContext sc = new SparkContext(conf);

    System.out.println("Pi is roughly " + new JavaSparkPi().runPi(sc, 10));

  }
}