var months = new Array('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December');

(function($){
  $.fn.datePicker = function(opts) {
    var defaults = {selected: null, minimumDate: null, maximumDate: null};
    var abbreviations = new Array('Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec');
    var daySelector = 'td:not(.m):not(:empty)';
    
    return this.each(function() {
      if (this.datePicker) { return false; }
      var options = $.extend({}, defaults, opts);
      var $input = $(this);
      var $container = currentDate = mode = null;
      var self = {
        initialize: function() {               
          $input.click(function() { self.show(); return false; }).focus(self.show).keydown(function(e){ if (e.keyCode == 13) { self.entered(); return false; }});
          $(document).keydown(function(e) { if (e.keyCode == 27) { self.hide(); }}).click(self.hide);
          $container = self.initializeContainer().hide()
            .append(self.buildMonth(new Date()))
            .delegate(daySelector, 'click', self.clicked)
            .delegate('td:not(:empty)', 'hover', self.hover)
            .delegate('.prev', 'click', self.loadPrevious)
            .delegate('.next', 'click', self.loadNext)
            .delegate('.month', 'click', self.pickMonth)
            .delegate('.year', 'click', self.pickYear)
            .click(function(){return false;});
        },
        parseDate: function(value) { return new Date(value); },
        show: function() { $container.show(); },
        hide: function() { $container.hide(); },
        loadPrevious: function() {               
          $container.empty().append(self.buildMonth(new Date(currentDate.getFullYear(), currentDate.getMonth()-1, 1)));
        },
        loadNext: function() {          
          $container.empty().append(self.buildMonth(new Date(currentDate.getFullYear(), currentDate.getMonth()+1, 1)));
        },
        hover: function() {
          $(this).toggleClass('hover');
        },
        clicked: function() {
          var $cell = $(this);
          if (mode == 'month') {
            $container.empty().append(self.buildMonth(new Date(currentDate.getFullYear(), (4*($cell.parent().index()-1)) + $cell.index(), 1)));
          } else if (mode == 'year') {
            currentDate = new Date($cell.text(), 0, 1);
            self.pickMonth();
          } else {
            $container.find('td.selected').removeClass('selected');
            $cell.addClass('selected');
            var date = new Date(currentDate.getFullYear(), currentDate.getMonth(), $cell.text());
            $input.val($.prettyDate(date)).data('date', date).change().focus();
            if (options.selected != null) { options.selected(selected); } 
            
            self.hide(); 
          }
        }, 
        entered: function() {
          var date = self.parseDate($input.val().replace(/^\s*/, '').replace(/\s*$/, ''));
          if (date == null) { return; }
          $container.empty().append(self.buildMonth(date)).find(daySelector).eq(date.getDate()-1).click();
        },     
        initializeContainer: function() {
          return $('<div>').addClass('calendar').insertAfter($input);
        },
        pickYear: function() {
          mode = 'year';
          var table = document.createElement('table');
          var start = currentDate.getFullYear() - 6;
          if (options.minimumDate && options.minimumDate > start) { start = options.minimumDate; }
          for (var i = 0; i < 3; ++i) {
            var row = table.insertRow(-1);
            for (var j = 0; j < 4; ++j) {
              var cell = row.insertCell(-1);
              cell.innerHTML = start + (4*i) + j;
            }
          }
          $container.empty().append(table);
        },
        pickMonth: function() {
          mode = 'month';
          var table = document.createElement('table');
          var header = table.insertRow(-1);
          var year = header.insertCell(-1);
          year.colSpan = 4;
          year.className = 'm year';
          year.innerHTML = currentDate.getFullYear();
          
          //could make this static...
          for (var i = 0; i < 3; ++i) {
            var row = table.insertRow(-1);
            for (var j = 0; j < 4; ++j) {
              var cell = row.insertCell(-1);
              cell.innerHTML = abbreviations[4*i+j];
            }
          }
          $container.empty().append(table);
        },
        buildMonth: function(date) {
          mode = 'day';
          var first = new Date(date.getFullYear(), date.getMonth(), 1);
          var last = new Date(date.getFullYear(), date.getMonth()+1, 0);
          currentDate = first;
          var firstDay = first.getDay();
          var totalDays = last.getDate();
          var weeks = Math.ceil((totalDays + firstDay) / 7); 
          var table = document.createElement('table');
          
          for (var i = 0, count = 1; i < weeks; ++i) {
            var row = table.insertRow(-1);
            for(var j = 0; j < 7; ++j, ++count) {
              var cell = row.insertCell(-1);
              if (count > firstDay && count <= totalDays+firstDay) {
                cell.innerHTML = count - firstDay;
              }
            }
          }
           
          var header = table.insertRow(0);
          self.addHeaderCell(header, '<div class="prev">&laquo;</div>', 1);
          self.addHeaderCell(header, '<div class="month">' + months[date.getMonth()] + ' ' + date.getFullYear() + '</div>', 5);
          self.addHeaderCell(header, '<div class="next">&raquo;</div>', 1);
          
          var $table = $(table);
          if (options.minimumDate && options.minimumDate >= first) { $table.find('.prev').hide(); }
          if (options.maximumDate && options.maximumDate <= last) { $table.find('.next').hide(); }
          return $table;
        },
        addHeaderCell: function(header, html, colspan) {
          var cell = header.insertCell(-1);
          cell.innerHTML = html;
          cell.className = 'm '; //very stupid IE (all versions) fix
          cell.colSpan = colspan;
        },
      };
      this.datePicker = self;
      self.initialize();
    });
  }
})(jQuery);

$.prettyDate = function(date) {
  if (!date) { return ''; }
  return months[date.getMonth()] + ' ' + date.getDate() + ' ' + date.getFullYear();
}

$.prettyTime = function(date) {
  if (!date) { return ''; }
  var hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
  var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
  return hour + ':' + minutes;
}

$.ymd = function(date) {
  if (!date) { return ''; }
  var month = date.getMonth()+1;
  if (month < 10) { month = '0' + month; }
  var day = date.getDate();
  if (day < 10) { day = '0' + day; }
  return date.getFullYear() + '-' + month + '-' + day;
}

$.ISO8601 = function(date) {
  if (!date) { return ''; }
  var hour = date.getHours();
  if (hour < 10) { hour = '0' + hour;}
  var minute = date.getMinutes();
  if (minute < 10) { minute = '0' + minute;}
  return $.ymd(date) + 'T' + hour + ':' + minute;
}