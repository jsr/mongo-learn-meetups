package controllers;

import utils.*; 
import models.*;
import play.mvc.Util;
import play.mvc.Before;
import org.bson.types.ObjectId;

public class Meetups extends BaseController {

  @Before(unless={"view", "find", "recent"})
  private static void before() {
    User user =  (User)User.findById(session.get("uid"));
    if (user == null) {
      redirect("/sessions/new");
    }
    renderArgs.put("user", user);
  }

  public static void novus() {
    renderArgs.put("meetup", new Meetup());
    renderTemplate("meetups/edit.html");
  }

  public static void edit(ObjectId id) {
    //todo load the meetup
    render();
  }

  public static void save(Meetup meetup) {
    //getUser is defined in the BaseController
    meetup.user = (ObjectId)getUser().getId();
    if(meetup.validateAndSave()) {
      redirect("/meetups/view/" + meetup.getId());
    }
  
    //we have a validation error
    renderArgs.put("meetup", meetup);
    validationError(new FieldError(validation.errors()));
    renderTemplate("Meetups/edit.html");
  }

  public static void view(ObjectId id) {
    //todo load the meetup
  }

  public static void find() {
    renderText("this is for later");
  }

  public static void recent(int page) {
    renderText("this is for later");
  }
}