angular.module('myApp.smarttableDirective', [])
.directive('smarttableDirective',  function() {
    return {
       restrict: 'EA', //E = element, A = attribute, C = class, M = comment         
       scope: {  data: '='},
       templateUrl: '/views/directives/smarttable-directive.html',
   };
});
