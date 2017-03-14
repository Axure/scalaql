package info.axurez.scalaql

/**
  * Created by zhenghu on 17-3-14.
  */
class ScalaQL {
  abstract sealed trait BaseLine
  case class SELECT(to: Any) extends BaseLine
  case class QueryBuilder() {
    object SELECT {
      def apply(): SELECT = new SELECT()
    }
  }
}
