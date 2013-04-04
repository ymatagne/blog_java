'use strict';

// Declare app level module which depends on filters, and services
angular.module('blogApp',
		[ 'blogApp.filters', 'blogApp.services', 'blogApp.directives' ])
		.config([ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/article/list', {
				templateUrl : 'partials/article/article-list.html',
				controller : ArticleListController
			}).when('/article/new', {
				templateUrl : 'partials/article/article-new.html',
				controller : ArticleNewController
			}).when('/article/:id', {
				templateUrl : 'partials/article/article-detail.html',
				controller : ArticleDetailController
			}).otherwise({
				redirectTo : '/article/list'
			});
		} ]);