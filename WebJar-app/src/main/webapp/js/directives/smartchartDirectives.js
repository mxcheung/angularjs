angular.module('myApp.smartchartDirective', [])
.directive('smartchartDirective',  function() {
    return {
       restrict: 'EA', //E = element, A = attribute, C = class, M = comment         
       scope: {  data: '=' },
       templateUrl: '/views/directives/smartchart-directive.html',
   };
});
