(function($) {
  $.fn.formTip = function() {
    return this.each(function() {
      if (this.formTip) { return false; }
      var $tip = $(this).hide();
      var $container = $tip.closest('div.item');
      var self = {
        initialize: function() {
          $container.focusin(self.showTip).focusout(self.hideTip);
        },
        showTip: function() {
          $tip.show();
        },
        hideTip: function() {
          $tip.hide();
        }
      };
      this.formTip = self;
      self.initialize();
    });
  }
})(jQuery);