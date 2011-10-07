package jobs;

import java.util.*;
import play.jobs.*;
import com.mongodb.*;
import play.modules.morphia.MorphiaPlugin;
 
@Every("60s")
public class TagBuilderJob extends Job {
  private final static String map = "function() { this.tags.forEach(function(tag) { emit(tag, 1); })};";
  private final static String reduce = "function(key, values) {" +
    "var sum = 0;" +
    "values.forEach(function(value) { sum += value; });" +
    "return sum;" +
  "};";
  
  public void doJob() {
    DBObject query = new BasicDBObject();
    query.put("tags", new BasicDBObject("$exists", true));
    query.put("date", new BasicDBObject("$gte", new Date()));
    MorphiaPlugin.ds().getCollection(models.Meetup.class).mapReduce(map, reduce, "tags", query);
  }
}