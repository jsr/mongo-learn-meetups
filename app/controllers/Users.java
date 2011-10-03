package controllers;

import utils.*; 
import models.*;

public class Users extends BaseController {

  public static void novus() {
    render();
  }
  
  public static void save(User user) {
    try
    {
      if(user != null && user.validateAndSave()) {
        signin(user);
      }  
      validationError(new FieldError(validation.errors()));
    } catch (DuplicateKeyException ex) {
      validationError(new FieldError("user.name", "is already taken"));
    }
    renderTemplate("Users/novus.html");
  }
}