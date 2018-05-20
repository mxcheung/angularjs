app.controller('HighChartPieController', function($scope, $location,	 $route, $routeParams) {
	// Retrieve selected task
	$scope.greeting = "Hello World2";

	
	Highcharts.chart('container', {

	    xAxis: {
	        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
	            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	    },

	    series: [{
	        data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
	    }]
	});

	
	 // Sample data for pie chart
    $scope.pieData = [{
            name: "Microsoft Internet Explorer",
            y: 56.33
        }, {
            name: "Chrome",
            y: 24.03,
            sliced: true,
            selected: true
        }, {
            name: "Firefox",
            y: 10.38
        }, {
            name: "Safari",
            y: 4.77
        }, {
            name: "Opera",
            y: 0.91
        }, {
            name: "Proprietary or Undetectable",
            y: 0.2
    }]
    
    

	 // Sample data for pie chart
    $scope.pieData2 = [{
            name: "Download Sales",
            y: 300
        }, {
            name: "In-Store Sales",
            y: 500
        }, {
            name: "Mail-Order Sales",
            y: 100
    }]
    
    
    
    
    
    

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

    

	var chart2 =	Highcharts.chart('container2', {

	    xAxis: {
	        categories: $scope.labels
	    },

	    series: [{   data: $scope.series1   },
	    	     {   data: $scope.series2   },
	    	     {   data: $scope.series3   }
	    	 	    	]
	});
	$scope.itemsX =  $scope.items;
//	var piechart2 = $('#containerpie').highcharts();
//	var piechart2 = angular.element(document.querySelector("#piechart2")); 
 //   $scope.pieData2csv = piechart2.getCSV();

});
