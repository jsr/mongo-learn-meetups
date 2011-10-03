(function($) {
  $.fn.textarea = function() {
    return this.each(function() {
      if (this.textarea) { return false; }
      var $textarea = $(this);
      var $counter = null;
      var count = $textarea.attr('maxlength');
      var self = {
        initialize: function() {
          if (!count) { return; }
          $counter = $('<span>').addClass('charCounter').appendTo($textarea.siblings('label'));
          $textarea.keyup(self.updateCount);
          self.updateCount();
        },
        updateCount: function() {
          $counter.html($textarea.val().length + " / " + count);
        }
      }
      this.textarea = self;
      self.initialize();
    });
  };
})(jQuery);