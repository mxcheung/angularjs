app.controller('HeroesController', function($scope, $http, $location,
		HeroesService, ListTasksByIdFactory, EditTasksFactory, $route,
		$routeParams) {

	var cat = HeroesService.getAllHeroes();
	cat.then(function(response) {
		$scope.names = response.data;// don't forget "this" in the service
	})
	// console.log($scope.names);

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
	/*
	 * $scope.screens = [ { scrnid : "1" }, { scrnid : "2" } ]; var table = [];
	 * $scope.depositSummaryRecords = [ { date : "2018-05-01" }, { date :
	 * "2018-05-01" } ]; this.depositSummaryRecords =
	 * $scope.depositSummaryRecords; for ( var date in
	 * this.depositSummaryRecords) { console.log("date: "); console.log(date);
	 * console.log(this.depositSummaryRecords[date]); var row = { date : new
	 * Date("2018-05-01") }; row["aud"] = 1; table.push(row); }
	 * console.log(table); var students = [ { name : "Mike", track : "track-a",
	 * achievements : 23, points : 400, }, { name : "james", track : "track-a",
	 * achievements : 2, points : 21, }, ]
	 * 
	 * for ( var key in students) { console.log(students[key]) for ( var track
	 * in students[key]) { console.log("----"); console.log(track);
	 * console.log(students[key][track]); } }
	 * 
	 * 
	 * 
	 * var string1 = ""; var object1 = {a: 1, b: 2, c: 3};
	 * 
	 * for (var property1 in object1) {
	 * 
	 * console.log("xxx----"); console.log(property1); string1 = string1 +
	 * object1[property1]; }
	 * 
	 */
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
		var singleSelectCCY = $scope.singleSelectCCY;
		$scope.loadDataCCY(singleSelectCCY)
	};

	$scope.loadDataCCY = function(ccy) {
		var cashDeposits = HeroesService.getCashDeposits(ccy);
		cashDeposits
		.then(function success(response) {
			$scope.cashDeposits = response.data;// don't forget "this" in the
			// service
			let deposits = $scope.cashDeposits;
			console.log("zzzz----");
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
			console.log(table);

			$scope.table = table;
		})
		.catch(function error(rejection) {
			// this function will be called when the request returned error
			// status
		        console.log("error:", rejection); 
				$scope.dummytext = rejection.status;
		});
	};

	$scope.loadDataCCY("AUD");
	$scope.dummytext = "hi THERE4";

});
