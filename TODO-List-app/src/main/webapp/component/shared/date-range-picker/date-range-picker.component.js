'use strict';

var dateRangePickerComponent = {
    templateUrl : './app/component/shared/date-range-picker/date-range-picker.html',
    bindings : {
        startDate : '<',
        endDate : '<',
        onUpdate : '&'
    },

    controller : function() {
        function controller($sessionStorage) {
            this.$sessionStorage = $sessionStorage;
        }

        controller.prototype.$onInit = function $onInit() {
            console.log(this);
        };

        controller.prototype.datesUpdated = function datesUpdated() {
            var self = this;

            // Update the session storage with the new picked dates.
            self.$sessionStorage.startDate = self.startDate;
            self.$sessionStorage.endDate = self.endDate;
            
            // Call the parent function to propagate the update.
            this.onUpdate({
                startDate : self.startDate,
                endDate : self.endDate
            });

        };

        return controller;
    }()
};