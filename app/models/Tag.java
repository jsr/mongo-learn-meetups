package models;
 
import play.modules.morphia.Model;
import com.google.code.morphia.annotations.*;

@Entity(value="tags", noClassnameStored=true)
public class Tag extends Model {
  @Id
  public String _id;
  
  public int value;
}