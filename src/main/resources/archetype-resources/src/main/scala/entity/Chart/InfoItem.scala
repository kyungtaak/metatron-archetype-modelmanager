package entity.Chart

import org.apache.spark.mllib.linalg.DenseMatrix

/**
  * Descriptive information
  *
  * @param key    Key (unique)
  * @param value  Single value
  * @param values Multiple value
  * @param matrix DenseMatrix
  */
case class InfoItem(key: String, value: Double = Double.NaN, values: Array[Double] = null, matrix: DenseMatrix = null) {
}