'use strict';

function ArticleListController($scope, $location, Article) {
    $scope.articles = Article.query();
    $scope.gotoArticleNewPage = function () {
        $location.path("/article/new");
    };
    $scope.deleteArticle = function (article) {
        article.$delete({'id':article.id}, function () {
            $location.path('/');
        });
    };
}

function ArticleDetailController($scope, $routeParams, $location, Article) {
    $scope.article = Article.get({id:$routeParams.id}, function (article) {
    });
    $scope.gotoArticleListPage = function () {
        $location.path("/");
    };
}

function ArticleNewController($scope, $location, Article) {
    $scope.submit = function () {
        Article.save($scope.article, function (articles) {
            $location.path('/');
        });
    };
    $scope.gotoArticleListPage = function () {
        $location.path("/");
    };
}