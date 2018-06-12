app.controller('FxCalcController', function($scope, $http, $location,
		FxCalcService, ModalService,  $route, $routeParams) {




    $scope.$watch(fxTranPageScopeChanged, function() {
    	$scope.lastModified = new Date();
    });


	// Retrieve selected task
	$scope.greeting = "Hello Smart World2";

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

	$scope.data2 = {};
	$scope.limit = 10000;

	$scope.tradeDate = "2008-08-07";
	$scope.expiryDate = "2008-08-17";
	$scope.createdBy = "abcd";
	$scope.verifiedBy = "user2";
	$scope.buyAmount = 1000;
	$scope.sellAmount = 1000;
	$scope.fxrate = 0.5;

	FxCalcService.getcalcBuyAmount()
	 .then(function success(response) {
		 $scope.buyAmount = response;
	 })
	.catch(function error(rejection) {
		$scope.error = rejection;
	});


    $scope.getSellAmount = function() {
    	var buyAmount = $scope.buyAmount;
    	var fxrate = $scope.fxrate;
    	if ( (buyAmount) && (fxrate)) {
    		FxCalcService.getcalcSellAmount(buyAmount, fxrate)
    		.then(function(response){
    			$scope.sellAmount = response;
    			$scope.usdAmount = response;
    		});
    	}

    };

    $scope.getBuyAmount = function() {
    	var sellAmount = $scope.sellAmount;
    	var fxrate = $scope.fxrate;
    	if ( (sellAmount) && (fxrate)) {
    		FxCalcService.getcalcBuyAmount(sellAmount, fxrate)
    		.then(function(response){
    			$scope.buyAmount = response;
    			$scope.usdAmount = response;
    		});
    	}
    };



    $scope.updateBuyAmount = function() {
        $scope.transaction = {};
        $scope.transaction.id = 125;
        $scope.transaction.tradeDate = $scope.tradeDate;
        $scope.transaction.expiryDate = $scope.expiryDate;
        $scope.transaction.buyAmount = $scope.buyAmount;
        $scope.transaction.sellAmount = $scope.sellAmount;
        $scope.transaction.fxrate = $scope.fxrate;
        FxCalcService.updateBuyAmount($scope.transaction)
        .then(function(response) {
        	$scope.response = response;
        });
	}


    $scope.verify = function() {
        $scope.transaction = {};
        $scope.transaction.id = 125;
        $scope.transaction.tradeDate = $scope.tradeDate;
        $scope.transaction.expiryDate = $scope.expiryDate;
        $scope.transaction.buyAmount = $scope.buyAmount;
        $scope.transaction.sellAmount = $scope.sellAmount;
        $scope.transaction.fxrate = $scope.fxrate;
        $scope.transaction.createdBy = $scope.createdBy;
        FxCalcService.verify($scope.transaction)
	   	 .then(function success(response) {
        	$scope.response = response;
        	if (response.status == 200) {
            	$scope.verifiedBy = response.data.verifiedBy;
            }
	   	 })
	   	.catch(function error(rejection) {
	   		$scope.error = rejection;
			 console.log('error==>');
			 console.log(rejection);
	   	});

        
	}

    $scope.exceedLimit = function() {
       return ($scope.usdAmount > $scope.limit );
    };



});




/*
 * Works out whether the page scope has changed and a display refresh is
 * required
 */
function fxTranPageScopeChanged($scope) {
    return " Deposit Buy Amount: " + $scope.buyAmount +
    	   " Display Sell Amount: " + $scope.sellAmount;
};