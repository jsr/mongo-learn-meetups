package fixtures;

import models.*;
import play.test.*;
import java.util.*;
import com.mongodb.*;
import play.modules.morphia.MorphiaPlugin;

public abstract class Helper {  
  
  public static void deleteDatabase() {
    MorphiaFixtures.deleteDatabase();
    MorphiaPlugin.ds().ensureIndexes();
    DBObject object = BasicDBObjectBuilder.start().add("coordinates", "2d").add("tags", 1).get();
    MorphiaPlugin.ds().getCollection(models.Meetup.class).ensureIndex(object);
  }
  
  public static void load(String file) {
    Fixtures.load("data/" + file + ".yml");
  }
  
  public static Meetup loadMeetupOne() {
    return loadMeetup(new Float[]{1f,2f});
  }
  
  public static Meetup loadMeetup(Float[] coordinates) {
    Meetup meetup = new Meetup();
    meetup.name = "Spice Teasers";
    meetup.city = "Caladan";
    meetup.address = "Sea Port 1";
    meetup.date = new Date(2015, 8, 1, 8, 0, 0);
    meetup.duration = 2f;
    meetup.description = "No Harkonnens";
    meetup.user = "jessica";
    meetup.coordinates = Arrays.asList(coordinates);
    meetup.save();
    return meetup;
  }
}