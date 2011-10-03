package fixtures;

import utils.*;
import org.junit.*;
import play.test.*;
import play.modules.morphia.MorphiaPlugin;

public abstract class ModelFixture extends UnitTest {
  
  @Before
  public void before() {
    Helper.deleteDatabase();
    MorphiaPlugin.ds().ensureCaps();
  }

}