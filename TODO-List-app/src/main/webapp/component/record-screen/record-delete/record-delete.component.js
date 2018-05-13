'use strict';

var recordDeleteComponent = {
    templateUrl: './app/component/record-screen/record-delete/record-delete.html',
    bindings: {
        selectedRecords: '<',
        recordService: '<',
        onDelete: '&',
    },

    controller: function () {

        function controller() {
        }
        
        controller.prototype.doDelete = function doDelete() {
            var self = this;
            if (confirm('Are you sure you want to delete the selected records?')) {
                this.recordService.deleteRecords(self.selectedRecords).then(function() {
                    self.onDelete();
                });
            }
        };

        return controller;
    }()
};