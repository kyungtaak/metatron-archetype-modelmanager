package ${package}.service

import java.io.StringWriter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import ${package}.entity.Chart.{Chart, ChartItem}
import ${package}.entity.ResponseEntity
import ${package}.service.ModelService
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.mllib.linalg.{Matrix, Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.slf4j.LoggerFactory

import scala.collection.mutable.WrappedArray

/**
  * Model API implements
  */
class ModelServiceImpl extends ModelService {

  val LOGGER = LoggerFactory.getLogger(classOf[ModelServiceImpl])

  override def evaluateCustom1(sc: SparkContext, inputDataFrame: DataFrame, parameters: Array[Object]): DataFrame = {

    val sqlContext: SQLContext = inputDataFrame.sqlContext

    // 전달된 데이터 프레임을 분석을 위한 데이터 모델로 변환 과정 생략..
    // 변환되었다고 가정하고 진행
    val convertDf: DataFrame = convert(sc)

    // 분석데이터 준비
    val rows: RDD[Vector] = convertDf.select("values").map {
      case Row(v: WrappedArray[Double]) => Vectors.dense(v.toArray)
    }
    val dataMatrix: RowMatrix = new RowMatrix(rows)

    // 알고리즘 수행
    val principalComponentMatrix: Matrix = dataMatrix.computePrincipalComponents(dataMatrix.numCols().toInt)

    // 결과 전달
    // - case class 를 활용하는 경우, DataFrame 생성시 이슈발생하여 Workaround 로 JSON String 활용
    val pcArrays: Array[Double] = principalComponentMatrix.toArray
    val xAxis: Array[Double] = pcArrays.take(5)
    val yAxis: Array[Double] = pcArrays.takeRight(5)
    val pcChart: Chart = new Chart("Principal Component Plot", "scatter", Array(new ChartItem("pc", "dot", xAxis, yAxis)))

    val responseEntity: ResponseEntity = new ResponseEntity(Array(pcChart))

    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)

    val out = new StringWriter
    mapper.writeValue(out, responseEntity)
    val json = out.toString()
    LOGGER.debug("Converted JSON value : {}", json)

    val rddrow: RDD[String] = sc.parallelize(Array(json))

    sqlContext.read.json(rddrow)

  }

  override def evaluateCustom2(sc: SparkContext, inputDataFrames: Array[DataFrame], modelDataFrame: DataFrame, parameters: Object): DataFrame = {

    val sqlContext:SQLContext = SQLContext.getOrCreate(sc);

    val anotherPeopleRDD = sc.parallelize(
      """{"name":"Yin","address":{"city":"Columbus","state":"Ohio"}}""" :: Nil);

    sqlContext.read.json(anotherPeopleRDD);
  }

  def convert(sc: SparkContext): DataFrame = {
    val sqlContext: SQLContext = new SQLContext(sc)

    val data = Array("{\"key\":\"EQP_PM1_1\",\"values\":[0.0,1.0,2.0,3.0,4.0,9.0]}",
      "{\"key\":\"EQP_PM1_2\",\"values\":[1.0,1.0,1.0,1.0,1.0,8.0]}",
      "{\"key\":\"EQP_PM1_3\",\"values\":[2.0,5.0,2.0,9.0,1.0,7.0]}",
      "{\"key\":\"EQP_PM1_4\",\"values\":[3.0,3.0,3.0,1.0,1.0,6.0]}",
      "{\"key\":\"EQP_PM1_5\",\"values\":[4.0,4.0,4.0,2.0,1.0,5.0]}",
      "{\"key\":\"EQP_PM2_1\",\"values\":[5.0,2.0,5.0,3.0,1.0,4.0]}",
      "{\"key\":\"EQP_PM2_2\",\"values\":[6.0,7.0,6.0,5.0,2.0,3.0]}",
      "{\"key\":\"EQP_PM2_3\",\"values\":[7.0,3.0,7.0,4.0,1.0,2.0]}",
      "{\"key\":\"EQP_PM2_4\",\"values\":[8.0,2.0,8.0,3.0,1.0,1.0]}",
      "{\"key\":\"EQP_PM2_5\",\"values\":[9.0,1.0,9.0,2.0,1.0,0.1]}")

    sqlContext.read.json(sc.parallelize(data))
  }

}
