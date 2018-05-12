'use strict';

var depositSummaryScreenComponent  = {
    templateUrl: './app/component/deposit-summary-screen/deposit-summary-screen.html',
    bindings: {
        displayFrom: '<',
        displayTo: '<',
        depositSummaryRecords: '<',
        allCurrencies: '<'
    },

    controller: function () {
        function controller(micsSites) {
            this.itemsByPage = 15;
        }

        controller.prototype.$onInit = function $onInit() {
            if (this.displayTo == null) {
                this.displayTo = new Date();
            }

            if (this.displayFrom  == null) {
                this.displayFrom = moment(this.displayTo).subtract(7, 'days').toDate();
            }
        };
        
        controller.prototype.$onChanges = function $onChanges(){
            this.depositSummaryToTable();
        }

        controller.prototype.depositSummaryToTable = function depositSummaryToTable() {
            var table = [];
            for (var date in this.depositSummaryRecords) {
                // Note - can't use between here, between is exclusive.
                if (moment(date).isSameOrAfter(this.displayFrom) && moment(date).isSameOrBefore(this.displayTo)) {
                    var row = {date: new Date(date)};
                    for (var currency in this.depositSummaryRecords[date]) {
                        row[currency] = this.depositSummaryRecords[date][currency];
                    }
                    
                    table.push(row);
                
                }
            }

            table.sort(function(left, right) { 
                return left.date - right.date
            });

            this.depositSummaryAsTable = table;
            
        }

        
        controller.prototype.updateDates = function updateDates(displayFrom, displayTo) {
            this.displayFrom = displayFrom;
            this.displayTo = displayTo;
            
            // Call onChanges manually here.
            this.$onChanges();
        }

        return controller;
    }()
};