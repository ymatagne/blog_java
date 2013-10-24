'use strict';
angular.module('blogApp.services', [ 'ngResource' ]).factory('Article',
		function($resource) {
			return $resource('rest/article/:id', {}, {
				'save' : {
					method : 'PUT'
				}
			});
		}).factory('Categorie', function($resource) {
	return $resource('rest/categorie/:id', {}, {
		'save' : {
			method : 'PUT'
		}
	});
}).factory('Auteur', function($resource) {
	return $resource('rest/auteur/:id', {}, {
		'save' : {
			method : 'PUT'
		}
	});
}).factory('Commentaire', function($resource) {
	return $resource('rest/commentaire/:id', {}, {
		'save' : {
			method : 'PUT'
		}
	});
}).factory('myHttpInterceptor', function($q) {
	return function(promise) {
		return promise.then(function(response) {
			return response;
		}, function(response) {
			window.location = '/blog/#/404';
		});
	};
}).config(function($httpProvider) {
	$httpProvider.responseInterceptors.push('myHttpInterceptor');
});