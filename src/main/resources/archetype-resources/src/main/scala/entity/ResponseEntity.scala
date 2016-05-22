package entity

import com.company.model.entity.Chart.{Chart, InfoItem}

/**
  * Response entity (chart array & info array)
  *
  * @param data multiple charts
  * @param info additional info
  * @return Response entity
  */
case class ResponseEntity(data: Array[Chart] = null, info: Array[InfoItem] = null) {
}
