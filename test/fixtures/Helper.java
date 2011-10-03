package fixtures;

import play.test.*;

public abstract class Helper {  
  public static void load(String file) {
    Fixtures.load("data/" + file + ".yml");
  }
}