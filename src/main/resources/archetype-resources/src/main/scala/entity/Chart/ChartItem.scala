package com.company.model.entity.Chart

/**
  * ChartItem info
  *
  * @param name id(unique)
  * @param mode present mode (e.g. line, dot, ...)
  * @param x x-axis array
  * @param y y-axis array
  * @param z z-axis array
  * @return ChartItem
  */
case class ChartItem(name: String, mode: String, x: Array[Double], y: Array[Double] = null, z: Array[Double] = null) {
}
