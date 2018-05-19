angular.module('myApp.hightableDirective', [])
.directive('hightableDirective',  function() {
    return {
       restrict: 'EA', //E = element, A = attribute, C = class, M = comment         
       scope: {  data: '='},
       templateUrl: '/views/directives/hightable-directive.html',
   };
});
