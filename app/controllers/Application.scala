package controllers

import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.iteratee.Enumerator
import play.api.mvc._
import models._



object Application extends Controller {

  def index = Action { implicit request =>
    println(request.session)
    Ok(views.html.index(""))
  }


  val loginForm:Form[User] = Form {
    mapping (
      "username" -> text,
      "password" -> text
    ) (User.apply)(User.unapply)
  }

  def authenticate = Action { implicit requets =>


//    val login = loginForm.bindFromRequest().get
//    val user:Option[User] = User.find(login.username)
//
//    user match {
//      case Some(user) => Redirect(routes.Application.test).withSession(Security.username -> user.username, "access" -> user.getAccess)
//      case None => Forbidden("Please try again")
//    }


    loginForm.bindFromRequest.fold(
      formWithErrors => Forbidden("Please try again"),
      user => Redirect(routes.Application.index).withSession(Security.username -> user.username, "access" -> user.getAccess)
    )



  }

  def test = Action { implicit request =>
    request.session.get("username") match {
      case Some(uname) => Ok(s"hell ${uname} with access=${request.session.get("access").get.toString}")
      case None => Ok("hello No one")
    }
  }


  def logout = Action {
    Redirect(routes.Application.index).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }


  def list = Action { implicit request =>
    val products = Product.findAll
    Ok(views.html.list(products,"Test"))
  }


  def getPdf(id:Int) = Action  {

     if(id==0)
     Ok.sendFile(
       content = new java.io.File("/home/yury/Documents/RT - YouTube.pdf"),
       inline = true
     )
    else if(id==1)
      Ok.sendFile(
        content = new java.io.File("/home/yury/Documents/SAS versus R - Butler Analytics 070814.pdf"),
        inline = true
      )
    else
       Ok.sendFile(
         content = new java.io.File("/home/yury/Documents/data.jpg"),
         inline = true
       )

  }



}