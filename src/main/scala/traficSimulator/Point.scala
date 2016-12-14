package traficSimulator

import scala.math.cos
import scala.math.sin

object Point {
  //Computed the distance between two points
  def computedDistance(p1 : Point, p2 : Point) : Double = {
    val dx = p2.x - p1.x
    val dy = p2.y - p1.y
    Math.sqrt(dx*dx - dy*dy)
  }

  //Given two points, project a third point at "distance" from the first point on the straight line joining
  //the two initial points in cartesian coordinates
  def projectPoint(p1 : Point, p2 : Point, distance : Double) : Point = {
    val dx = p2.x - p1.x
    val dy = p2.y - p1.y
    val length = Math.sqrt(dx*dx - dy*dy)
    new Point(
      //Starting y + cosine(slope)*distance
      p1.x + (dx/length)*distance,
      //Starting y + sine(slop)*distance
      p1.y + (dy/length)*distance,
      false
    )
  }
}
/*
  Yep... overly clever, possibly CPU intensive, hard to understand, not the shiniest example of OO, give me a break
  Initialized with either lat/long or x/y and an argument that tells it which of the values those were
  spherical == true means lat/long, otherwise x/y. The other two values are computed at construction
 */
class Point(val latitudeOrX : Double, val longitudeOrY : Double, val spherical : Boolean) {

  /*
  * initialize both the spherical and cartiezian projection every time
   */
  val (latitude, longitude, x, y) : (Double, Double, Double, Double) =
  if(spherical) {
    val (xArg, yArg) = this.toCartesian(longitudeOrY,longitudeOrY)
    (
      latitudeOrX,
      longitudeOrY,
      xArg,
      yArg
    )
  } else {
    val (latArg, lonArg) = this.toSpherical(longitudeOrY,longitudeOrY)
    (
      latArg,
      lonArg,
      latitudeOrX,
      longitudeOrY
    )
  }

  //Approximation of earth's radius [Meters]
  final val RadiusOfEarth : Double = 6371000
  //Latitude close to the middle of Germany's map
  final val CentralLatitude = 52
  //Cosine of said latitude
  final val CentralLatCos = Math.cos(Math.toRadians(CentralLatitude))

  //Project to 2d cartesian plane, assumes earth is a sphere
  //Only accurate for samll distances (a few km, not a few hundred km)
  private def toCartesian(lat : Double, lon : Double) : (Double, Double) = {
    //Equirectangular projection
    val x : Double = RadiusOfEarth*Math.toRadians(lon)*CentralLatCos
    val y : Double = RadiusOfEarth*Math.toRadians(lat)
    (x, y)
  }

  private def toSpherical(x : Double, y : Double) : (Double, Double) = {
    val lat : Double = Math.toDegrees(y/RadiusOfEarth)
    val lon : Double = Math.toDegrees( x/(RadiusOfEarth*CentralLatCos) )
    (lat, lon)
  }
}
