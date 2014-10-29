function registerWebSocket() {
    var ws = $.gracefulWebSocket("ws://" + window.location.host + "/coffee/last");
    ws.onmessage = function (event) {
        var messageFromServer = event.data;
        $("ul.bought").append($("<li>An " + messageFromServer + " was bought.</li>"));
    };

}
function init() {
    load();
    registerWebSocket();
}

function addToList(list, coffee) {
    var container = $("<div/>").addClass("col-lg-4");
    container.append($("<img/>").attr("src", coffee.picture).addClass("img-circle").addClass("coffee-img"));
    container.append($("<h2/>").html(coffee.name));
    container.append($("<p/>").html(coffee.description));
    container.append($("<button/>").html("Buy").attr("data-coffee-name", coffee.name).addClass("coffee-buy-btn"));
    list.append(container);
}

function buy(coffee) {
    $.post( "/coffee/" + coffee, function( data ) {
        console.log("Data:", data);
        if (! data.fortune) {
            alert("You are drinking a delicious " + data.coffee.name + ".");
        } else {
            alert(data.fortune);
        }
        load();
    }, "json");
}
/**
 * Retrieves the coffee from the server.
 */
function load() {
    $.getJSON("/coffee", function(coffees) {
        console.log("Retrieved", coffees);
        var list = $(".list");
        $(list).empty();
        $(coffees).each(function(index, coffee) {
            addToList(list, coffee);
        });

        // Add a listener to generated buttons.
        $(".coffee-buy-btn").click(function(elem) {
           var coffee = $(this).attr("data-coffee-name");
           buy(coffee);
        });
    });
}