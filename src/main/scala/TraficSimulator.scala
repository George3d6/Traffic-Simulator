package traficSimulator

import MapAPI.RouteApiWrapper

object TraficSimulator {
  def main(args: Array[String]) = {
    val router = new RouteApiWrapper()

    for(point <- router.returnRoute(
      new Point(52,9),
      new Point(51,10)
    )) {
      println(point.latitude)
      println(point.longitude)
    }

    for(point <- router.returnRoute(
      new Point(53,9),
      new Point(50,8)
    )) {
      println(point.latitude)
      println(point.longitude)
    }

  }
}
