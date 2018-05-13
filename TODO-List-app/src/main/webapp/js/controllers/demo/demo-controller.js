app.controller('DemoController', function($scope,  $http, $location, 
		DemoService, ListTasksByIdFactory, EditTasksFactory, $route, $routeParams) {
    
    var cat = DemoService.getGreeting();
    cat.then(function(response) {
        $scope.greeting = response.data;//don't forget "this" in the service
    })
    console.log($scope.greeting);
});
