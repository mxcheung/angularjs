var routes = function routes($routeProvider) {
				$routeProvider
				.when('/',
				{
					controller: 'ListTasksController',
					templateUrl: '/views/tasks.html'
				})
				.when('/hello',
				{
					controller: 'HelloController',
					templateUrl: '/views/hello/hello.html'
				})
				.when('/there',
				{
					controller: 'ThereController',
					templateUrl: '/views/there/there.html'
				})
				.when('/addTask',
				{
					controller: 'AddTasksController',
					templateUrl: '/views/addTasks.html'
				})
				.when('/editTask:id',
				{
					controller: 'EditTasksController',
					templateUrl: '/views/editTasks.html'
				})
				.when('/completedTasks',
				{
					controller: 'ListTasksController',
					templateUrl: '/views/completedTasks.html'
				})				
				.otherwise({
					redirectTo: '/#'
				})

		});
		