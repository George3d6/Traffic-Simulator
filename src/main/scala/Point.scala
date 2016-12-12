import scala.math.cos
import scala.math.sin

class Point(val latitude : Double, val longitude : Double) {
  //We're in germany and I don't want to make complex calculations, so
  //for now these are constants no matter the exact position of the car
  //For a better simulation these should be provided/compute or a different
  //formula alltogether should be used
  private val longituide0 : Double= 10
  private val latitude1 : Double = 50

  def getX() = {
    (longitude-longituide0)*cos(latitude1)
  }
  def getY() = {
    latitude - latitude1
  }

  //Construct a point using x, y coordinates instead of lattitude and longitude
  def this(x : Double,y : Double, doIt : Boolean) {
    this(( ( x/cos(latitude1) ) + longituide0 ), (y + longituide0))
  }
}
