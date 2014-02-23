'use strict';

function ArticleListController($scope, $location, $cookieStore, $filter,
		SharedProperties, Article) {
	$scope.cookieStore = $cookieStore;
	$scope.sharedProperties = SharedProperties;
	if ($scope.selectedCategorie != null) {
		$scope.articlesTmp = Article
				.query(function() {
					$scope.articles = $filter('filter')
							(
									$scope.articlesTmp,
									function(x) {
										var isOK = false;
										if (x.categorie != null) {
											isOK = x.categorie.id == $scope.selectedCategorie.id;
										}
										return isOK;
									});
				});
	} else {
		$scope.articles = Article.query();
	}
	$scope.filterFunction = function(dataItem) {
		if ($scope.cookieStore.get('USER') != null
				&& $scope.cookieStore.get('USER').admin) {
			if ($scope.invalidAdmin == true) {
				return dataItem.valide == false;
			}
			return true;
		} else {
			return dataItem.valide == true;
		}
	};
	$scope.valid = function(article, valid) {
		article.valide = valid;
		Article.save(article, function(articles) {
			$location.path('/');
		});
	};
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
	$scope.goToArticleAuteur=function(article){
		$location.path('/auteur/'+article.auteur.id);
	};
}

function ArticleDetailController($scope, $routeParams, $location,
		$anchorScroll, $cookieStore, Article, Commentaire) {
	$scope.commentaire = new Commentaire();
	$scope.cookieStore = $cookieStore;
	$scope.article = Article.get({
		id : $routeParams.id
	}, function(article) {
	});
	$scope.otherArticles = Article.query();
	$scope.goToComments = function() {
		$location.hash('titleComment');
		$anchorScroll();
	};
	$scope.gotoArticleListPage = function() {
		$location.path("/");
	};
	$scope.deleteComments = function(commentaire) {
		var index = $scope.article.commentaires.indexOf(commentaire);
		$scope.article.commentaires.splice(index, 1);
		Commentaire.save($scope.article, function() {
		});
	};
	$scope.submitForm = function() {
		$scope.commentaire.dateCreation = new Date();
		$scope.article.commentaires.push($scope.commentaire);
		Commentaire.save($scope.article, function() {
			$scope.commentaire = new Commentaire();
		});
	};
	$scope.goToArticleAuteur=function(article){
		$location.path('/auteur/'+article.auteur.id);
	};
}

function ArticleNewController($scope, $location, $cookieStore, Article,
		Categorie) {
	$scope.categories = Categorie.query();
	$scope.submit = function() {
		$scope.article.auteur = $cookieStore.get('USER');
		Article.save($scope.article, function(articles) {
			$location.path('/');
		});
	};
	$scope.gotoArticleListPage = function() {
		$location.path("/");
	};
}

function ArticleUpdateController($scope, $location, $routeParams, $timeout,
		Article) {
	$scope.article = new Article();
	Article.get({
		id : $routeParams.id
	}, function(article) {
		$scope.article = article;
		$timeout(function() {
			tinyMCE.activeEditor.setContent(article.article);
		}, 500);
	});
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

function CategorieUpdateController($scope, $location, $routeParams, $timeout,
		Categorie) {
	$scope.categorie = new Categorie();
	Categorie.get({
		id : $routeParams.id
	}, function(categorie) {
		$scope.categorie = categorie;
	});
	$scope.submit = function() {
		Categorie.save($scope.categorie, function(categories) {
			$location.path("/categorie/list");
		});
	};
	$scope.gotoCategorieListPage = function() {
		$location.path("/categorie/list");
	};
}

function MenuCtrl($scope, $location, SharedProperties, Article, Categorie) {
	$scope.invalidAdmin = null;
	$scope.categories = Categorie.query();
	SharedProperties.setProperty("");
	$scope.searchChange = function(searchModel) {
		SharedProperties.setProperty(searchModel);
	};
	$scope.gotoListArticle = function() {
		$scope.invalidAdmin = false;
		$location.path('/');
	};
	$scope.selectCategorie = function(categorie) {
		$scope.selectedCategorie = categorie;
		$location.path('/');
	};
	$scope.gotoListInvalidArticle = function() {
		$scope.articles = Article.query();
		$scope.invalidAdmin = true;
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

function AuteurControlleur($scope, $http, $cookieStore, $location, Auteur) {
	jQuery('#menuAdmin').hide();
	$scope.auteur = new Auteur();
	$scope.gotoArticleNewPage = function() {
		$location.path("/article/new");
	};
	$scope.gotoCategorieListPage = function() {
		$location.path("/categorie/list");
	};
	$scope.gotoAuteurListPage = function() {
		$location.path("/auteur/list");
	};
	$scope.gotoHome = function() {
		$location.path("/");
	};
	$scope.login = function() {

		var data = "j_username=" + $scope.auteur.email + "&j_password="
				+ $scope.auteur.password + "&submit=Login";
		$http.post('j_spring_security_check', data, {
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded',
			}
		}).success(function(data, status, headers, config) {
			$scope.auteur = Auteur.get({
				id : data
			}, function(auteur) {
				$cookieStore.put('USER', auteur);
				jQuery('#loging').hide();
				jQuery('#menuAdmin').show();
			});

		}).error(function(data, status, headers, config) {
			$scope.auteur = new Auteur();
			$cookieStore.put('USER', auteur.email);
			jQuery('#loginAlert').html("Wrong username or password !");
			jQuery('#loginAlert').show();
			setTimeout(function() {
				jQuery('#loginAlert').hide();
			}, 4000);
			$location.path("/");
		});
	};
	$scope.logout = function() {
		$scope.auteur = new Auteur();
		$http.get('j_spring_security_logout').success(
				function(data, status, headers, config) {
					$cookieStore.put('USER', null);
					jQuery('#loging').show();
					jQuery('#menuAdmin').hide();
					jQuery('#loginAlert').html("logged out");
					setTimeout(function() {
						jQuery('#loginAlert').hide();
					}, 2000);
					$location.path("/");
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
			$scope.auteurs = Auteur.query();
			$location.path("/auteur/list");
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

function AuteurNewController($http, $scope, $location, Auteur) {
	$scope.auteur = new Auteur();
	$scope.erreur=false;
	$scope.submit = function() {

		Auteur.save($scope.auteur, function(auteur) {
			if (auteur.id == null && auteur.email == null) {
				$scope.erreur=true;
			} else {
				$location.path("/auteur/list");
			}
		});
	};
	$scope.gotoAuteurListPage = function() {
		$location.path("/auteur/list");
	};
}

function AuteurUpdateController($scope, $location, $routeParams, Auteur) {
	$scope.auteur = Auteur.get({
		id : $routeParams.id
	}, function(auteur) {
		$scope.auteur = auteur;
	});
	$scope.submit = function() {
		Auteur.save($scope.auteur, function(auteurs) {
			$location.path("/auteur/list");
		});
	};
	$scope.gotoAuteurListPage = function() {
		$location.path("/auteur/list");
	};
}