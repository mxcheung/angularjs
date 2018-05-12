app.controller('ThereController', function($scope, $location,
		ListTasksByIdFactory, EditTasksFactory, $route, $routeParams) {
	// Retrieve selected task
	var selectedTask = ListTasksByIdFactory.query({
		id : $routeParams.id
	});
	selectedTask.$promise.then(function(result) {
		// Populate scope variables
		$scope.editTaskCtrl = $scope.editTaskCtrl || {};
		$scope.editTaskCtrl.taskname = result.taskname;
		$scope.editTaskCtrl.performdate = new Date(result.performdate);
		$scope.editTaskCtrl.category = result.category;
		$scope.editTaskCtrl.priority = result.priority;
		$scope.editTaskCtrl.isdone = result.isdone;
	});

	this.editTask = function(editTaskCtrl) {
		EditTasksFactory.update({
			id : $routeParams.id
		}, editTaskCtrl);

		window.setTimeout(function() {
			$location.path('/');
		}, 10);

	};
});
