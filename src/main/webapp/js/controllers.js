'use strict';

function ArticleListController($scope, $location, $filter, Article) {
	$scope.articles = null;
	if ($scope.selectedCategorie != null) {
		$scope.articlesTmp = Article
				.query(function() {
					$scope.articles = $filter('filter')
							(
									$scope.articlesTmp,
									function(x) {
										if (x.categorie != null) {
											return x.categorie.id == $scope.selectedCategorie.id;
										}
										return false;
									});
				});
	} else {
		$scope.articles = Article.query();
	}

	$scope.gotoArticleNewPage = function() {
		$location.path("/article/new");
	};
	$scope.deleteArticle = function(article) {
		article.$delete({
			'id' : article.id
		}, function() {
			$location.path('/');
		});
	};
}

function ArticleDetailController($scope, $routeParams, $location, Article) {
	$scope.article = Article.get({
		id : $routeParams.id
	}, function(article) {
	});
	$scope.gotoArticleListPage = function() {
		$location.path("/");
	};
}

function ArticleNewController($scope, $location, Article, Categorie) {
	$scope.categories = Categorie.query();
	$scope.submit = function() {
		Article.save($scope.article, function(articles) {
			$location.path('/');
		});
	};
	$scope.gotoArticleListPage = function() {
		$location.path("/");
	};
}

function CategorieListController($scope, $location, Categorie) {
	$scope.categories = Categorie.query();
	$scope.gotoCategorieNewPage = function() {
		$location.path("/categorie/new");
	};
	$scope.deleteCategorie = function(categorie) {
		categorie.$delete({
			'id' : categorie.id
		}, function() {
			$location.path('/categorie/list/');
		});
	};
}

function CategorieDetailController($scope, $routeParams, $location, Categorie) {
	$scope.categorie = Categorie.get({
		id : $routeParams.id
	}, function(categorie) {
	});
	$scope.gotoCategorieListPage = function() {
		$location.path("/categorie/list");
	};
}

function CategorieNewController($scope, $location, Categorie) {
	$scope.submit = function() {
		Categorie.save($scope.categorie, function(categories) {
			$location.path("/categorie/list");
		});
	};
	$scope.gotoCategorieListPage = function() {
		$location.path("/categorie/list");
	};
}

function MenuCtrl($scope, $location, Article, Categorie) {
	$scope.categories = Categorie.query();
	$scope.selectCategorie = function(categorie) {
		$scope.selectedCategorie = categorie;
		$location.path('/');
	};
	$scope.dropCategorie = function() {
		$scope.selectedCategorie = null;
		$scope.articles = Article.query();
		$location.path('/');

	};

	$scope.uploadArticles = function() {
		$scope.categories = Categorie.query();
		$location.path("/categorie/list");
	};
}

function AuteurControlleur($scope, $http, $location, Auteur) {
	jQuery('#menuAdmin').hide();
	$scope.auteur = new Auteur();
	$scope.gotoArticleNewPage = function() {
		$location.path("/article/new");
	};
	$scope.gotoCategorieNewPage = function() {
		$location.path("/categorie/new");
	};
	$scope.gotoHome = function() {
		$location.path("/");
	};
	$scope.login = function() {
		var data = "j_username=" + $scope.auteur.email + "&j_password="
				+ $scope.auteur.password + "&submit=Login";
		$http
				.post('j_spring_security_check', data, {
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded',
					}
				})
				.success(
						function(data, status, headers, config) {
							jQuery('#loging').hide();
							jQuery('#menuAdmin').show();
						})
				.error(
						function(data, status, headers, config) {
							jQuery('#loginAlert').html(
									"Wrong username or password !");
							jQuery('#loginAlert').show();
							setTimeout(function() {
								jQuery('#loginAlert').hide();
							}, 4000);
						});
	};
	$scope.logout = function() {
		$http.get('j_spring_security_logout').success(
				function(data, status, headers, config) {
					jQuery('#loging').show();
					jQuery('#menuAdmin').hide();
					jQuery('#loginAlert').html("logged out");
					setTimeout(function() {
						jQuery('#loginAlert').hide();
					}, 4000);
				});
	};
}















function AuteurListController($scope, $location, $filter, Auteur) {
	
		$scope.auteurs = Auteur.query();

	$scope.gotoAuteurNewPage = function() {
		$location.path("/auteur/new");
	};
	$scope.deleteAuteur = function(auteur) {
		auteur.$delete({
			'id' : auteur.id
		}, function() {
			$location.path('/');
		});
	};
}

function AuteurDetailController($scope, $routeParams, $location, Auteur) {
	$scope.auteur = Auteur.get({
		id : $routeParams.id
	}, function(auteur) {
	});
	$scope.gotoAuteurListPage = function() {
		$location.path("/");
	};
}

function AuteurNewController($scope, $location, Auteur) {
	$scope.auteur = new Auteur();
	$scope.submit = function() {
		Auteur.save($scope.auteur, function(auteurs) {
			$location.path('/');
		});
	};
	$scope.gotoAuteurListPage = function() {
		$location.path("/");
	};
}






function TypeaheadCtrl($scope) {

	$scope.selected = undefined;
	$scope.states = [ 'Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
			'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia',
			'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas',
			'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts',
			'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana',
			'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico',
			'New York', 'North Dakota', 'North Carolina', 'Ohio', 'Oklahoma',
			'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina',
			'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
			'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming' ];
}