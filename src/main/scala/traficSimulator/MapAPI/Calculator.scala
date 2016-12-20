package traficSimulator.MapAPI

import com.graphhopper.util.DistanceCalcEarth

/**
  * Created by george on 12/14/16.
  */
object Calculator {

  val distanceCalculator = new DistanceCalcEarth()

  def project(p1 : Point, p2 : Point, distance : Double) : (Point, Double) = {
    val distanceBetweenPoints : Double = distanceCalculator.calcDist(p1.latitude, p1.longitude, p2.latitude, p2.longitude)
    if(distanceBetweenPoints == 0) {
      return (p2, distance)
    }
    val by = 1 - (distanceBetweenPoints - distance)/distanceBetweenPoints
    if(distanceBetweenPoints > distance) {
      (new Point( p1.latitude + (p2.latitude - p1.latitude)*by, p1.longitude + (p2.longitude - p1.longitude)*by, true ), 0)
    } else {
      (p2, distance - distanceBetweenPoints)
    }
  }

  def getNextLocation(route : Array[Point], at : Point, ind : Int, distance: Double): (Point, Int, String) = {
    val (newPoint : Point, remains : Double) = Calculator.project(
      at,
      route(ind),
      distance
    )
    if(remains <= 0) {
      (newPoint, ind, "intermediary")
    } else {
      if(ind == route.length - 1) {
        (route(route.length - 1), ind, "stop")
      } else {
        getNextLocation(route, newPoint, ind+1, remains)
      }
    }
  }

}