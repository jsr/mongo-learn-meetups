package models;

import utils.*;
import java.util.*;
import org.bson.types.ObjectId;
import play.modules.morphia.Model;
import com.google.code.morphia.annotations.*;

@Entity(value="recents", noClassnameStored=true, cap=@CappedAt(value=2097152, count=25))
public class Recent extends Model {

  public Recent(ObjectId meetupId) {
    this.setId(meetupId);
  }
  
  
	public static PagedResult<Meetup> find(int page) {
		int limit = 5;
		int offset = (page - 1) * limit;
	  MorphiaQuery query = find().disableValidation().limit(5).offset(offset).order("-$natural");
	  List<Recent> recent = query.fetchAll();
	  List<Meetup> meetups = Meetup.filter("_id in", toIdArray(recent)).fetchAll();
	  return new PagedResult<Meetup>(query.countAll(), page, 5, sort(recent, meetups));
	}
	
	private static ObjectId[] toIdArray(List<Recent> recent) {
	  ObjectId[] ids = new ObjectId[recent.size()];
	  for(int i = 0; i < recent.size(); ++i) {
	    ids[i] = (ObjectId)recent.get(i).getId();
	  }
	  return ids;
	}
	
  private static List<Meetup> sort(List<Recent> recents, List<Meetup> meetups) {
    List<Meetup> sorted = new ArrayList<Meetup>(meetups.size());
    for(Recent recent : recents) {
      int index = findIndex((ObjectId)recent.getId(), meetups);
      if (index == -1) { continue; }
      sorted.add(meetups.remove(index));
    }
    return sorted;
  }
  
  private static int findIndex(ObjectId id, List<Meetup> meetups) {
    for (int i = 0; i < meetups.size(); ++i) {
      if (meetups.get(i).getId().equals(id)) {
        return i;
      }
    }
    return -1;
  }
}