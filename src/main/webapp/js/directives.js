'use strict';

/* Directives */

angular.module('blogApp.directives', []).directive('footer', function() {
	return {
		templateUrl : "partials/directives/footer.html"
	};
}).directive('edito', function() {
	return {
		templateUrl : "partials/directives/edito.html"
	};
}).directive('menu', function() {
	return {
		templateUrl : "partials/directives/menu.html",
		controller : MenuCtrl
	};
}).directive('authentification', function() {
	return {
		templateUrl : "partials/directives/login.html",
		controller : AuteurControlleur
	};
});
