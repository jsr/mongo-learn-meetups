$(document).ready(function() {
  $('#upcoming ul').dynamicTable({go: '/meetups/view/:id', template: '#meetups-item-template', url: '/meetups/findUpcoming?page=:page'});
  $('#recent ul').dynamicTable({go: '/meetups/view/:id', template: '#meetups-item-template', url: '/meetups/findRecent?page=:page'});

  $('#tagFilterPicker a').click(pickTags);
  var $tagChoice =  $('#tagChoice');
  
  $tagChoice.find('a').click(tagsPicked);
  var tags = [];
  function pickTags() {
    $tagChoice.show();
  }
  function tagsPicked() {
    tags = [];
    $tagChoice.find('input:checked').each(function(i, input) { tags.push(input.value); });
    $('#tagFilterPicker a').text(tags.length == 0 ? 'all' : tags.join(', '));
    $tagChoice.hide();
    
    loadMapMeetups();
  }
  $(document).keydown(function(e) {
    if ($tagChoice.is(':hidden')) { return; }
    if (e.keyCode == 27) { $tagChoice.hide(); }
    if (e.keyCode == 13) { tagsPicked(); }
  });
    
  var center = new google.maps.LatLng(35, -95);
  var options = {zoom: 4, center: center, width:500, height:600, mapTypeId: google.maps.MapTypeId.ROADMAP, disableDefaultUI:false};
  var map = new google.maps.Map(document.getElementById('map'), options);
  var infoWindow = new google.maps.InfoWindow();
  var markers = [];
  google.maps.event.addListener(map, 'dragend', loadMapMeetups);
  
  function loadMapMeetups() {
    var center = map.getCenter();
    $.get('/meetups/find', {'x': center.lng(), 'y': center.lat(), 'tags': tags}, gotMapMeetups, 'json');
  }
  
  function gotMapMeetups(meetups) {
    clearExistingMarkers();
    for(var i = 0; i < meetups.length; ++i) {
      var meetup = meetups[i];
      var point = new google.maps.LatLng(meetup.coordinates[1], meetup.coordinates[0])
      var marker = new google.maps.Marker({position: point, map: map, clickable: true, raiseOnDrag:false});
      markers.push(marker);
      marker._meetup = meetup;
      google.maps.event.addListener(marker, 'click', showMeetup);
    }
  }
  function clearExistingMarkers() {
    for(var i = 0; i < markers.length; ++i) {
      markers[i].setMap(null);
    }
    markers = []
  }
  
  var template = _.template($('#meetups-marker-template').html());
  function showMeetup(e) {
    infoWindow.setContent(template({model: this._meetup}));
    infoWindow.open(map, this);
  }
  
  loadMapMeetups();
});