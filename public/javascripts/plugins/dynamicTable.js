(function($) {
  $.fn.dynamicTable = function(opts) {
    var defaults = {template: null, url: null, go: null};
    return this.each(function() {
      if (this.dynamicTable) { return false; }
      var options = $.extend({}, defaults, opts);
      var template = _.template($(options.template).html());
      var $container = $(this);
      var $pager = null;
      var self = {
        initialize: function() {
          $container.delegate('[data-id]', 'click', self.clicked);
          self.loadData(1);
        },
        loadData: function(page) {
          $.get(options.url.replace(':page', page), {}, self.gotData, 'json');
        },
        gotData: function(r) {
          if (r.models.length == 0) {
            $container.hide();
            return;
          }
          $container.empty();
          _(r.models).each(function(m) { $container.append(template({model: m})); });
          $container.show();
          self.setPager(r);
        },
        setPager: function(r) {
          if (r.pages < 2) { return; }
          if ($pager != null) {
            $pager.children().removeClass('current').eq(r.page-1).addClass('current');
            return;
          }
          $pager = $('<div>').addClass('pager');
          
          var maxPages = r.pages > 5 ? 5 : r.pages;
          for (var i = 1; i <= maxPages; ++i) {
            var $a = $('<a>').text(i);
            if (r.page == i) { $a.addClass('current'); }
            $pager.append($a);
          }
          $pager.delegate('a', 'click', self.paged);
          $pager.insertAfter($container);
        },
        clicked: function() {
          top.location = options.go.replace(':id', $(this).attr('data-id'));
        },
        paged: function() {
          self.loadData($(this).text());
        }
      }
      this.dynamicTable = self;
      self.initialize();
    });
  };
})(jQuery);