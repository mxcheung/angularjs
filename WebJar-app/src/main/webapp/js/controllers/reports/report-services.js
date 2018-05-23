app.service('ReportCashBalanceService', function($http) {
	this.getAccountCashBalanceSummary = function() {
		return $http({
			method : 'GET',
			url : './report/cash-balance',
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


});
