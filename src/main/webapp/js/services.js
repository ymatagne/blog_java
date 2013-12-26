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
			method : 'PUT',
			headers : {
				'Content-Type' : 'application/json; charset=utf-8',
				'Accept' : 'application/json, text/javascript, /;'
			}
		}
	});
}).factory('Commentaire', function($resource) {
	return $resource('rest/commentaire/:id', {}, {
		'save' : {
			method : 'PUT'
		}
	});
}).service('SharedProperties', function() {
	var property = 'SearchText';

	return {
		getProperty : function() {
			return property;
		},
		setProperty : function(value) {
			property = value;
		}
	};
}).factory(
		'myHttpInterceptor',
		function($q) {
			return function(promise) {
				return promise.then(function(response) {
					return response;
				}, function(response) {
					if (response.config.url == "rest/auteur"
							&& response.config.method == "PUT") {

					} else {
						window.location = '/blog/#/404';
					}
				});
			};
		}).config(function($httpProvider) {
	$httpProvider.responseInterceptors.push('myHttpInterceptor');
});
