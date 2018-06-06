app.controller('FxCalcController', function($scope, $http, $location,
		FxCalcService, ModalService, $route, $routeParams) {
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
    
    $scope.exceedLimit = function() {
       return ($scope.usdAmount > $scope.limit );
    };
    
    
    
});




