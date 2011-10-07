package models;

import utils.*;
import play.data.validation.*;
import com.google.code.morphia.*;
import play.modules.morphia.Model;
import com.google.code.morphia.annotations.*;
import com.mongodb.MongoException.DuplicateKey;

@Entity(value="users", noClassnameStored=true)
public class User extends Model {  
  @Id
  public String _id;
  
  @Required @MaxSize(20) @Indexed(unique=true)
  public String name;
  
  @Required @MinSize(8)
  public String password;
  
  public static User loadFromCredentials(String name, String password) {
    if (name == null || password == null) { return null; }

    User user = findById(name.toLowerCase());
    return user == null || !BCrypt.checkpw(password, user.password) ? null : user;
  }
  
  @Override
  public User save() {
    try {
      _id = name.toLowerCase();
      password = BCrypt.hashpw(password, BCrypt.gensalt());
      ((AdvancedDatastore)ds()).insert(this);
      return this;
    } catch(DuplicateKey e) {
      throw new DuplicateKeyException(e);
    }
  }
  
  @Override 
  protected void setId_(Object id) { _id = (String)id; }
  
  @Override 
  public String getId() {return _id;}
  
  protected static Object processId_(Object id) {return id.toString();}
  
  public String toString() {
    return name;
  }
}