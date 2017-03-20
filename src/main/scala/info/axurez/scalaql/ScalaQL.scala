package info.axurez.scalaql
import scala.collection.immutable
import scala.collection.mutable
/**
  * Created by zhenghu on 17-3-14.
  */
class ScalaQL {
  
  abstract sealed class BaseLine
  
  val queries: mutable.Queue[BaseLine] = mutable.Queue[BaseLine]()

  case class ConditionedSelectQuery(columnName: Symbol, tableName: Symbol, condition: Symbol) extends BaseLine
  case class SelectQuery(columnName: Symbol, tableName: Symbol) extends BaseLine
  case class UpdateQuery(to: Any) extends BaseLine
  case class InsertQuery(to: Any) extends BaseLine
  case class DeleteQuery(to: Any) extends BaseLine

  abstract sealed class KEYWORD

  case class QueryBuilder(symbol: Symbol) {
    case object SELECT extends KEYWORD {
      def apply(columnName: Symbol): WithSelect = {
        val newSelect = WithSelect(columnName)
//        queries.enqueue(newSelect)
        newSelect
      }
    }

    case object UPDATE extends KEYWORD {
      def apply(): UpdateQuery = {
        val newSelect = UpdateQuery()
        queries.enqueue(newSelect)
        newSelect
      }
    }

    case object DELETE extends KEYWORD {
      def apply(): DeleteQuery = {
        val newSelect = DeleteQuery()
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

  case class WithSelect(columnName: Symbol) {
    object FROM {
      def apply(tableName: Symbol): WithFrom = {
        WithFrom(columnName, tableName)
      }
    }
  }

  case class WithFrom(columnName: Symbol, tableName: Symbol) {
    object WHERE {
      def apply(condition: Symbol): WithWhere = {
        WithWhere(columnName, tableName, condition)
      }
    }

    def END : Unit = {
      val newSelect = SelectQuery(columnName, tableName)
      queries.enqueue(newSelect)
    }
  }

  case class WithWhere(columnName: Symbol, tableName: Symbol, condition: Symbol) {
    def END : Unit = {
      val newSelect = ConditionedSelectQuery(columnName: Symbol, tableName: Symbol, condition: Symbol)
      queries.enqueue(newSelect)
    }
  }

  def GO() = execute()

  private def execute() {
    while (queries.nonEmpty) {
      val query = queries.dequeue
      query match {
        case ConditionedSelectQuery(columnName, tableName, condition) =>
          println(columnName, tableName, condition)
        case SelectQuery(columnName, tableName) =>
          println(columnName, tableName)
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
