app.service('SmartTableService', function($http) {
	this.getAllEmployees = function() {
		return $http({
			method : 'GET',
			url : 'http://dummy.restapiexample.com/api/v1/employees',
			headers : {
				'Access-Control-Allow-Origin' : '*',
				'Upgrade-Insecure-Requests': '1',
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		
		// http://localhost:8005/nodejs-example-app-herokuapp-com/heroes
		// https://nodejs-example-app.herokuapp.com/heroes
		// set the headers so angular passing info as form data (not request
		// payload)
		}).then(function(data) {
			return data;
		})
	}


});

