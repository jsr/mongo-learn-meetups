package models.tests.meetup;

import models.*;
import org.junit.*;
import java.util.*;
import fixtures.ValidationFixture;

public class Validation extends ValidationFixture {
         
  @Test
  public void isInvalidWhenRequiredFieldsAreMissing() {
    Meetup meetup = new Meetup();
    validation.valid(meetup);
    assertError("name", "Required");
    assertError("city", "Required");
    assertError("address", "Required");
    assertError("date", "Required");
    assertError("duration", "Required");
    assertError("user", "Required");
    assertError("coordinates", "Required");
  }
  
  @Test
  public void isInvalidWhenThereAreTooManyTags() {
    Meetup meetup = new Meetup();
    meetup.tags = new HashSet<String>();
    for(int i = 0; i < 7; ++i) { meetup.tags.add("tag" + i);}
    validation.valid(meetup);
    assertError("tags", "Can't have more than 5");
  }
  
  @Test
  public void isInvalidWhenATagValueIsTooLong() {
    Meetup meetup = new Meetup();
    meetup.tags = new HashSet<String>();
    meetup.tags.add(createString(16));
    validation.valid(meetup);
    assertError("tags", "A tag should be less than 15 characters");
  }
  
  @Test
  public void isInvalidWhenCoordinatesHasASingleValue() {
    Meetup meetup = new Meetup();
    meetup.coordinates = new ArrayList<Float>();
    meetup.coordinates.add(23f);
    validation.valid(meetup);
    assertError("coordinates", "Should have 2 values");
  }
  
  @Test
  public void isInvalidWhenCoordinatesHasMoreThanTwo() {
    Meetup meetup = new Meetup();
    meetup.coordinates = new ArrayList<Float>();
    meetup.coordinates.add(1f);
    meetup.coordinates.add(2f);
    meetup.coordinates.add(3f);
    validation.valid(meetup);
    assertError("coordinates", "Should have 2 values");
  }
  
  @Test
  public void isInvalidWhenFieldsAreTooLong() {
    Meetup meetup = new Meetup();
    meetup.name = createString(51);
    meetup.city = createString(26);
    meetup.address = createString(76);
    meetup.link = createString(101);
    meetup.description = createString(2201);
    meetup.tags = new HashSet<String>(Arrays.asList(new String[]{"a", "b", "c", "d", "e", "f'"}));
    validation.valid(meetup);
    assertError("name", "Maximum size is 50");
    assertError("city", "Maximum size is 25");
    assertError("address", "Maximum size is 75");
    assertError("link", "Maximum size is 100");
    assertError("description", "Maximum size is 2200");
    assertError("tags", "Can't have more than 5");
  }
  
  @Test
  public void hasNoErrorsWhenValid() {
    Meetup meetup = new Meetup();
    meetup.name = "Spice Sampling";
    meetup.city = "Arrakeen";
    meetup.address = "Grand Palace";
    meetup.link = "http://spicesampling12304.com";
    meetup.description = "BYOS";
    meetup.tags = new HashSet<String>(Arrays.asList(new String[]{"a", "b", "c", "d", "e"}));
    meetup.duration = 5.5f;
    meetup.date = new Date(12304, 10, 2, 3, 4, 5);
    assertNoErrors();
  }
}
