app.controller('DateTimePickerController',function ($scope, $timeout) {
	$scope.dateTimeNow = function() {
    $scope.date = new Date();
	};
	$scope.dateTimeNow();
  
	$scope.toggleMinDate = function() {
		var minDate = new Date();
			//Minimum date set to now
		minDate.setDate(minDate.getDate());
		$scope.minDate = $scope.minDate ? null : minDate;
	};
   
	$scope.toggleMinDate();

	$scope.dateOptions = {
		showWeeks: false
	};
  
	$scope.open = function($event,opened) {
		$event.preventDefault();
		$event.stopPropagation();
    	$scope.dateOpened = true;
	};
  
	$scope.dateOpened = false;
	$scope.hourStep = 1;
	$scope.format = "dd-MM-yyyy";
	$scope.minuteStep = 1;

	$scope.showMeridian = true;
	$scope.timeToggleMode = function() {
		$scope.showMeridian = !$scope.showMeridian;
	};
  
	$scope.$watch("date", function(date) {
		// read date value
	}, true);
  
	$scope.resetHours = function() {
		$scope.date.setHours(1);
	};
});


/* Common Controllers goes here */
app.controller('DatePickerController', function($scope, DatePickerService) {

    // Initialize the date options with the given values
    $scope.initDateOptions = function() {
    	$scope.dateOptions.maxDate = moment().add(14, 'days').toDate();
    }
    
    $scope.reload = function() {
        $scope.datePicker = {};
        $scope.datePicker.maxDate = moment().add(1, 'days').toDate();
	};
	$scope.reload();
	
	$scope.dateOptions = {
		dateDisabled : disabled,
		formatYear : 'yy',
		maxDate : $scope.datePicker.maxDate,
		minDate : $scope.datePicker.minDate,
		startingDay : 1,
		showWeeks : false
	};
	
	$scope.dateOptionsUnboundedDate = {
		dateDisabled : disabled,
		formatYear : 'yy',
		startingDay : 1,
		showWeeks : false
	};
	
	$scope.dateOptionsFilter = {
		formatYear : 'yy',
		startingDay : 1,
		showWeeks : false,
		clearBtn : true
	}

	// Disable weekend selection
	function disabled (data) {
		var date = data.date, mode = data.mode;
		switch (mode) {
		case 'day':
			return DatePickerService.isWeekend(date);
		default:
			return false;
		}
	}

	$scope.open = function() {
		$scope.popup.opened = true;
	};

	$scope.popup = {
		opened : false
	};
});




app.service('DatePickerService', function($http, $q) {
	return {
        /*
         * Formats a date as an ISO-8601 date (yyyy-MM-dd) for use in a request.
         */
        formatDateForRequest: function(date) {
        	if(date) {
        		return moment(date).format('YYYY-MM-DD');
        	}
        },

        /*
         * Formats a date for start-of-day comparison.
         */
        formatDateForComparison: function(date) {
            return moment(date).startOf('day').format();
        },
        
        /*
         * Formats a date for date and time
         */
        formatDateWithTime: function(date) {
        	return moment(date).format("YYYY-MM-DDTHH:mm:ss.SSS");
        },
        
        /*
         * Checks if given date is weekend or not. Return true if weekend
         */
    	isWeekend : function(date) {
    		var day = date.getDay();
    		var weekend = (day == 6) || (day == 0); 
    		return weekend;
    	},
    	
	};
});
