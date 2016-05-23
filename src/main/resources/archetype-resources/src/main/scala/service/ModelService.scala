package ${package}.service

import org.apache.spark.SparkContext
import org.apache.spark.sql.DataFrame

/**
  * Model API Interface
  */
trait ModelService {

  /**
    * evaluate
    *
    * @param sc         SparkContext generated from Metatron
    * @param dataSet    input data (DataFrame) from Metatron
    * @param parameters input parameter
    * @return DataFrame (SparkSQL)
    */
  def evaluate(sc: SparkContext, dataSet: DataFrame, parameters: Array[Object]): DataFrame

}
