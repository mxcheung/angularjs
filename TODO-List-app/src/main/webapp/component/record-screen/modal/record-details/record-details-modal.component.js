var recordDetailsModalComponent =  {
        templateUrl: './app/component/record-screen/modal/record-details/record-details-modal.html',
        bindings: {
            recordSchema: '<',
            recordService: '<',
            targetRecord: '<',
            modalId: '<',
            onSave: '&',
        },

        controller: function () {
            function controller() {
            }
            
            controller.prototype.$onInit = function $onInit() {
                this.populateTypeaheadOptions();
            }
            
            controller.prototype.$onChanges = function $onChanges() {
                this.isNewRecord = (this.targetRecord == null);
                
                if (this.isNewRecord) {
                    this.modalTitle = 'Add a new ' + this.recordSchema.name;
                    this.record = {};
                } else {
                    this.modalTitle = 'Edit ' + this.recordSchema.name;
                    this.record = angular.copy(this.targetRecord);

                    // Check for date fields.  If we find one, convert the underlying field value to a javascript 
                    // date object so that we can display it properly in a datepicker.
                    var self = this;
                    angular.forEach(this.recordSchema.fields, function(field) {
                        if (field.type.indexOf('date') != -1) {
                            self.record[field.name] = new Date(self.record[field.name]);
                            console.log(self);
                        }
                    });
                }
            }

            controller.prototype.populateTypeaheadOptions = function populateTypeaheadOptions() {
                this.typeaheadValues = {};
                var self = this;
                angular.forEach(this.recordSchema.fields, function(field) {
                    if (!field.typeahead) {
                        return;
                    }
                    self.recordService.getDistinctFieldValues(field.name).then(function(result) {
                        self.typeaheadValues[field.name] = result.data.filter(function(record) {
                            return record != null;
                        });
                    });
                });
                
                console.log(this);
            };

            controller.prototype.formSaved = function formSaved() {
                var self = this;
                this.onSave({record: angular.copy(self.record)});
                
                jQuery('#'+ this.modalId).modal('toggle');

                if (this.isNewRecord) {
                    // If we were adding a record, clear it on submit.
                    this.record = {};
                }
            }

            return controller;
        }()
    };