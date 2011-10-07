package controllers;

import utils.*;
import models.*;
import play.mvc.Util;
import play.mvc.Before;
import play.mvc.Controller;
import org.bson.types.ObjectId;
import com.google.gson.GsonBuilder;
import play.modules.morphia.utils.ObjectIdGsonAdapter;

public class BaseController extends Controller {
  
  protected final static GsonBuilder gsonBuilder;
  static {
    gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    gsonBuilder.registerTypeAdapter(ObjectId.class, new ObjectIdGsonAdapter());
    gsonBuilder.registerTypeAdapter(PagedResult.class, new PagedResultGsonAdapter());
  }
  
  @Before
  //load the user for the view
  private static void loadUser() {
    if (session.get("uid") == null) { return; }
    renderArgs.put("user", User.findById(session.get("uid")));
  }
  
  @Util
  protected static void signin(User user) {
    session.put("uid", user.getId());
    redirect("/");
  }
  
  @Util
  protected static void addError(String error) {
    renderArgs.put("error", error);
  }
  
  @Util
  protected static void validationError(FieldError error) {
    renderArgs.put("validation", error);
  }
  
  @Util
  protected static User getUser() {
    return (User)renderArgs.get("user");
  }

  @Util
  protected static void renderJSON(Object object) {  
    renderJSON(object, 200);
  }
    
  @Util
  protected static void renderJSON(Object object, int status) {  
    response.contentType = "application/json";
    response.status = status;
    renderText(toJson(object));
  }
  
  @Util
  protected static String toJson(Object object) {
    return gsonBuilder.create().toJson(object);
  }
}