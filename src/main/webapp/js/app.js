'use strict';
// Declare app level module which depends on filters, and services
angular.module(
		'blogApp',
		[ 'ui.bootstrap', 'blogApp.filters', 'blogApp.services',
				'blogApp.directives' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/article/list', {
				templateUrl : 'partials/article/article-list.html',
				controller : ArticleListController
			}).when('/article/new', {
				templateUrl : 'partials/article/article-new.html',
				controller : ArticleNewController
			}).when('/article/:id', {
				templateUrl : 'partials/article/article-detail.html',
				controller : ArticleDetailController
			}).when('/article/:id', {
				templateUrl : 'partials/article/article-detail.html',
				controller : ArticleDetailController
			})

			.when('/categorie/list', {
				templateUrl : 'partials/categorie/categorie-list.html',
				controller : CategorieListController
			}).when('/categorie/new', {
				templateUrl : 'partials/categorie/categorie-new.html',
				controller : CategorieNewController
			}).when('/categorie/:id', {
				templateUrl : 'partials/categorie/categorie-detail.html',
				controller : CategorieDetailController
			}).when('/categorie/:id', {
				templateUrl : 'partials/categorie/categorie-detail.html',
				controller : CategorieDetailController
			})

			.otherwise({
				redirectTo : '/article/list'
			});
		} ]);