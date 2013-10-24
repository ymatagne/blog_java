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
				admin : false
			}).when('/article/new', {
				templateUrl : 'partials/article/article-new.html',
				controller : ArticleNewController,
				admin : true
			}).when('/article/update/:id', {
				templateUrl : 'partials/article/article-update.html',
				controller : ArticleUpdateController,
				admin : true
			}).when('/article/:id', {
				templateUrl : 'partials/article/article-detail.html',
				controller : ArticleDetailController,
				admin : false
			})

			.when('/categorie/list', {
				templateUrl : 'partials/categorie/categorie-list.html',
				controller : CategorieListController,
				admin : false
			}).when('/categorie/new', {
				templateUrl : 'partials/categorie/categorie-new.html',
				controller : CategorieNewController,
				admin : true
			}).when('/categorie/:id', {
				templateUrl : 'partials/categorie/categorie-detail.html',
				controller : CategorieDetailController,
				admin : false
			}).when('/categorie/:id', {
				templateUrl : 'partials/categorie/categorie-detail.html',
				controller : CategorieDetailController,
				admin : false
			})

			.when('/auteur/list', {
				templateUrl : 'partials/auteur/auteur-list.html',
				controller : AuteurListController,
				admin : false
			}).when('/auteur/new', {
				templateUrl : 'partials/auteur/auteur-new.html',
				controller : AuteurNewController,
				admin : true
			}).when('/auteur/:id', {
				templateUrl : 'partials/auteur/auteur-detail.html',
				controller : AuteurDetailController,
				admin : false
			}).when('/auteur/:id', {
				templateUrl : 'partials/auteur/auteur-detail.html',
				controller : AuteurDetailController,
				admin : false
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
				var userIsAdmin = false;
				if ($cookieStore.get('USER') != null
						&& $cookieStore.get('USER').admin != null) {
					userIsAdmin = $cookieStore.get('USER').admin;
				}
				if (next.admin && !userIsAdmin) {
					$location.path('/article/list');
				}
			});
		}).factory('initCookie', function($cookieStore, $http) {
	return function(scope) {
		$cookieStore.put('USER', null);
	};
});