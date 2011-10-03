package controllers;

import play.mvc.*;
import models.User;

public class Sessions extends BaseController {

  public static void novus() {
    render();
  }
  
  public static void save(String name, String password) {
    renderText("todo");
  }
  
  public static void logout() {
    session.remove("uid");
    redirect("/");
  }
}