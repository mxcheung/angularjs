angular.module('myApp.tableDirective', [])
.directive('tableDirective',  function() {
    return {
       restrict: 'EA', //E = element, A = attribute, C = class, M = comment         
       scope: {  data: '='},
       templateUrl: '/views/directives/table-directive.html',
   };
});
