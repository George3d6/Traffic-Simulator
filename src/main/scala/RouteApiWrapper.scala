import com.graphhopper.GHRequest
import com.graphhopper.api.GraphHopperWeb
import com.graphhopper.util.shapes.GHPoint

class RouteApiWrapper() {

  val ghr = new GraphHopperWeb("http://localhost:8989/route?point=52.625561,13.278351&point=52.537108,13.425293&points_encoded=false");


  def getRoute(start: Point ,end: Point) = {

  }
  def returnPoint() {
    val req = new GHRequest().
      addPoint(new GHPoint(52.625561,13.278351)).
      addPoint(new GHPoint(52.537108,13.425293))
    req.setVehicle("car")
    req.getHints().put("elevation", false)
    req.getHints().put("calc_points", true)
    val fullRes = ghr.route(req)
    val res = fullRes.getBest()
    val pl = res.getPoints()
    val distance = res.getDistance()
    val millis = res.getTime()
    //println(distance)
    //println(millis)
  }
}
