app.service('HeroesService', function($http) {
	this.getAllHeroes = function() {
		return $http({
			method : 'GET',
			url : 'https://nodejs-example-app.herokuapp.com/heroes',
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
