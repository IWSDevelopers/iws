PrimeFaces.widget.MenuButton = PrimeFaces.widget.MenuButton.extend({

    /**
     * Align the menu to the right if the widget contains the class menuAlignRight
     * Since our MenuButton has the icon on the right instead of the left side,
     * we might also want the menu to be positioned accordingly
     */
    bindEvents: function() {
        this._super();

        if (this.jq.hasClass("menuAlignRight")) {
            this.cfg.position = {
                my: 'right top'
                ,at: 'right bottom'
                ,of: this.button
            }
        } else {
            this.cfg.position = {
                my: 'left top'
                ,at: 'left bottom'
                ,of: this.button
            }
        }
    }
});