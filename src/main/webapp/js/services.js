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
});