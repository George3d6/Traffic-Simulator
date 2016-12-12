object TraficSimulator {
  def main(args: Array[String]) = {
    val router = new RouteApiWrapper()
    router.returnPoint()
  }
}
