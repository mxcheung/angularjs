app.service('HeroesService', function($http) {
	this.getAllHeroes = function() {
		return $http({
			method : 'GET',
			url : 'http://localhost:8005/nodejs-example-app-herokuapp-com/heroes',
			headers : {
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

	this.getCashDeposits = function() {
		return $http({
			method : 'GET',
			url : 'http://localhost:8005/cashmgmt/deposits',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		
		// set the headers so angular passing info as form data (not request
		// payload)
		}).then(function(data) {
			return data;
		})
	}

});
