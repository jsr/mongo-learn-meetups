package controllers;

import play.mvc.*;
import models.User;

public class Sessions extends BaseController {

  public static void novus() {
    render();
  }
  
  public static void save(String name, String password) {
    User user = User.loadFromCredentials(name, password);
    if (user != null) {
      signin(user);
    }
    addError("invalid name or password, please try again");
    renderTemplate("Sessions/novus.html");
  }
  
  public static void logout() {
    session.remove("uid");
    redirect("/");
  }
}