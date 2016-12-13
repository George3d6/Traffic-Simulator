package traficSimulator.MapAPI

import com.graphhopper.GHRequest
import com.graphhopper.GHResponse
import com.graphhopper.api.GraphHopperWeb
import com.graphhopper.util.shapes.{GHPoint, GHPoint3D}
import com.graphhopper.util.DistanceCalcEarth
import com.graphhopper.util.PointList
import com.graphhopper.PathWrapper
import traficSimulator.Point

import scala.collection.mutable.ListBuffer;

class RouteApiWrapper() {
  val distanceCalculator = new DistanceCalcEarth()
  val ghr = new GraphHopperWeb("http://localhost:8989/route")

  def returnRoute(begin : Point, end : Point) : ListBuffer[Point] = {
    val req = new GHRequest().
      addPoint(new GHPoint(begin.latitude,begin.longitude)).
      addPoint(new GHPoint(end.latitude,end.longitude))
    req.setVehicle("car")
    req.getHints.put("elevation", false)
    req.getHints.put("instructions", true)
    req.getHints.put("calc_points", true)

    val fullRes : GHResponse = ghr.route(req)

    if(fullRes.hasErrors)
      fullRes.getErrors

    val res : PathWrapper = fullRes.getBest

    if(res.hasErrors)
      res.getErrors


    val wayPoints = res.getPoints
    val listOfPoints = new ListBuffer[Point]()
    wayPoints.forEach(v => {
      listOfPoints += new Point(v.getLat, v.getLon)
    })
    listOfPoints
  }
}
