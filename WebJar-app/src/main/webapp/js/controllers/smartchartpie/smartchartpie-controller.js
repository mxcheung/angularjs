app.controller('SmartChartPieController', function($scope, $http, $location,
		 $route, $routeParams) {
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

	$scope.data2.greeting = "Melbourne Employees";

	
	$scope.loading = false;
	$scope.getData = function() {
		$scope.loading = true;
		$http.get("http://dummy.restapiexample.com/api/v1/employees")
		.then(function(response){
			$scope.employeesdata = response.data;
			$scope.loading = false;
			$scope.data1 = {
				greeting : "Sydney Employees",
				employees : [
						{"id":"1","employee_name":"testing","employee_salary":"1","employee_age":"1","profile_image":""},
						{"id":"2","employee_name":"Wonsu Dupcia","employee_salary":"0","employee_age":"0","profile_image":""}
						]
			}; 
			$scope.data2.employees =  [
				             {"id":"3","employee_name":"Ewa Czika","employee_salary":"2398580","employee_age":"30","profile_image":""},
							 {"id":"4","employee_name":"\u0141ukasz Trolololo","employee_salary":"0","employee_age":"85","profile_image":""}
			                ];
			$scope.data2.employees = $scope.employeesdata.splice(36, 3);



		
		});
	}
	$scope.getData();



		  
	$scope.mychart = {
			greeting : "Sydney Employees",
			labels : ['2006', '2007', '2008', '2009', '2010', '2011', '2012'],
			data : [
				[65, 59, 80, 81, 56, 55, 40],
			    [28, 48, 40, 19, 86, 27, 90]
					],
			series : ['Series A', 'Series B']
		}; 

	$scope.mychart2 = {
			greeting : "Melbourne Employees",
			labels : ['2006', '2007', '2008', '2009', '2010', '2011', '2012'],
			data : [
			    [28, 48, 40, 19, 86, 27, 90],
				[65, 59, 80, 81, 56, 55, 40]
					],
			series : ['Series C', 'Series D']
		}; 
	
	$scope.labels =  ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
	
	
	$scope.data =  [
		[65, 59, 80, 81, 56, 55, 40],
	    [28, 48, 40, 19, 86, 27, 90]
			];
	
	
	

	
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



	 $scope.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
	 $scope.data = [300, 500, 100];

	 
	 $scope.options = {
			  title: {
		            display: true,
		            text: 'Finance Chart Title'
		       },
			   legend: {
				   position : 'top',
		            display: true,
		            labels: {
		                fontColor: 'rgb(255, 99, 132)'
		            }
		        }
			  };	
	 
	
	   $scope.datasetOverride1 = { 
			   backgroundColor:  ['rgba(255, 0, 0, 0.5)', 'rgba(0, 255, 0, 0.7)', 'rgba(0, 0, 255, 0.5)'],
			   borderColor: ['rgba(0, 255, 0, 1)', 'rgba(0, 255, 0, 1)'], 
			   borderWidth: [2, 2, 2], 
			   hoverBorderColor: ['rgba(0, 255, 0, 1)', 'rgba(0, 255, 0, 1)'], 
			   hoverBorderWidth: [1, 1]
	   	}
	   ;
	   
	   
});





