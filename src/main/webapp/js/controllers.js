'use strict';

function ArticleListController($scope, $location, Article) {
	$scope.articles = Article.query();
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

function ArticleNewController($scope, $location, Article) {
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
			$location.path("/categorie/list");
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

function MenuCtrl($scope, $location, Categorie) {
	$scope.categories = Categorie.query();
	$scope.selectCategorie = function(categorie) {
		$scope.selectedCategorie = categorie;
	};
	$scope.dropCategorie = function() {
		$scope.selectedCategorie = null;
	};

	$scope.uploadArticles = function() {
		$scope.categories = Categorie.query();
		$location.path("/categorie/list");
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