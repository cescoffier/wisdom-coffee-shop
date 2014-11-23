(function ($) {

	$.extend({
	
		easyWebSocket: function (url, options) {

			// Override defaults with user properties
			var opts = $.extend({}, this.defaults, options);

			// create a new websocket or fallback
			var ws = new WebSocket(url);
	 		$(window).unload(function () { ws.close(); ws = null });
			return ws;
		}
	});
	
})(jQuery);