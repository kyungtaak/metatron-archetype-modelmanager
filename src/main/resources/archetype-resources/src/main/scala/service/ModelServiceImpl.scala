package service

import com.company.model.entity.Chart.{Chart, ChartItem}
import com.company.model.entity.ResponseEntity
import com.company.model.enums.Model
import com.company.model.enums.Model._
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.mllib.linalg.{Matrix, Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SQLContext}

import scala.collection.mutable.WrappedArray

/**
  * Model API implements
  */
class ModelServiceImpl extends ModelService {

  /**
    * evaluating
    *
    * @param model      supported analytics model
    * @param sc         SparkContext generated from Polaris
    * @param dataSet    input data (DataFrame) from Polaris
    * @param parameters input parameter
    * @return DataFrame (SparkSQL)
    */
  override def evaluate(model: Model, sc: SparkContext, dataSet: DataFrame, parameters: Array[String]): DataFrame = {
    val result: DataFrame = model match {
      case Model.PCA => {
        //1.read data
        val rows: RDD[Vector] = dataSet.select("values").map {
          case Row(v: WrappedArray[Double]) => Vectors.dense(v.toArray)
        }
        val dataMatrix: RowMatrix = new RowMatrix(rows)
        //2.execute algs
        val principalComponentMatrix: Matrix = dataMatrix.computePrincipalComponents(dataMatrix.numCols().toInt)
        //3.make ResponseEntity
        val pcArrays: Array[Double] = principalComponentMatrix.toArray
        val xAxis = pcArrays.take(5)
        val yAxis = pcArrays.takeRight(5)
        val pcChart = new Chart("Principal Component Plot", "scatter", Array(new ChartItem("pc", "dot", xAxis, yAxis)))
        val responseEntity: ResponseEntity = new ResponseEntity(Array(pcChart))
        new SQLContext(sc).createDataFrame(sc.parallelize(Array(responseEntity)))
      }
    }
    result
  }

  /**
    * training
    *
    * @param model      supported analytics model in Metis
    * @param sc         SparkContext generated from Polaris
    * @param dataSet    input data (DataFrame) from Polaris
    * @return DataFrame (SparkSQL)
    */
  override def train(model: Model, sc: SparkContext, dataSet: DataFrame): DataFrame = {
    val result: DataFrame = model match {
      case Model.PCA => {
        null
      }
    }
    result
  }

}
