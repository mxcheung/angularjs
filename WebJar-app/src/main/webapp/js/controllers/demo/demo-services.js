app.service('DemoService', function( $http) {
	
	 this.getGreeting = function() {

	        return $http({
	            method: 'GET',
	            url: 'http://rest-service.guides.spring.io/greeting',
	            // pass in data as strings
	            headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
	        })
	                .then(function(data) {

	                    return data;


	                })


	    }
	 
	 
});
