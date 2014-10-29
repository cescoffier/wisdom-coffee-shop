/**
 * WebSocket with graceful degradation - jQuery plugin
 * @author David Lindkvist
 * @version 0.1
 * 
 * Returns an object implementing the WebSocket API. 
 * 
 * If browser supports WebSockets a native WebSocket instance is returned. 
 * If not, a simulated half-duplex implementation is returned which uses polling 
 * over HTTP to retrieve new messages
 * 
 * OPTIONS
 * -----------------------------------------------------------------------------
 * 
 * {Number}		fallbackOpenDelay		number of ms to delay simulated open 
 * 										event for fallback
 * {Number}		fallbackPollInterval	number of ms between requests for 
 * 										fallback polling
 * {Object}		fallbackPollParams		optional params to pass with each poll 
 * 										requests
 * 
 * EXAMPLES
 * -----------------------------------------------------------------------------
 * 
 * 	var websocket = $.gracefulWebSocket("ws://127.0.0.1:8080/");
 * 
 * 	var websocket = $.gracefulWebSocket({
 * 		fallbackPollParams:  {
 * 			"latestMessageID": function () {
 * 				return latestMessageID;
 * 			}
 *  	} 
 * 	});
 * 
 */

(function ($) {

	$.extend({
	
		gracefulWebSocket: function (url, options) {

			// Override defaults with user properties
			var opts = $.extend({}, this.defaults, options);

			// create a new websocket or fallback
			var ws = new WebSocket(url);
	 		$(window).unload(function () { ws.close(); ws = null });
			return ws;
		}
	});
	
})(jQuery);