angular.module("toDoListApp.w3TestDirective", [])
.directive("w3TestDirective", function() {
    return {
        template : "<h1>Made by a directive!</h1>"
    };
});