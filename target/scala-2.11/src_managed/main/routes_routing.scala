// @SOURCE:/home/yury/Play/data-analytics/conf/routes
// @HASH:e21a694149d0f99d2512fe0ca58a4562c4695088
// @DATE:Wed Oct 22 22:28:47 MDT 2014


import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString

object Routes extends Router.Routes {

import ReverseRouteContext.empty

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
private[this] lazy val controllers_Application_index0_invoker = createInvoker(
controllers.Application.index,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
        

// @LINE:7
private[this] lazy val controllers_Application_getPdf1_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("pdf"))))
private[this] lazy val controllers_Application_getPdf1_invoker = createInvoker(
controllers.Application.getPdf(fakeValue[Int]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "getPdf", Seq(classOf[Int]),"GET", """""", Routes.prefix + """pdf"""))
        

// @LINE:8
private[this] lazy val controllers_Application_list2_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("list"))))
private[this] lazy val controllers_Application_list2_invoker = createInvoker(
controllers.Application.list,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "list", Nil,"GET", """""", Routes.prefix + """list"""))
        

// @LINE:12
private[this] lazy val controllers_Application_authenticate3_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("login"))))
private[this] lazy val controllers_Application_authenticate3_invoker = createInvoker(
controllers.Application.authenticate,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "authenticate", Nil,"POST", """""", Routes.prefix + """login"""))
        

// @LINE:13
private[this] lazy val controllers_Application_logout4_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("logout"))))
private[this] lazy val controllers_Application_logout4_invoker = createInvoker(
controllers.Application.logout,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "logout", Nil,"POST", """""", Routes.prefix + """logout"""))
        

// @LINE:18
private[this] lazy val controllers_Assets_at5_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_at5_invoker = createInvoker(
controllers.Assets.at(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """pdf""","""controllers.Application.getPdf(id:Int)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """list""","""controllers.Application.list"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """login""","""controllers.Application.authenticate"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """logout""","""controllers.Application.logout"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]]
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0_route(params) => {
   call { 
        controllers_Application_index0_invoker.call(controllers.Application.index)
   }
}
        

// @LINE:7
case controllers_Application_getPdf1_route(params) => {
   call(params.fromQuery[Int]("id", None)) { (id) =>
        controllers_Application_getPdf1_invoker.call(controllers.Application.getPdf(id))
   }
}
        

// @LINE:8
case controllers_Application_list2_route(params) => {
   call { 
        controllers_Application_list2_invoker.call(controllers.Application.list)
   }
}
        

// @LINE:12
case controllers_Application_authenticate3_route(params) => {
   call { 
        controllers_Application_authenticate3_invoker.call(controllers.Application.authenticate)
   }
}
        

// @LINE:13
case controllers_Application_logout4_route(params) => {
   call { 
        controllers_Application_logout4_invoker.call(controllers.Application.logout)
   }
}
        

// @LINE:18
case controllers_Assets_at5_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at5_invoker.call(controllers.Assets.at(path, file))
   }
}
        
}

}
     