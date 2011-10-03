package fixtures;

import utils.*;
import models.*;
import org.junit.*;
import java.util.*;
import play.mvc.Http.*;
import com.google.gson.*;
import java.util.regex.*;
import java.net.URLDecoder;
import play.mvc.Http.Response;

public abstract class ControllerFixture extends FunctionalTest {
  
  @Before
  public void before() {
    Helper.deleteDatabase();
    Helper.load("user-jessica");
  }
  
  public void assertValidation(String field, String message) {
    FieldError errors = (FieldError)renderArgs("validation");
    assertEquals(message, errors.get(field));
  }
  
  public void assertError(String message) {
    assertEquals(message, renderArgs("error"));
  }
  
  public void assertRedirectsTo(String expected, Response response) {
    assertHeaderEquals("Location", expected, response);
  }
  
  public void assertMustBeLoggedIn(Response response) {
    assertRedirectsTo("/sessions/new", response);
  }
  
  public JsonObject deserializeResponse(Response response) {
    String json = getContent(response).toString();
    return new JsonParser().parse(json).getAsJsonObject();
  }
  
  public Response LoggedInGET(String url) {
    Request request = newRequest();
    request.cookies = login().cookies;
    return GET(request, url);
  }
  
  public Response LoggedInPOST(String url) {
    Request request = newRequest();
    request.cookies = login().cookies;
    return POST(request, url);
  }
  
  //there doesn't seem to be any way to take direct control over sessions during functional tests
  //therefor, a simple way to log the user in is to actually log in
  //from here, the response.cookies can be copied over to a request to make this ugly mess work
  public Response login() {
    return POST("/sessions?name=jessica&password=dukeleto");
  }
  
  //Manually since that seems like the only option available
  public String getSession(Response response, String key) {
    Pattern sessionParser = Pattern.compile("\u0000([^:]*):([^\u0000]*)\u0000");
    String value = response.cookies.get("meetups_SESSION").value;
    String sessionData = URLDecoder.decode(value.substring(value.indexOf("-") + 1));
    Matcher matcher = sessionParser.matcher(sessionData);
    Map<String, String> session = new HashMap<String, String>();
    while (matcher.find()) {
      session.put(matcher.group(1), matcher.group(2));
    }
    return session.get(key);
  }
}