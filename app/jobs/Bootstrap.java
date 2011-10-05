package jobs;

import play.jobs.*; 
import play.modules.morphia.MorphiaPlugin;

@OnApplicationStart
public class Bootstrap extends Job {  
  public void doJob() {
    MorphiaPlugin.ds().ensureCaps();
  }
}