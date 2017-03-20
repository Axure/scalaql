package info.axurez.scalaql
import scala.collection.immutable
import scala.collection.mutable
/**
  * Created by zhenghu on 17-3-14.
  */

/**
  * The class.
  */
class ScalaQL {

  /**
    * The base class for type matching.
    */
  abstract sealed class BaseLine
  
  val queries: mutable.Queue[BaseLine] = mutable.Queue[BaseLine]()

  case class ConditionedSelectQuery(columnName: Symbol, tableName: Symbol, condition: Symbol) extends BaseLine
  case class SelectQuery(columnName: Symbol, tableName: Symbol) extends BaseLine
  case class UpdateQuery(to: Any) extends BaseLine
  case class InsertQuery(to: Any) extends BaseLine
  case class DeleteQuery(to: Any) extends BaseLine

  abstract sealed class KEYWORD

  sealed trait * {

  }
  object * extends * {

  }

  /**
    * @todo Nothing is actually implemented since I don't master dependent typing.
    * @param keyword The starting keyword.
    */
  case class QueryBuilder2(keyword: KEYWORD) {

  }

  /**
    * The initial query builder class.
    * Converted from the starting symbol/
    * @param symbol The constructing symbol. Could only be `'>`, otherwise a runtime exception would be raised.
    */
  case class QueryBuilder(symbol: Symbol) {

    /**
      *
      */
    case object SELECT extends KEYWORD {
      def apply(columnName: Symbol): WithSelect = {
        val newSelect = WithSelect(columnName)
        newSelect
      }
      def apply(sym: *): WithSelect = {
        WithSelect('*)
      }
      def apply(columnNames: (Symbol, Symbol)): WithSelect = {
        WithSelect('two)
      }
      def apply(columnNames: (Symbol, Symbol, Symbol)): WithSelect = {
        WithSelect('three)
      }
      def apply(columnNames: (Symbol, Symbol, Symbol, Symbol)): WithSelect = {
        WithSelect('four)
      }

      /**
        * The apply method for 5 columns.
        * @param columnNames The names of the 5 columns
        * @return
        */
      def apply(columnNames: (Symbol, Symbol, Symbol, Symbol, Symbol)): WithSelect = {
        WithSelect('five)
      }
    }

    /**
      *
      */
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

    /**
      *
      */
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

  /**
    *
    */
  def GO() = execute()

  /**
    *
    */
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

  /**
    *
    * @param symbol The starting symbol.
    * @return
    */
  implicit def symbol2QueryBuilder(symbol: Symbol): QueryBuilder = symbol match {
    case '> => QueryBuilder(symbol)
    case _ => throw new Exception("Invalid")
  }

  /**
    * Implicit conversion of `KEYWORD` to `QueryBuilder2`.
    * Yet another query builder.
    * @param keyword The keyword converted.
    * @return
    */
  implicit def Keyword2LineBuilder(keyword: KEYWORD): QueryBuilder2 = QueryBuilder2(keyword) // It seems this is a dynamic way. TODO: probably could be static by dependent typing.
 // implicit def Line2LineBuilder(line: Int) = QueryBuilder()
}
