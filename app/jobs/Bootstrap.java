package jobs;

import play.jobs.*; 
import com.mongodb.*;
import play.modules.morphia.MorphiaPlugin;

@OnApplicationStart
public class Bootstrap extends Job {  
  public void doJob() {
    MorphiaPlugin.ds().ensureCaps();
    DBObject object = BasicDBObjectBuilder.start().add("coordinates", "2d").add("date", 1).get();
    MorphiaPlugin.ds().getCollection(models.Meetup.class).ensureIndex(object);
  }
}