package ${package}.service

import org.apache.spark.SparkContext
import org.apache.spark.sql.DataFrame

/**
  * Model API Interface
  */
trait ModelService {

  /**
    * CUSTOM1 Type
    *
    * @param sc SparkContext generated from Metatron
    * @param inputDataFrame  input data (DataFrame) from Metatron
    * @param parameters input parameters (Array)
    * @return DataFrame Analyzed result
    */
  def evaluateCustom1(sc: SparkContext, inputDataFrame: DataFrame, parameters: Array[Object]): DataFrame;

  /**
    * CUSTOM2 Type
    *
    * @param sc SparkContext generated from Metatron
    * @param inputDataFrames input data(DataFrame Array) from Metatron
    * @param modelDataFrame model data(DataFrame) from Metatron
    * @param parameters input parameters (Key/Value Object like Map)
    * @return DataFrame Analyzed result
    */
  def evaluateCustom2(sc: SparkContext, inputDataFrames: Array[DataFrame], modelDataFrame: DataFrame, parameters: Object): DataFrame;

}