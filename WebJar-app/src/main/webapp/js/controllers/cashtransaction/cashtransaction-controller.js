app.controller('CashTransactionController', function($scope, $http, $location,
		CashTransactionService, ModalService, $route, $routeParams) {
	// Retrieve selected task
	$scope.greeting = "Hello Smart World2";
	$scope.current = {};
	$scope.current.displayDeleted = true;
	$scope.current.cashbalanceCounter = 0;
	$scope.data = {
			   headList: [{ name: 'Company' }, { name: 'Address' }, { name: 'City' }],
			   rowList: [{ name: 'Eve' , surname : 'Jackson', propertyName : 'Michigan'},
				         { name: 'Mary' , surname : 'Thompson', propertyName : 'Chicago'}
				         ]
			};
	$scope.data1 = {
			   headList: [{ name: 'Company' }, { name: 'Address' }, { name: 'City' }],
			   rowList: [{ name: 'Joe' , surname : 'Jackson', propertyName : 'Michigan'},
				         { name: 'Mary' , surname : 'Thompson', propertyName : 'Chicago'}
				         ]
			};

	
	

    $scope.$watch(cashTransactionsPageScopeChanged, function() {
    	CashTransactionService.getCashTransactionsByCriteria($scope.current.displayDeleted)
        .then(function(response) {
        	$scope.current.cashtransactions = response.data;
        });
    });
    
    
    $scope.openUpdateCashTransactionModal = function(depositId) {
    	CashTransactionService.getCashTransaction(depositId).then(function(depositResponse){
    		var cashDetails = depositResponse.data;
    		var resolve = {
    			cashDetails : cashDetails
    		}
    		var options = {
    			windowClass : 'cashmgmt-small-modal-window'
    		}
    		var modalEvent = ModalService.getModalInstance('views/cashtransaction/cashtransaction-input.html', 'CashTransactionModalController', resolve, options);
    		
    		 modalEvent.result
    		 	.then(function(result1) {
    		 		console.log("result");
    				console.log(result1);
    	       	$scope.current.cashbalanceCounter++;
    	     });
    	});
    }
     
    
	$scope.data2 = {};

	$scope.data2.greeting = "Melbourne Employees";

    $scope.openAddSalesModal = function(rowId) {
    	SalesService.getEmployee(rowId).then(function(employeeResponse){
    		var employeeDetails = employeeResponse.data;
    		var employeeData = employeeDetails.table[rowId];
    		var salesData = employeeDetails.sales[employeeData['EmployeeId']];
        	var employeeDetails = {}
        	employeeDetails.isUpdate = true;
        	employeeDetails.employeeData = employeeData;
        	employeeDetails.salesData = salesData;
        	var resolve = {
        		employeeDetails : employeeDetails
        	};
        	var options = {
        	};
            ModalService.getModalInstance('views/sales/sales-input.html', 'SalesModalController', resolve, options);
    	});
    }
	
	$scope.loading = false;



});



app.controller('CashTransactionModalController', function ($scope, $rootScope, $uibModalInstance, CashTransactionService, cashDetails) {
	$scope.staticdata = {};
	$scope.cashDetails = cashDetails;

	$scope.cancelModal = function() {
		$uibModalInstance.close(false);
	}
	
	$scope.close = function() {
		$uibModalInstance.close(false);
	}
	
	$scope.update = function() {
	    CashTransactionService.updateCashTransaction($scope.cashDetails)
        .then(function(response) {
            if (response.status == 200) {
            //	$scope.$parent.current.cashbalanceCounter++;
        		$uibModalInstance.close("update occurred");
            }
        });
	}	
	
});


/*
 * Works out whether the page scope has changed and a display refresh is
 * required
 */
function cashTransactionsPageScopeChanged($scope) {
    return " Cash Balance Counter: " + $scope.current.cashbalanceCounter +
    	   " Display Deleted: " + $scope.current.displayDeleted;
};
