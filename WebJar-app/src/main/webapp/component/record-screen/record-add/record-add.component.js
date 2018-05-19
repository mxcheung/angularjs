'use strict';

var recordAddComponent = {
    templateUrl: './app/component/record-screen/record-add/record-add.html',
    bindings: {
        recordSchema: '<',
        existingRecords: '<',
        recordService: '<',
        onSave: '&',
    },

    controller: function () {
        function controller() {
        }

        controller.prototype.$onInit = function $onInit() {
            this.modalId = this.recordSchema.name + '-add-modal';
        }
        
        return controller;
    }()
};