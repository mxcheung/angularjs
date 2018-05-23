app.controller('ReportController', function($scope, $location,	 $route, $routeParams, ReportCashBalanceService) {
	// Retrieve selected task
	$scope.greeting = "Hello World2";

	var cat = ReportCashBalanceService.getAccountCashBalanceSummary();
	cat.then(function(response) {
		$scope.data = response.data;// don't forget "this" in the service
		console.log($scope.data);
		
		$scope.report = $scope.data.reports['BASE'];
		
	})
 
	
});

	