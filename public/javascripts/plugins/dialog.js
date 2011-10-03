(function($) {
  $.fn.dialog = function(opts) {
    var defaults = {width: 700, height:400, title: null};
    if (opts && opts.command && opts.command == 'show') {
      return this.each(function() { 
        if (!this.dialog) { $(this).dialog(); }
        this.dialog.show(opts.title); 
      });
    }

    var options = $.extend({}, defaults, opts); 
    return this.each(function() {
      if (this.dialog) { return false; }
      var $content = $(this);
      var $container = $('<div>').append(this)
      var $overlay = null;
      var self = {
        initialize: function()  {
          $content.addClass('content');
          $container.appendTo($('body')).hide().addClass('dialog').css({width: options.width, height: options.height});
          $container.prepend($('<div>').addClass('header').html('<span></span>'));
          $container.append($content.find('.buttons'));
          $overlay = $('<div>').addClass('dialogOverlay').appendTo('body');
          $container.find('.button.cancel').click(self.close);
          $container.find('.button.ok').click(self.oked);
          self.setTitle($content.attr('title'));
          self.bindEvents();
        },
        show: function(title) {
          self.setTitle(title);
          self.position();
          $overlay.show();
          $container.show();
        },
        oked: function() {
          if (options.oked) { options.oked(); }
          self.close();
          return false;
        },
        close: function() {
          $overlay.hide();
          $container.hide();
          return false;
        },
        getDimensions: function() {
          var el = $(window);
          var h = $.browser.opera && $.browser.version > '9.5' && $.fn.jquery <= '1.2.6' ? document.documentElement['clientHeight'] : el.height();
          return [h, el.width()];
        },
        position: function() {
          var dimensions = self.getDimensions();
          $overlay.height(dimensions[0]).width(dimensions[1]);
          $container.center();
        },
        bindEvents: function() {
          $(window).bind('resize', self.position);
          $(document).bind('keydown', function(e)  { if (e.keyCode == 27) { self.close(); } });
        },
        setTitle: function(title) {
          if (title) { $container.find('.header span').text(title); }
        }
      };
      this.dialog = self;
      self.initialize();
    });
  };
})(jQuery);

  
jQuery.fn.center = function() {
  var left = ($(window).width() - this.width()) / 2 + $(window).scrollLeft();
  this.css({position: 'absolute', top: 100, left: left});
  return this;
};
