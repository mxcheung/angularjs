app.controller('SmartChartController', function($scope, $http, $location,
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
	
});





