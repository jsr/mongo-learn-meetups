package fixtures;

import play.test.*;
import play.modules.morphia.MorphiaPlugin;

public abstract class Helper {  
  
  public static void deleteDatabase() {
    MorphiaFixtures.deleteDatabase();
    MorphiaPlugin.ds().ensureIndexes();
  }
  
  public static void load(String file) {
    Fixtures.load("data/" + file + ".yml");
  }
}