app.controller('HeroesController', function($scope, $http, $location,
		HeroesService, ListTasksByIdFactory, EditTasksFactory, $route,
		$routeParams) {

	var cat = HeroesService.getAllHeroes();
	cat.then(function(response) {
		$scope.names = response.data;// don't forget "this" in the service
	})

	// Setting Now + 5 minutes as default date for datetimepicker.
	$scope.now = new Date();
	$scope.now.setMinutes($scope.now.getMinutes() + 5);
	this.addTask = function(addNewTaskCtrl) {
		AddTasksFactory.save(addNewTaskCtrl);
		DataTasksFactory.addTask(addNewTaskCtrl);

		window.setTimeout(function() {
			$location.path('/');
		}, 10);

	};

	var string1 = "";
	var deposits2 = {
		"2018-03-01" : {
			aud : 112,
			usd : 8672,
			cad : 3
		},
		"2018-03-02" : {
			aud : 15,
			usd : 18672,
			cad : 356
		}
	};

	$scope.allCurrencies = [ "aud", "cad", "jpy", "usd" ];

	$scope.loadData = function() {
		$scope.loadDataCCY($scope.singleSelectCCY)
	};

	$scope.loadDataCCY = function(ccy) {
		HeroesService.getCashDeposits(ccy)
		.then(function success(response) {
			$scope.table = $scope.tableData(response.data) ;
		})
		.catch(function error(rejection) {
			$scope.error = rejection;
		});
	};
	
	 $scope.tableData = function(deposits) {
			var table = [];
			for ( var date in deposits) {
				console.log(date);
				var row = {
					date : new Date(date)
				};
				for ( var currency in deposits[date]) {
					console.log(deposits[date][currency]);
					row[currency] = deposits[date][currency];
				}
				table.push(row);
			}
         return table;
     };

	$scope.loadDataCCY("AUD");
	$scope.dummytext = "hi THERE6";

});
