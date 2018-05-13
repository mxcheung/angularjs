'use strict';

var recordDetailsTableComponent = {
    templateUrl: './app/component/record-screen/record-details-table/record-details-table.html',
    bindings: {
        records: '<',
        recordSchema: '<',
        selectedRecords: '<',
        onRecordSelected: '&',
    },

    controller: function () {
        function controller() {
            this.itemsByPage = 15;
            // We need an object instead of an array to do angular watches - e.g selected[123] = true.
            // We keep a watch on the parent records so that we can convert back and forward.
            this._selectedRecordsInternal = {};
        }

        controller.prototype.$onInit = function $onInit() {
            console.log(this);
        };

        controller.prototype.$doCheck = function $doCheck() {
            if (this.selectedRecords.length !== Object.keys(this._selectedRecordsInternal).length) {
                this._selectedRecordsInternal = [];
                for (var i = 0; i < this.selectedRecords.length; i++) {
                    var recordKey = this.selectedRecords[i];
                    this._selectedRecordsInternal[recordKey] = true;
                }
                console.log(this);
            }
            
        };
        
        controller.prototype.getRecordId = function getRecordId(record) {
            var recordIdFields = this.recordSchema.fields.filter(function(field) { 
                return field.id == true; 
            });

            if (recordIdFields.length == 1) {
                return record[recordIdFields[0].name];
            } else {
                var id = {};
                for (var i = 0; i < recordIdFields.length; i++) {
                    var idFieldName = recordIdFields[i].name;
                    id[idFieldName] = record[idFieldName];
                }

                return id;
            }
        };

        controller.prototype.recordSelected = function recordSelected(rid) {
            return this._selectedRecordsInternal.indexOf(rid) != -1;
        }
        
        controller.prototype.getSelectedRecords = function getSelectedRecords() {
            console.log('getting selected records');
            // AngularJS stores models as keys against the given object, so for example in our model - model[123] = true.
            // We extract all models stored and only include the ones which are true (i.e selected).
            var self = this;
            return Object.keys(this._selectedRecordsInternal).filter(function(attr) {
                return self._selectedRecordsInternal[attr] == true;
            });
        };

        return controller;
    }()
};