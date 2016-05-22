package com.company.model.entity.Chart

/**
  * Chart info
  *
  * @param name chart name
  * @param types plot type (e.g. scatter, histogram, scree, bar, pie, box, ...)
  * @param chartItem multiple chart items
  * @return Chart
  */
case class Chart(name: String, types: String, chartItem: Array[ChartItem]) {
}
