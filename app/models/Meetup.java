package models;

import utils.*;
import java.util.*;
import play.data.validation.*;
import org.bson.types.ObjectId;
import play.modules.morphia.Model;
import com.google.code.morphia.annotations.*;

@Entity(value="meetups", noClassnameStored=true)
public class Meetup extends Model {
  @Required @MaxSize(50)
  public String name;

  @Required @MaxSize(25)
  public String city;

  @Required @MaxSize(75)
  public String address;

  @Required
  public Date date;

  @MaxSize(100)
  public String link;

  @Required
  public Float duration;

  @MaxSize(2200)
  public String description;

  @Required
  public ObjectId user;
  
  @Required @ExactListSize(value=2, message="Should have 2 values")
  public List<Float> coordinates;

  @MaxListSize(value=5, message="Can't have more than 5") @MaxListItemSize(value=15, message="A tag should be less than 15 characters")
  public Set<String> tags;
  
  public static PagedResult findUpcoming(int page) {
    int limit = 5;
    int offset = (page - 1) * limit;
    MorphiaQuery query = filter("date >=", new Date()).limit(limit).offset(offset);
    List<Meetup> meetups = query.asList();
    return new PagedResult<Meetup>(query.countAll(), page, limit, meetups); 
  }
}