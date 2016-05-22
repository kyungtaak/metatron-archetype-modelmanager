package service

import com.company.model.enums.Model.Model
import org.apache.spark.SparkContext
import org.apache.spark.sql.DataFrame

/**
  * Model API Interface
  */
trait ModelService {

  /**
    * evaluating
    *
    * @param model      supported analytics model
    * @param sc         SparkContext generated from Polaris
    * @param dataSet    input data (DataFrame) from Polaris
    * @param parameters input parameter
    * @return DataFrame (SparkSQL)
    */
  def evaluate(model: Model, sc: SparkContext, dataSet: DataFrame, parameters: Array[String]): DataFrame


  /**
    * training
    *
    * @param model   supported analytics model in Metis
    * @param sc      SparkContext generated from Polaris
    * @param dataSet input data (DataFrame) from Polaris
    * @return DataFrame (SparkSQL)
    */
  def train(model: Model, sc: SparkContext, dataSet: DataFrame): DataFrame

}
