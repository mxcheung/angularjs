app.controller('FxCalcController', function($scope, $http, $location,
		FxCalcService, ModalService,  $route, $routeParams) {
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
	
	FxCalcService.getcalcBuyAmount()
	 .then(function success(response) {
		 $scope.buyAmount = response.data;
		 console.log('newemployees==>');
		 console.log($scope.newemployees);
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
    			$scope.sellAmount = response.sellAmount;
    			$scope.usdAmount = response.sellAmount;
    		});
    	}
    	
    };
    
    $scope.getBuyAmount = function() {
    	var sellAmount = $scope.sellAmount;
    	var fxrate = $scope.fxrate;
    	if ( (sellAmount) && (fxrate)) {
    		FxCalcService.getcalcBuyAmount(sellAmount, fxrate)
    		.then(function(response){
    			$scope.buyAmount = response.buyAmount;
    			$scope.usdAmount = response.sellAmount;
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
	    

    $scope.exceedLimit = function() {
       return ($scope.usdAmount > $scope.limit );
    };
    
    
    
});




