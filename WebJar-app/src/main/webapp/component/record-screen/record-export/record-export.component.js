'use strict';

var recordExportComponent = {
    templateUrl: './app/component/record-screen/record-export/record-export.html',
    bindings: {
        recordSchema: '<',
        records: '<',
    },

    controller: function () {
        function controller($q) {
            this.$q = $q;
        }

        controller.prototype.$onInit = function $onInit() {
        }
        
        controller.prototype.$onChanges = function $onChanges() {
            this.updateCsvOutput();
        }
        
        controller.prototype.updateCsvOutput = function updateCsvOutput () {
            var self = this;
            var rows = [];
            if (this.records != null) {
                this.records.forEach(function(record) {
                    var cells = [];
                    self.recordSchema.fields.forEach(function(field) {
                        var sanitizedValue = JSON.stringify(record[field.name]);
                        cells.push(sanitizedValue);
                    });
                    rows.push(cells.join());
                })
            }

            this.csvOutput = rows.join("\n");
        }

        return controller;
    }()
};