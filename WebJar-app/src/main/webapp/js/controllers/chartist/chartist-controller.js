app.controller('ChartistController', function($scope, $location,	 $route, $routeParams) {
	// Retrieve selected task
	$scope.greeting = "Hello World2";


    
    

	 
	// start and end date
	var
	  startDate = new Date("2017-01-01"),
	  endDate = new Date("2017-10-07");
	
	// date array
	var getDateArray = function(start, end) {

	  var
	    arr = new Array(),
	    dt = new Date(start);

	  while (dt <= end) {
	    arr.push(new Date(dt).toISOString().slice(0,10));
	    dt.setDate(dt.getDate() + 1);
	  }

	  return arr;

	}
    
	var getRandomInt = function(min, max) {
	    return Math.floor(Math.random() * (max - min + 1)) + min;
	}
	
	// value array
	var getValueArray = function(start, end, min, max) {

	  var
	    arr = new Array(),
	    dt = new Date(start);

	  while (dt <= end) {
		   arr.push(getRandomInt(min,max));
	    dt.setDate(dt.getDate() + 1);
	  }

	  return arr;

	}


	$scope.labels = getDateArray(startDate, endDate);
	$scope.series1 = getValueArray(startDate, endDate, 30,  90);
	$scope.series2 = getValueArray(startDate, endDate, 40, 60);
	$scope.series3 = getValueArray(startDate, endDate, 50, 75);
	$scope.series =  ['Series A', 'Series B', 'Series C'];

	$scope.data =  [$scope.series1, $scope.series2, $scope.series3];
	$scope.data.greeting = "Melbourne Employees";

    


	var data1 = {
			  labels: ['Apple', 'Blueberry', 'KeyLime'],
			  series: [60, 10, 30]
			};

			new Chartist.Pie('#chart5', data1);


			var data12 = {
			  labels: ['Sugar', 'Glazed', 'Maple', 'Chocolate'],
			  series: [10, 10, 20, 60]
			};

			var options2 = {
			  donut: true
			}

			new Chartist.Pie('#chart6', data12, options2);


});
