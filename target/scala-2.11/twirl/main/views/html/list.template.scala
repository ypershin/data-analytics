
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
object list extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[List[Product],String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(products: List[Product],title:String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.40*/("""
   """),_display_(/*2.5*/main(title)/*2.16*/ {_display_(Seq[Any](format.raw/*2.18*/("""

    """),format.raw/*4.5*/("""<dl class="products">
        """),_display_(/*5.10*/for(product <- products) yield /*5.34*/ {_display_(Seq[Any](format.raw/*5.36*/("""
        """),format.raw/*6.9*/("""<dt>"""),_display_(/*6.14*/product/*6.21*/.name),format.raw/*6.26*/("""</dt>
        <dd>"""),_display_(/*7.14*/product/*7.21*/.description),format.raw/*7.33*/("""</dd>
    """)))}),format.raw/*8.6*/("""
    """),format.raw/*9.5*/("""</dl>

    <table border="1">
        <tr><td><b>Name</b></td><td><b>Description</b></td></b></tr>
        """),_display_(/*13.10*/for(product <- products) yield /*13.34*/ {_display_(Seq[Any](format.raw/*13.36*/("""
        """),format.raw/*14.9*/("""<tr>
        <td>"""),_display_(/*15.14*/product/*15.21*/.name),format.raw/*15.26*/("""</td>
           <td>"""),_display_(/*16.17*/product/*16.24*/.description),format.raw/*16.36*/("""</td>
         </tr>
        """)))}),format.raw/*18.10*/("""
    """),format.raw/*19.5*/("""</table>

""")))}),format.raw/*21.2*/("""
"""))}
  }

  def render(products:List[Product],title:String): play.twirl.api.HtmlFormat.Appendable = apply(products,title)

  def f:((List[Product],String) => play.twirl.api.HtmlFormat.Appendable) = (products,title) => apply(products,title)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Tue Oct 14 21:41:12 MDT 2014
                  SOURCE: /home/yury/Play/data-analytics/app/views/list.scala.html
                  HASH: 3db66e3d6670f36a6632277ca3fdba79b99bf4f8
                  MATRIX: 518->1|644->39|674->44|693->55|732->57|764->63|821->94|860->118|899->120|934->129|965->134|980->141|1005->146|1050->165|1065->172|1097->184|1137->195|1168->200|1303->308|1343->332|1383->334|1419->343|1464->361|1480->368|1506->373|1555->395|1571->402|1604->414|1665->444|1697->449|1738->460
                  LINES: 19->1|22->1|23->2|23->2|23->2|25->4|26->5|26->5|26->5|27->6|27->6|27->6|27->6|28->7|28->7|28->7|29->8|30->9|34->13|34->13|34->13|35->14|36->15|36->15|36->15|37->16|37->16|37->16|39->18|40->19|42->21
                  -- GENERATED --
              */
          