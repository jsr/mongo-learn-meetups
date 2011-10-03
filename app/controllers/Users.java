package controllers;

import utils.*; 
import models.*;

public class Users extends BaseController {

  public static void novus() {
    render();
  }
  
  public static void save(User user) {
    renderText("todo");
  }
}