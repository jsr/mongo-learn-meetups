package controllers;

import models.*;

public class Home extends BaseController {
  public static void index() {
    renderArgs.put("tags", Tag.find().order("_id").asList());
    render();
  }
  
  public static void about() {
    render();
  }
}