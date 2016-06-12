package ${package}.service;

import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;

import java.util.Map;

/**
 * Model Java API Interface
 */
public interface JavaModelService {

  /**
   * CUSTOM1 Type
   *
   * @param sc SparkContext generated from Metatron
   * @param inputDataFrame  input data (DataFrame) from Metatron
   * @param parameters input parameters (Array)
   * @return DataFrame Analyzed result
   */
  DataFrame evaluateCustom1(SparkContext sc, DataFrame inputDataFrame, Object[] parameters);

  /**
   * CUSTOM2 Type
   *
   * @param sc SparkContext generated from Metatron
   * @param inputDataFrames input data(DataFrame Array) from Metatron
   * @param modelDataFrame model data(DataFrame) from Metatron
   * @param parameters input parameters (Key/Value Object like Map)
   * @return DataFrame Analyzed result
   */
  DataFrame evaluateCustom2(SparkContext sc, DataFrame[] inputDataFrames, DataFrame modelDataFrame, Map<String, Object> parameters);
}
