app.controller('SmartChartFinancialXController', function($scope, $http, $location,
		 $route, $routeParams) {
	// Retrieve selected task
	$scope.greeting = "Hello Smart World2";
	


	$scope.data2 = {};

	$scope.data2.greeting = "Melbourne Employees";

	
	$scope.loading = false;
	$scope.getData = function() {
		$scope.loading = true;
		$http.get("./report/financialchartdata?formatType=DEFAULT")
		.then(function(response){
			$scope.chartdata = response.data;
			
			var length = Object.keys($scope.chartdata).length;
			var i = 0;
//			$scope.labels = Object.keys($scope.chartdata);
			$scope.labels = [];
			$scope.series1 = [];
			$scope.series2 = [];
			$scope.series3 = [];
			for (var key in $scope.chartdata) {
				  console.log(key, $scope.chartdata[key]);
				  var  row = $scope.chartdata[key];
				  if (row["Series1"]) {
					  $scope.labels.push(key);
					  $scope.series1.push(row["Series1"].value);
					  $scope.series2.push(row["Series2"].value);
					  $scope.series3.push(row["Series3"].value);
				  }
			}
			$scope.data =  [$scope.series1, $scope.series2, $scope.series3];
		
			$scope.loading = false;
		
		});
	}
	$scope.getData();

//	$scope.labels = getDateArray(startDate, endDate);
//	$scope.series1 = getValueArray(startDate, endDate, 30,  90);
//	$scope.series2 = getValueArray(startDate, endDate, 40, 60);
//	$scope.series3 = getValueArray(startDate, endDate, 50, 75);
//	$scope.series =  ['Series A', 'Series B', 'Series C'];
// http://jtblin.github.io/angular-chart.js/	
	 $scope.series = ['Series A', 'Series B', 'Series C' ];
//	 $scope.colors = ["rgb(159,204,0)","rgb(250,109,33)","rgb(154,154,154)"];
//	 $scope.colors = ["#803690", "#00ADF9", "#DCDCDC"];
	 $scope.options = {
			  title: {
		            display: true,
		            text: 'Custom Chart Title'
		       },
			   legend: {
				   position : 'right',
		            display: true,
		            labels: {
		                fontColor: 'rgb(255, 99, 132)'
		            }
		        },
		        
		        scales: {
		        	xAxes: [{
						distribution: 'series',
						ticks: {
							source: 'labels'
						}
					}],
		            yAxes: [{
		            	scaleLabel: {
							display: true,
							labelString: 'Closing price ($)'
						},
		                ticks: {
		                    suggestedMin: 0,
		                    suggestedMax: 100
		                }
		            }]
		        }		        
			  };	
	$scope.data =  [$scope.series1, $scope.series2, $scope.series3];
    $scope.datasetOverride = [
	      {
	        label: "Series A",
	        lineTension: 0.1,
	        fill: true,
	        borderWidth: 1,
	        type: 'line'
	      },
	      {
	        label: "Line chart barrier",
	        pointStyle: line,
	        lineTension: 0.5,
	        fill: false,
	        borderWidth: 4,
	        borderDash: [],
	        borderColor: "rgba(0,255,0,0.3)",   /* green with opacity */
	        hoverBackgroundColor: "rgba(255,99,132,0.4)",
	        hoverBorderColor: "rgba(255,99,132,1)",
	        type: 'line'
	      },
	      {
	        label: "Line chart3",
	        fill: false,
	        pointStyle: "dash",
	        borderWidth: 4,
	        borderColor: "rgba(255,0,0,0.3)",   /* blue with opacity */
	        hoverBackgroundColor: "rgba(255,99,132,0.4)", 
	        hoverBorderColor: "rgba(255,99,132,1)",
	        type: 'line'
	      }
	    ];
	$scope.data.greeting = "Melbourne Employees";

});





