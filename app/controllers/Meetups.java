package controllers;

import utils.*; 
import models.*;
import play.mvc.Util;
import play.mvc.Before;
import org.bson.types.ObjectId;

public class Meetups extends BaseController {

	@Before(unless={"view", "findUpcoming", "findRecent", "find"})
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
    renderArgs.put("meetup", loadMeetupAsCreator(id));
    render();
  }

  public static void save(Meetup meetup) {
    if (meetup.getId() == null) {
      meetup.user = (ObjectId)getUser().getId();
    } else {
      loadMeetupAsCreator((ObjectId)meetup.getId());
    }
    if(meetup.validateAndSave()) {
      redirect("/meetups/view/" + meetup.getId());
    }
  
    //we have a validation error
    renderArgs.put("meetup", meetup);
    validationError(new FieldError(validation.errors()));
    renderTemplate("Meetups/edit.html");
  }

  public static void view(ObjectId id) {
    Meetup meetup = Meetup.findById(id);
    if (meetup == null) {
      redirect("/");
    }
    renderArgs.put("meetup", meetup);
    render();
  }

  public static void findUpcoming(int page) {
    renderJSON(Meetup.findUpcoming(page));
  }

  public static void findRecent(int page) {
    renderJSON(Recent.find(page));
  }

  public static void find(float x, float y) {
    renderJSON(Meetup.find(x, y));
  }
  
  @Util
  private static Meetup loadMeetupAsCreator(ObjectId id) {
    Meetup meetup = Meetup.findById(id);
    if (meetup == null || !meetup.user.equals(getUser().getId())) {
      redirect("/");
    }
    return meetup;
  }
}