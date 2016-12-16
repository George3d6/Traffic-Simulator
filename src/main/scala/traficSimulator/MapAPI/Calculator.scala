package traficSimulator.MapAPI

/**
  * Created by george on 12/14/16.
  */
object Calculator {
  //Approximation of earth's radius [Meters]
  val RadiusOfEarth : Double = 6371000
  //Latitude close to the middle of Germany's map
  val CentralLatitude = 52
  //Cosine of said latitude
  val CentralLatCos = Math.cos(Math.toRadians(CentralLatitude))


  //Project to 2d cartesian plane, assumes earth is a sphere
  //Only accurate for samll distances (a few km, not a few hundred km)
  def toCartesian(lat : Double, lon : Double) : (Double, Double) = {
    //Equirectangular projection
    val x : Double = RadiusOfEarth*Math.toRadians(lon)*CentralLatCos
    val y : Double = RadiusOfEarth*Math.toRadians(lat)
    (x, y)
  }

  def toSpherical(x : Double, y : Double) : (Double, Double) = {
    val lat : Double = Math.toDegrees(y/RadiusOfEarth)
    val lon : Double = Math.toDegrees( x/(RadiusOfEarth*CentralLatCos) )
    (lat, lon)
  }

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