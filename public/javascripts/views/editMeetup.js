$(document).ready(function() {
  var $form = $('#meetupForm');
  var $date = $form.find('[name="meetup.date"]').datePicker();
  var $time = $form.find('[name="meetup.time"]');
  var $coordinates = $form.find(':text[name="meetup.coordinates"]');

  $('#coordinatesPicker').dialog({width:'850px', height:'500px', oked: pickedCoordinates});
  $form.find('[name="meetup.address"]').change(getCoordinates);
  $('#pickCoordinates').click(pickCoordinates);

  var map = null;
  var marker = null;
  var center = new google.maps.LatLng(37.7793,-122.4192);
  var geocoder = new google.maps.Geocoder();
  function getCoordinates() {
    var address = $form.find('[name="meetup.address"]').val() + ',' + $form.find('[name="meetup.city"]').val();
    geocoder.geocode({address: address}, function(r) {
      if (r && r.length >= 1) {
        center = r[0].geometry.location;
      }
    });
  }
  function pickCoordinates() {
    if (map == null) {
      var options = {zoom: 14, center: center, mapTypeId: google.maps.MapTypeId.ROADMAP, disableDefaultUI:false};
      map = new google.maps.Map(document.getElementById('coordinatesPicker'), options);
      google.maps.event.addListener(map, 'click', function(e) {
        if (marker != null) { marker.setMap(null); }
        marker = new google.maps.Marker({ position: e.latLng, map: map});
      });
    }
    map.setCenter(center);
    $('#coordinatesPicker').dialog({command: 'show'});
    return false;
  }
  function pickedCoordinates() {
    $coordinates.val(marker.getPosition().lng() + ',' + marker.getPosition().lat())
  }

  $form.submit(function() {
    var date = $date.data('date') || new Date($date.val());
    if (!isNaN(date.getTime())) { 
      var timeParts = $time.val().split(':');
      if (timeParts.length == 2) {
        date.setHours(timeParts[0]);
        date.setMinutes(timeParts[1]);
      }
      $date.val($.ISO8601(date));
    }
    
    var parts = $coordinates.val().split(',');
    if (parts.length == 2) {
      $('#coordx').val(parts[0]);
      $('#coordy').val(parts[1]);
    } else {
      $('#coordx').attr("disabled", "disabled");
      $('#coordx').attr("disabled", "disabled");
    }
    $coordinates.attr("disabled", "disabled");
    
    var tags = $('#tags').val().split(',');
    for (var i = 0; i < tags.length; ++i) {
      var value = tags[i].replace(/^\s\s*/, '').replace(/\s\s*$/, '');
      $form.append($('<input>').attr({type: 'hidden', value: value, name: 'meetup.tags'}));
    }
    $('#tags').attr("disabled", "disabled");
  });
});