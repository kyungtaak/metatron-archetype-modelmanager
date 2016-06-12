package ${package}.service;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ModelServiceTest {

  @Test
  public void evaluatePCATest() {
    SparkConf sparkConf = new SparkConf().setAppName("PCA").setMaster("local[*]")
            .set("spark.driver.host", "localhost")
            .set("spark.driver.allowMultipleContexts", "true");
    SparkContext sc = new SparkContext(sparkConf);
    SQLContext sqlContext = new SQLContext(sc);

    DataFrame df = sqlContext.emptyDataFrame();

    DataFrame result = new ModelServiceImpl().evaluateCustom1(sc, df, new String[]{"PM1","PM2" });
    assertNotNull(result);
    result.printSchema();
    result.show();
    System.out.println("result : \n"+result.toJSON().first());
  }

}