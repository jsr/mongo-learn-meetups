package models;

import utils.*;
import play.data.validation.*;
import play.modules.morphia.Model;
import com.google.code.morphia.annotations.*;
import com.mongodb.MongoException.DuplicateKey;

@Entity(value="users", noClassnameStored=true)
public class User extends Model {  
  @Required @MaxSize(20) @Indexed(unique=true)
  public String name;
  
  @Required @MinSize(8)
  public String password;
  
  public static User loadFromCredentials(String name, String password) {
    if (name == null || password == null) { return null; }

    User user = filter("name = ", name).first();
    return user == null || !BCrypt.checkpw(password, user.password) ? null : user;
  }
  
  @Override
  public User save() {
    try {
      password = BCrypt.hashpw(password, BCrypt.gensalt());
      return super.save();
    } catch(DuplicateKey e) {
      throw new DuplicateKeyException(e);
    }
  }
}