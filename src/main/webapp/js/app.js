'use strict';
// Declare module and path for application
angular.module(
		'blogApp',
		[ 'ngCookies', 'ui.tinymce', 'ui.bootstrap', 'blogApp.filters',
				'blogApp.services', 'blogApp.directives' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/article/list', {
				templateUrl : 'partials/article/article-list.html',
				controller : ArticleListController,
				role : 'USER'
			}).when('/article/new', {
				templateUrl : 'partials/article/article-new.html',
				controller : ArticleNewController,
				role : 'AUTHOR'
			}).when('/article/update/:id', {
				templateUrl : 'partials/article/article-update.html',
				controller : ArticleUpdateController,
				role : 'ADMIN'
			}).when('/article/:id', {
				templateUrl : 'partials/article/article-detail.html',
				controller : ArticleDetailController,
				role : 'USER'
			})

			.when('/categorie/list', {
				templateUrl : 'partials/categorie/categorie-list.html',
				controller : CategorieListController,
				role : 'USER'
			}).when('/categorie/new', {
				templateUrl : 'partials/categorie/categorie-new.html',
				controller : CategorieNewController,
				role : 'ADMIN'
			}).when('/categorie/:id', {
				templateUrl : 'partials/categorie/categorie-detail.html',
				controller : CategorieDetailController,
				role : 'ADMIN'
			}).when('/categorie/update/:id', {
				templateUrl : 'partials/categorie/categorie-update.html',
				controller : CategorieUpdateController,
				role : 'ADMIN'
			})

			.when('/auteur/list', {
				templateUrl : 'partials/auteur/auteur-list.html',
				controller : AuteurListController,
				role : 'USER'
			}).when('/auteur/new', {
				templateUrl : 'partials/auteur/auteur-new.html',
				controller : AuteurNewController,
				role : 'ADMIN'
			}).when('/auteur/:id', {
				templateUrl : 'partials/auteur/auteur-detail.html',
				controller : AuteurDetailController,
				role : 'USER'
			}).when('/auteur/update/:id', {
				templateUrl : 'partials/auteur/auteur-update.html',
				controller : AuteurUpdateController,
				role : 'ADMIN'
			})

			.when('/404', {
				templateUrl : 'partials/authentification/erreur.html'
			})

			.otherwise({
				redirectTo : '/article/list'
			});
		} ]).run(
		function($rootScope, $cookieStore, $location, initCookie) {
			initCookie($rootScope);
			$rootScope.$on("$routeChangeStart", function(event, next, current) {
				var role = 'USER';
				if ($cookieStore.get('USER') != null) {
					role = 'AUTHOR';
					if ($cookieStore.get('USER').admin == true) {
						role = 'ADMIN';
					}
				}
				if (next.role == 'AUTHOR' && role == 'USER') {
					$location.path('/article/list');
				} else if (next.role == 'ADMIN'
						&& (role == 'USER' || role == 'AUTHOR')) {
					$location.path('/article/list');
				}
			});
		}).factory('initCookie', function($cookieStore, $http) {
	return function(scope) {
		$cookieStore.put('USER', null);
	};
});