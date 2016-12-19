package traficSimulator.MapAPI

import scala.util.Random
import com.graphhopper.{GHRequest, GHResponse, PathWrapper}
import com.graphhopper.api.GraphHopperWeb
import com.graphhopper.util.DistanceCalcEarth
import com.graphhopper.util.shapes.GHPoint

import Array._
import scala.collection.mutable.ListBuffer;

object RouteApiWrapper {
  val distanceCalculator = new DistanceCalcEarth()
  val ghr = new GraphHopperWeb("http://localhost:8989/route")

  def returnRoute(begin : Point, end : Point) : Array[Point] = {
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
      listOfPoints += new Point(v.getLat, v.getLon, true)
    })
    listOfPoints.toArray
  }

  def getRandomPoint : Point = {
    new Point(48 + Random.nextDouble()*5,8.5 + Random.nextDouble()*3,true)
  }
}
