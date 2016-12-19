package traficSimulator.MapAPI

import com.graphhopper.util.DistanceCalcEarth

/**
  * Created by george on 12/14/16.
  */
object Calculator {

  val distanceCalculator = new DistanceCalcEarth()

  def project(p1 : Point, p2 : Point, distance : Double) : (Point, Double) = {
    val distanceBetweenPoints = distanceCalculator.calcDist(p1.latitude, p1.longitude, p2.latitude, p2.longitude)
    val SplitBy = distanceBetweenPoints/distance
    if(SplitBy > 1) {
      (new Point( (p1.latitude + p2.latitude)/2, (p1.longitude + p2.longitude)/2, true ), 0)
    } else {
      (p2, distance - distanceBetweenPoints)
    }
  }

  def getNextLocation(route : Array[Point], at : Point, ind : Int, distance: Double): (Point, Int) = {
    val (newPoint : Point, remains : Double) = Calculator.project(
      at,
      route(ind),
      10
    )
    if(ind == route.length - 1) {
      (at, ind)
    }
    if(remains == 0) {
      (newPoint, ind+1)
    } else {
      val remDist =  distanceCalculator.calcDist(
        at.latitude,
        at.longitude,
        route(ind).latitude,
        route(ind).longitude
      )
      getNextLocation(route, route(ind), ind+1, remDist)
    }
  }
/*
        while(!break) {
          val (newPoint : Point, remains : Double) = Calculator.project(
            at,
            stop,
            10
          )
          if(remains == 0) {
            cDist += distanceCalculator.calcDist(
              at.latitude,
              at.longitude,
              newPoint.latitude,
              newPoint.longitude
            )
            at = newPoint
          } else {
            cDist += distanceCalculator.calcDist(
              at.latitude,
              at.longitude,
              stop.latitude,
              stop.longitude
            )
            break = true
          }
        }
 */
}