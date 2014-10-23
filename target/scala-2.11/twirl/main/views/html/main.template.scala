
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
object main extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.32*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(/*7.17*/title),format.raw/*7.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(/*8.54*/routes/*8.60*/.Assets.at("stylesheets/main.css")),format.raw/*8.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*9.59*/routes/*9.65*/.Assets.at("images/favicon.png")),format.raw/*9.97*/("""">
        <script src=""""),_display_(/*10.23*/routes/*10.29*/.Assets.at("javascripts/hello.js")),format.raw/*10.63*/("""" type="text/javascript"></script>
    </head>
    <body>
        """),_display_(/*13.10*/content),format.raw/*13.17*/("""

        """),format.raw/*15.9*/("""<p><a href=""""),_display_(/*15.22*/routes/*15.28*/.Application.getPdf(0)),format.raw/*15.50*/("""">RT - YouTube</a></p>

        <p><a href=""""),_display_(/*17.22*/routes/*17.28*/.Application.getPdf(1)),format.raw/*17.50*/("""">R vs SAS</a></p>

        <p><img src=""""),_display_(/*19.23*/routes/*19.29*/.Application.getPdf(2)),format.raw/*19.51*/(""""/></p>


    </body>
</html>
"""))}
  }

  def render(title:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Wed Oct 22 22:31:00 MDT 2014
                  SOURCE: /home/yury/Play/data-analytics/app/views/main.scala.html
                  HASH: 1f972d3b8a1ef9519d87ffbaa74b7900032db419
                  MATRIX: 509->1|627->31|655->33|732->84|757->89|845->151|859->157|913->191|1000->252|1014->258|1066->290|1118->315|1133->321|1188->355|1282->422|1310->429|1347->439|1387->452|1402->458|1445->480|1517->525|1532->531|1575->553|1644->595|1659->601|1702->623
                  LINES: 19->1|22->1|24->3|28->7|28->7|29->8|29->8|29->8|30->9|30->9|30->9|31->10|31->10|31->10|34->13|34->13|36->15|36->15|36->15|36->15|38->17|38->17|38->17|40->19|40->19|40->19
                  -- GENERATED --
              */
          