angular.module('myDirectives', []).
 directive('myAwesomeDirective', function() {
   return {
     restrict: 'E',
     template: '<h1>This is awesome!</h1>'
   };
 });