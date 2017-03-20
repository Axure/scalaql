package example

import info.axurez.scalaql.ScalaQL

/**
  * Created by zhenghu on 17-3-14.
  */

/**
  * The hello-world-ish examples of the DSL.
  */
object HelloWorld extends ScalaQL {
  def main(args: Array[String]): Unit = {
    '> SELECT 'name FROM 'user WHERE 'haha END; // semicolon is compulsory. TODO: fix the postfix thing.
    '> SELECT ('name, 'h) FROM 'user WHERE 'haha END; // TODO: It seems the Scala plugin on IntelliJ IDEA cannot parse this.
    '> SELECT ('name, 'h, 'h1) FROM 'user WHERE 'haha END;
    '> SELECT ('name, 'h, 'h1, 'h2) FROM 'user WHERE 'haha END;
    '> SELECT * FROM 'user WHERE 'haha END; // semicolon is compulsory. TODO: fix the postfix thing.
    '> SELECT 'height FROM 'user END; // TODO: ponder on the combination/implicit conversion/type inference of 2 or more objects.
    '> SELECT 'age FROM; // TODO: detect such errors. Currently no error reported.
    //    'ha SELECT 'name
    //SELECT
    //SELECT
    //UPDATE
    GO


  }
}
