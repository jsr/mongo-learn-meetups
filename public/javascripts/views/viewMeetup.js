$(document).ready(function() {
  var parts = center.split(',');
  
  if (parts.length != 2 || isNaN(parseFloat(parts[0])) || isNaN(parseFloat(parts[1]))) { return; }
  
  center = new google.maps.LatLng(parseFloat(parts[1]), parseFloat(parts[0]));
  var options = {zoom: 12, center: center, width:500, height:600, mapTypeId: google.maps.MapTypeId.ROADMAP, disableDefaultUI:false};
  var map = null;
  
  $('#seeMap a').click(function() {
    $('#map').toggle();
    if (map == null) {
      map = new google.maps.Map(document.getElementById('map'), options);
      new google.maps.Marker({ position: center, map: map});
    }
    return false;
  }).parent().show();
});