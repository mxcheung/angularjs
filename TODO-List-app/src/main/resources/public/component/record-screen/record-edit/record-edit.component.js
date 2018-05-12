'use strict';

var recordEditComponent = {
    templateUrl: './app/component/record-screen/record-edit/record-edit.html',
    bindings: {
        recordSchema: '<',
        recordService: '<',
        existingRecords: '<',
        selectedRecords: '<',
        onSave: '&',
    },

    controller: function () {
        function controller() {
            this.record = {};
        }
        
        controller.prototype.$onInit = function $onInit() {
            this.modalId = this.recordSchema.name + '-edit-modal';
            console.log(this);
            
        }
        
        controller.prototype.$onChanges = function $onChanges() {
            this.canEditSelected = this.selectedRecords.length == 1;
            console.log(this.canEditSelected)
            
            if (! this.canEditSelected) {
                this.targetRecord = null;
                
            } else {
                var idField = this.recordSchema.fields
                    .filter(function(field) {
                        return field.id;
                    })
                    .pop();
                
                var self = this;
                this.targetRecord = this.existingRecords.filter(function(record) {
                    return record[idField.name] == self.selectedRecords[0];
                }).pop();

                console.log(this.targetRecord);

            }
        }

        return controller;
    }()
};