var app = angular.module('ToDoListApp', [
	'ngRoute',
	'ngResource',
	'ui.bootstrap', 
	'ui.bootstrap.datetimepicker',
	'chart.js',
	'toDoListApp.w3TestDirective',
	'myApp.highchartDirectives',
	'myApp.tableDirective',
	'myApp.hightableDirective',
	'myApp.smartchartDirective',
	'myApp.smarttableDirective',
	'myApp.flotDirective',
	'tableExample',
	'myDirectives'
	])

	//Define routes to cashmgmt web pages.
	app
	.config(function($routeProvider){
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
				.when('/chartist',
				{
					controller: 'ChartistController',
					templateUrl: '/views/chartist/chartist.html'
				})
				.when('/smartchart',
				{
					controller: 'SmartChartController',
					templateUrl: '/views/smartchart/smartchart.html'
				})
				.when('/smartchartpie',
				{
					controller: 'SmartChartPieController',
					templateUrl: '/views/smartchartpie/smartchartpie.html'
				})
				.when('/smartchartfinancial',
				{
					controller: 'SmartChartFinancialController',
					templateUrl: '/views/smartchartfinancial/smartchartfinancial.html'
				})
				.when('/smarttable',
				{
					controller: 'SmartTableController',
					templateUrl: '/views/smarttable/smarttable.html'
				})
				.when('/smarttablercv',
				{
					controller: 'SmartTableRCVController',
					templateUrl: '/views/smarttablercv/smarttablercv.html'
				})
				.when('/highchartfinancial',
				{
					controller: 'HighChartFinancialController',
					templateUrl: '/views/highchartfinancial/highchartfinancial.html'
				})
				.when('/highstock',
				{
					controller: 'HighStockController',
					templateUrl: '/views/highstock/highstock.html'
				})
				.when('/highreport',
				{
					controller: 'HighReportController',
					templateUrl: '/views/highreport/highreport.html'
				})
				.when('/flot',
				{
					controller: 'FlotController',
					templateUrl: '/views/flot/flot.html'
				})
				.when('/morris',
				{
					controller: 'MorrisController',
					templateUrl: '/views/morris/morris.html'
				})
				.when('/highchartcombo',
				{
					controller: 'HighChartComboController',
					templateUrl: '/views/highchartcombo/highchartcombo.html'
				})
				.when('/highchartpie',
				{
					controller: 'HighChartPieController',
					templateUrl: '/views/highchartpie/highchartpie.html'
				})
				.when('/hightable',
				{
					controller: 'HighTableController',
					templateUrl: '/views/hightable/hightable.html'
				})
				.when('/reports',
				{
					controller: 'ReportController',
					templateUrl: '/views/reports/cash-balances.html'
				})
				.when('/there',
				{
					controller: 'ThereController',
					templateUrl: '/views/there/there.html'
				})
				.when('/demo',
				{
					controller: 'DemoController',
					templateUrl: '/views/demo/demo.html'
				})
				.when('/heroes',
				{
					controller: 'HeroesController',
					templateUrl: '/views/heroes/heroes.html'
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
				});
		});
		