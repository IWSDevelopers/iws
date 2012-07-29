/**
 * PrimeFaces CommandButton Widget
 *
 * Added click funtion to client side api
 */
PrimeFaces.widget.CommandButton = PrimeFaces.widget.CommandButton.extend({

    click: function() {
        this.jq.click();
    }

});
