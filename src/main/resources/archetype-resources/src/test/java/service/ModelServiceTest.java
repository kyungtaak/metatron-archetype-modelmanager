package com.company.model.service;

import com.company.model.enums.Model;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ModelServiceTest {

  @Test
  public void evaluatePCATest() {
    SparkConf sparkConf = new SparkConf().setAppName("PCA").setMaster("local[*]")
            .set("spark.driver.host", "localhost");
    SparkContext sc = new SparkContext(sparkConf);
    SQLContext sqlContext = new SQLContext(sc);
    DataFrame df = sqlContext.createDataFrame(
            Arrays.asList(new KVModel("EQP_PM1_1", Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 9.0)),
                    new KVModel("EQP_PM1_2", Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 8.0)),
                    new KVModel("EQP_PM1_3", Arrays.asList(2.0, 5.0, 2.0, 9.0, 1.0, 7.0)),
                    new KVModel("EQP_PM1_4", Arrays.asList(3.0, 3.0, 3.0, 1.0, 1.0, 6.0)),
                    new KVModel("EQP_PM1_5", Arrays.asList(4.0, 4.0, 4.0, 2.0, 1.0, 5.0)),
                    new KVModel("EQP_PM2_1", Arrays.asList(5.0, 2.0, 5.0, 3.0, 1.0, 4.0)),
                    new KVModel("EQP_PM2_2", Arrays.asList(6.0, 7.0, 6.0, 5.0, 2.0, 3.0)),
                    new KVModel("EQP_PM2_3", Arrays.asList(7.0, 3.0, 7.0, 4.0, 1.0, 2.0)),
                    new KVModel("EQP_PM2_4", Arrays.asList(8.0, 2.0, 8.0, 3.0, 1.0, 1.0)),
                    new KVModel("EQP_PM2_5", Arrays.asList(9.0, 1.0, 9.0, 2.0, 1.0, 0.1))), KVModel.class);

    DataFrame result = new ModelServiceImpl().evaluate(Model.PCA(), sc, df, new String[]{"PM1","PM2" });
    assertNotNull(result);
    result.printSchema();
    result.show();
    System.out.println("result : \n"+result.toJSON().first());
  }

  /**
   * from Metatron
   */
  public class KVModel implements Serializable {
    private String key;
    private List<Double> values;

    public KVModel(String key, List<Double> values) {
      this.key = key;
      this.values = values;
    }

    public String getKey() {
      return key;
    }
    public void setKey(String key) {
      this.key = key;
    }
    public List<Double> getValues() {
      return values;
    }
    public void setValues(List<Double> values) {
      this.values = values;
    }
  }
}
