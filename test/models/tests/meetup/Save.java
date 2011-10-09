package models.tests.meetup;

import models.*;
import org.junit.*;
import java.util.*;
import fixtures.ModelFixture;

public class Save extends ModelFixture {

  @Test
  public void newMeetupsAreMarkedAsRecent() {
    Meetup meetup = new Meetup();
    meetup.save();
    Recent recent = Recent.find().first();
    assertEquals(meetup.getId(), recent.getId());
  }
  
  @Test
  public void updatedMeetupsArenNotStoredInRecents() {
    Meetup oldest = new Meetup();
    oldest.save();
    for(int i = 0; i < 25; ++i) {
      Meetup meetup = new Meetup();
      meetup.save();
    }
    oldest.save();
    assertEquals(0, Recent.filter("_id = ", oldest.getId()).count());
  }
  
  @Test
  public void onlyTracksTheMostRecent20() {
    Meetup oldest = new Meetup();
    oldest.save();
    for(int i = 0; i < 25; ++i) {
      Meetup meetup = new Meetup();
      meetup.save();
    }
    assertEquals(25, Recent.find().count());
    assertEquals(0, Recent.filter("_id = ", oldest.getId()).count());
  }
  
  @Test
  public void savesTheMeetup() {
    new Meetup().save();
    assertEquals(1, Meetup.find().count()); //now I'm just being lazy
  }
}
