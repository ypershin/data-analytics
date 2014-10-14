// @SOURCE:/home/yury/Play/data-analytics/conf/routes
// @HASH:bf2ec194f5d00faf01aaec75ad3a2c10863732da
// @DATE:Mon Oct 13 20:55:46 MDT 2014


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
private[this] lazy val controllers_Application_test1_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("test"))))
private[this] lazy val controllers_Application_test1_invoker = createInvoker(
controllers.Application.test,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "test", Nil,"GET", """""", Routes.prefix + """test"""))
        

// @LINE:10
private[this] lazy val controllers_Application_authenticate2_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("login"))))
private[this] lazy val controllers_Application_authenticate2_invoker = createInvoker(
controllers.Application.authenticate,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "authenticate", Nil,"POST", """""", Routes.prefix + """login"""))
        

// @LINE:11
private[this] lazy val controllers_Application_logout3_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("logout"))))
private[this] lazy val controllers_Application_logout3_invoker = createInvoker(
controllers.Application.logout,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "logout", Nil,"POST", """""", Routes.prefix + """logout"""))
        

// @LINE:16
private[this] lazy val controllers_Assets_at4_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_at4_invoker = createInvoker(
controllers.Assets.at(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """test""","""controllers.Application.test"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """login""","""controllers.Application.authenticate"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """logout""","""controllers.Application.logout"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
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
case controllers_Application_test1_route(params) => {
   call { 
        controllers_Application_test1_invoker.call(controllers.Application.test)
   }
}
        

// @LINE:10
case controllers_Application_authenticate2_route(params) => {
   call { 
        controllers_Application_authenticate2_invoker.call(controllers.Application.authenticate)
   }
}
        

// @LINE:11
case controllers_Application_logout3_route(params) => {
   call { 
        controllers_Application_logout3_invoker.call(controllers.Application.logout)
   }
}
        

// @LINE:16
case controllers_Assets_at4_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at4_invoker.call(controllers.Assets.at(path, file))
   }
}
        
}

}
     