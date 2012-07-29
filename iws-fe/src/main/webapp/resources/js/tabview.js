PrimeFaces.widget.TabView = PrimeFaces.widget.TabView.extend({

    init: function(cfg) {
        this._super(cfg);
        var _self = this;

        //Tab header events
        this.navContainer.children('li')
            .unbind('click.tabview')// unbind the default click handler
            .bind('click.tabview', function(e) {
                var element = $(this);

                if ($(e.target).is(':not(.ui-icon-close)')) {
                    var index = element.index();

                    if (!element.hasClass('ui-state-disabled') && index != _self.cfg.selected) {
                        var tabId = _self.panelContainer.children().eq(_self.cfg.selected).attr('id');

                        var options = {
                            source:tabId,
                            process:tabId,
                            update:_self.id,
                            formId:_self.cfg.formId,
                            onsuccess: function(responseXML) {
                                var xmlDoc = $(responseXML.documentElement), updates = xmlDoc.find('update');

                                PrimeFaces.ajax.AjaxUtils.handleResponse.call(this, xmlDoc);

                                for (var i = 0; i < updates.length; i++) {
                                    var update = updates.eq(i), id = update.attr('id'), content = update.text();

                                    if (id == _self.id) {
                                        if (!this.args.validationFailed) {
                                            _self.select(index);
                                        } else {
                                            //PrimeFaces.ajax.AjaxUtils.updateElement.call(this, _self.id, content);
                                            _self.select(_self.cfg.selected)
                                        }
                                    }
                                }
                                return true;
                            }
                        };
                        PrimeFaces.ajax.AjaxRequest(options);
                    }
                }
            });
    }

});
