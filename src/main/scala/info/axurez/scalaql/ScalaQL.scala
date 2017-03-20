package info.axurez.scalaql
import scala.collection.immutable
import scala.collection.mutable
/**
  * Created by zhenghu on 17-3-14.
  */
class ScalaQL {
  
  abstract sealed class BaseLine
  
  val queries: mutable.Queue[BaseLine] = mutable.Queue[BaseLine]()

  case class SelectQuery(columnName: Symbol) extends BaseLine
  case class UpdateQuery(to: Any) extends BaseLine
  case class InsertQuery(to: Any) extends BaseLine
  case class DeleteQuery(to: Any) extends BaseLine
  
  abstract sealed class KEYWORD

  case class QueryBuilder2() {
    
    def apply(columnName: Symbol): SelectQuery = {
        val newSelect = SelectQuery(columnName)
        queries.enqueue(newSelect)
        newSelect
      }
  }

  case class QueryBuilder(symbol: Symbol) {
    case object SELECT extends KEYWORD {
      def apply(columnName: Symbol): SelectQuery = {
        val newSelect = SelectQuery(columnName)
        queries.enqueue(newSelect)
        newSelect
      }
    }

    case object UPDATE extends KEYWORD {
      def apply(): UpdateQuery = {
        val newSelect = new UpdateQuery()
        queries.enqueue(newSelect)
        newSelect
      }
    }

    case object DELETE extends KEYWORD {
      def apply(): DeleteQuery = {
        val newSelect = new DeleteQuery()
        queries.enqueue(newSelect)
        newSelect
      }
    }

    case object INSERT extends KEYWORD {
      def apply(): InsertQuery = {
        val newSelect = InsertQuery()
        queries.enqueue(newSelect)
        newSelect
      }
    }
  }

  def GO = execute()

  private def execute() {
    while (queries.nonEmpty) {
      val query = queries.dequeue
      query match {
        case SelectQuery(columnName) =>
          println(columnName)
        case _ =>
      }
    }
  }

  implicit def symbol2QueryBuilder(symbol: Symbol): QueryBuilder = symbol match {
    case '> => QueryBuilder(symbol)
    case _ => throw new Exception("Invalid")
  }
  //implicit def Keyword2LineBuilder(keyword: KEYWORD) = QueryBuilder2()
 // implicit def Line2LineBuilder(line: Int) = QueryBuilder()
}
