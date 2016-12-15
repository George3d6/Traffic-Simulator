package traficSimulator.MapAPI

import traficSimulator.MapAPI.Calculator.{toCartesian, toSpherical}
/*
  Yep... overly clever, possibly CPU intensive, hard to understand, not the shiniest example of OO, give me a break
  Initialized with either lat/long or x/y and an argument that tells it which of the values those were
  spherical == true means lat/long, otherwise x/y. The other two values are computed at construction
 */
class Point(latitudeOrX : Double, longitudeOrY : Double, spherical : Boolean) {

  /*
  * initialize both the spherical and cartiezian projection every time
   */
  val (latitude, longitude, x, y) : (Double, Double, Double, Double) =
  if(spherical) {
    val (xArg, yArg) = toCartesian(longitudeOrY,longitudeOrY)
    (
      latitudeOrX,
      longitudeOrY,
      xArg,
      yArg
    )
  } else {
    val (latArg, lonArg) = toSpherical(longitudeOrY,longitudeOrY)
    (
      latArg,
      lonArg,
      latitudeOrX,
      longitudeOrY
    )
  }

}
