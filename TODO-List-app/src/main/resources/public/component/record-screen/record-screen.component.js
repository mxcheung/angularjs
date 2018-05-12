'use strict';

var recordScreenComponent = {
    templateUrl: './app/component/record-screen/record-screen.html',
    bindings: {
        displayFrom: '<',
        displayTo: '<',
        recordSchema: '<',
        recordService: '<',
    },

    controller: function () {
        function controller(micsSites) {
            this.micsSites = micsSites;
        }

        controller.prototype.$onInit = function $onInit() {
            return this.refreshRecords();
        };
        
        controller.prototype.updateDates = function updateDates(displayFrom, displayTo) {
            this.displayFrom = displayFrom;
            this.displayTo = displayTo;
            
            return this.refreshRecords();
        }
        
        controller.prototype.refreshRecords = function refreshRecords() {
            var self = this;
            this.selectedRecords = [];
            
            return this.recordService.getRecordsPerDate(this.displayFrom, this.displayTo).then(function(result) {
                self.records = result.data;
            })
        }
        
        controller.prototype.addNewRecord = function addNewRecord(record) {
            var self = this;
            console.log(record);
            
            return this.recordService.addNewRecord(record).then(function() {
                self.refreshRecords();
            })
        }
        
        controller.prototype.updateRecord = function updateRecord(record) {
            var self = this;
            console.log(record);
            
            return this.recordService.updateRecord(record).then(function() {
                self.refreshRecords();
            })
        }


        return controller;
    }()
};