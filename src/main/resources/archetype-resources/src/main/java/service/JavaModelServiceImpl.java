package ${package}.service;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JavaModelServiceImpl implements JavaModelService {

  @Override
  public DataFrame evaluateCustom1(SparkContext sc, DataFrame inputDataFrame, Object[] parameters) {

    SQLContext sqlContext = new SQLContext(sc);
    JavaSparkContext jsc = new JavaSparkContext(sc);

    List<String> jsonData = Arrays.asList(
            "{\"name\":\"Yin\",\"address\":{\"city\":\"Columbus\",\"state\":\"Ohio\"}}");
    JavaRDD<String> anotherPeopleRDD = jsc.parallelize(jsonData);

    return sqlContext.read().json(anotherPeopleRDD);

  }

  @Override
  public DataFrame evaluateCustom2(SparkContext sc, DataFrame[] inputDataFrames, DataFrame modelDataFrame, Map<String, Object> parameters) {

    SQLContext sqlContext = new SQLContext(sc);
    JavaSparkContext jsc = new JavaSparkContext(sc);

    List<String> jsonData = Arrays.asList(
            "{\"name\":\"Yin\",\"address\":{\"city\":\"Columbus\",\"state\":\"Ohio\"}}");
    JavaRDD<String> anotherPeopleRDD = jsc.parallelize(jsonData);

    return sqlContext.read().json(anotherPeopleRDD);
  }
}
