
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._

import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._

/**/
object index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(message: String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.19*/("""

"""),_display_(/*3.2*/main("Welcome to Play")/*3.25*/ {_display_(Seq[Any](format.raw/*3.27*/("""

"""),format.raw/*5.1*/("""<form action=""""),_display_(/*5.16*/routes/*5.22*/.Application.authenticate),format.raw/*5.47*/("""" method="post">
    <input name="username" type="text" />
    <br/>
    <input name="password" type="password" />
    <br/>
    <button type="submit">Login</button>
</form>
<br/>
<form action=""""),_display_(/*13.16*/routes/*13.22*/.Application.logout),format.raw/*13.41*/("""" method="post">
    <button type="submit">Logout</button>
</form>

""")))}),format.raw/*17.2*/("""
"""))}
  }

  def render(message:String): play.twirl.api.HtmlFormat.Appendable = apply(message)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (message) => apply(message)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Mon Oct 13 20:34:32 MDT 2014
                  SOURCE: /home/yury/Play/data-analytics/app/views/index.scala.html
                  HASH: fe49f8a7cec3ffa14d6deae6a683759c687a5015
                  MATRIX: 505->1|610->18|638->21|669->44|708->46|736->48|777->63|791->69|836->94|1058->289|1073->295|1113->314|1212->383
                  LINES: 19->1|22->1|24->3|24->3|24->3|26->5|26->5|26->5|26->5|34->13|34->13|34->13|38->17
                  -- GENERATED --
              */
          