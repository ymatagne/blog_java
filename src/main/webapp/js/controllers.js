'use strict';

///////////////////////////////////////////////////////////////////////////
///                                                                     ///
///             Fonctions                                               ///
///                                                                     ///
///////////////////////////////////////////////////////////////////////////

function updateArticleToCategorie($scope, Article, $filter) {
    if ($scope.selectedCategorie != null) {
        $scope.articlesTmp = Article
            .query(function () {
                $scope.articles = $filter('filter')
                (
                    $scope.articlesTmp,
                    function (x) {
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
}

///////////////////////////////////////////////////////////////////////////
///                                                                     ///
///             Controleurs sur la gestion des articles                 ///
///                                                                     ///
///////////////////////////////////////////////////////////////////////////
function ArticleListController($scope, $location, $cookieStore, $filter, $routeParams, SharedProperties, Article) {
    $scope.cookieStore = $cookieStore;
    $scope.sharedProperties = SharedProperties;
    $scope.user = $scope.cookieStore.get('USER');
    $('.menu-gauche  li').removeClass('active');
    if ($routeParams.categorie == null || $routeParams.categorie == 'all') {
        $scope.articles = Article.query();
        $('#categorie_tous').parent().addClass('active');
    } else {
        $('#lien__categorie_' + $routeParams.categorie).parent().addClass('active')
        $scope.articles = null;
        $scope.articlesTmp = Article
            .query(function () {
                $scope.articles = $filter('filter')
                (
                    $scope.articlesTmp,
                    function (x) {
                        var isOK = false;
                        if (x.categorie != null) {
                            isOK = x.categorie.nom == $routeParams.categorie;
                        }
                        return isOK;
                    });
            });
    }

    /* $scope.selectCategorie = function (categorie) {
     $scope.selectedCategorie = categorie;
     $('.menu-gauche  li').removeClass('active');
     if (categorie != null) {
     $('#lien__categorie_' + categorie.id).parent().addClass('active')
     } else {
     $('#categorie_tous').parent().addClass('active');
     }
     updateArticleToCategorie($scope, Article, $filter);
     };*/
    $scope.filterFunction = function (dataItem) {
        return dataItem.valide == true;
    };
    $scope.gotoArticle = function (article) {
        $location.path('/article/' + article.categorie.nom + '/' + article.id);

    }
    $scope.goToArticleAuteur = function (article) {
        $location.path('/auteur/' + article.auteur.id);
    };
}

function ArticleDetailController($scope, $routeParams, $location, $filter, $anchorScroll, $cookieStore, Article, Commentaire) {
    $scope.commentaire = new Commentaire();
    $scope.cookieStore = $cookieStore;
    Article.get({
        id: $routeParams.id
    }, function (articleReturn) {
        $scope.article = articleReturn;
    });
    $scope.selectCategorie = function (categorie) {
        $scope.selectedCategorie = categorie;
        $('.menu-gauche  li').removeClass('active');
        if (categorie != null) {
            $('#lien__categorie_' + categorie.id).parent().addClass('active')
        } else {
            $('#categorie_tous').parent().addClass('active');
        }
        updateArticleToCategorie($scope, Article, $filter);
    };
    $scope.otherArticles = Article.query();
    $scope.goToComments = function () {
        $location.hash('titleComment');
        $anchorScroll();
    };
    $scope.gotoArticleListPage = function () {
        $location.path("/");
    };
    $scope.deleteComments = function (commentaire) {
        var index = $scope.article.commentaires.indexOf(commentaire);
        $scope.article.commentaires.splice(index, 1);
        Commentaire.save($scope.article, function () {
        });
    };
    $scope.submitForm = function () {
        $scope.commentaire.dateCreation = new Date();
        $scope.article.commentaires.push($scope.commentaire);
        Commentaire.save($scope.article, function () {
            $scope.commentaire = new Commentaire();
        });
    };
    $scope.goToArticleAuteur = function (article) {
        $location.path('/auteur/' + article.auteur.id);
    };
}

function ArticleNewController($scope, $location, $cookieStore, Article, Categorie) {
    $scope.categories = Categorie.query();
    $scope.submit = function () {
        $scope.article.auteur = $cookieStore.get('USER');
        Article.save($scope.article, function (articles) {
            $location.path('/article/list/admin');
        });
    };
    $scope.gotoArticleListPage = function () {
        $location.path("/article/list/admin");
    };
}

function ArticleUpdateController($scope, $location, $routeParams, $timeout, Article) {
    $scope.article = new Article();
    Article.get({
        id: $routeParams.id
    }, function (article) {
        $scope.article = article;
        $timeout(function () {
            tinyMCE.activeEditor.setContent(article.article);
        }, 500);
    });
    $scope.submit = function () {
        Article.save($scope.article, function (articles) {
            $location.path('/article/list/admin');
        });
    };
    $scope.gotoArticleListPage = function () {
        $location.path("/article/list/admin");
    };
    $scope.deleteComments = function (commentaire) {
        var index = $scope.article.commentaires.indexOf(commentaire);
        $scope.article.commentaires.splice(index, 1);
        Commentaire.save($scope.article, function () {
        });
    };
}

///////////////////////////////////////////////////////////////////////////
///                                                                     ///
///             Controleurs sur la gestion des catégories               ///
///                                                                     ///
///////////////////////////////////////////////////////////////////////////
function CategorieListController($scope, $location, Categorie) {
    $scope.categories = Categorie.query();
    $scope.gotoCategorieNewPage = function () {
        $location.path("/categorie/new");
    };
    $scope.deleteCategorie = function (categorie) {
        categorie.$delete({
            'id': categorie.id
        }, function () {
            $location.path('/categorie/list/');
        });
    };
}

function CategorieDetailController($scope, $routeParams, $location, Categorie) {
    $scope.categorie = Categorie.get({
        id: $routeParams.id
    }, function (categorie) {
    });
    $scope.gotoCategorieListPage = function () {
        $location.path("/categorie/list");
    };
}

function CategorieNewController($scope, $location, Categorie) {
    $scope.submit = function () {
        Categorie.save($scope.categorie, function (categories) {
            $location.path("/categorie/list");
        });
    };
    $scope.gotoCategorieListPage = function () {
        $location.path("/categorie/list");
    };
}

function CategorieUpdateController($scope, $location, $routeParams, $timeout, Categorie) {
    $scope.categorie = new Categorie();
    Categorie.get({
        id: $routeParams.id
    }, function (categorie) {
        $scope.categorie = categorie;
    });
    $scope.submit = function () {
        Categorie.save($scope.categorie, function (categories) {
            $location.path("/categorie/list");
        });
    };
    $scope.gotoCategorieListPage = function () {
        $location.path("/categorie/list");
    };
}

///////////////////////////////////////////////////////////////////////////
///                                                                     ///
///             Controleurs sur le menu                                 ///
///                                                                     ///
///////////////////////////////////////////////////////////////////////////
function EditoCtrl($scope, $location) {
    $scope.selectCategorie = function (categorie) {
        $location.path("/article/" + categorie);
    };
}
function MenuCtrl($scope, $location, $modal, $cookieStore, $filter, $http, Auteur, SharedProperties, Article, Categorie) {
    $scope.invalidAdmin = null;
    $scope.categories = Categorie.query();
    $scope.user = $cookieStore.get('USER');
    SharedProperties.setProperty("");
    $scope.gotoArticleNewPage = function () {
        $location.path("/article/new");
    };
    $scope.gotoCategorieListPage = function () {
        $location.path("/categorie/list");
    };
    $scope.gotoAuteurListPage = function () {
        $location.path("/auteur/list");
    };
    $scope.gotoHome = function () {
        $scope.selectedCategorie = null;
        $location.path("/");
    };
    $scope.searchChange = function (searchModel) {
        SharedProperties.setProperty(searchModel);
    };
    $scope.gotoListArticle = function () {
        $scope.invalidAdmin = false;
        $location.path('/');
    };

    $scope.gotoListInvalidArticle = function () {
        $scope.selectedCategorie = null;
        $location.path('/article/list/admin');
    };
    $scope.dropCategorie = function () {
        $scope.selectedCategorie = null;
        $scope.articles = Article.query();
        $location.path('/');

    };
    $scope.openPopupIdentification = function () {
        var modalInstance = $modal.open({
            templateUrl: 'partials/directives/login.html',
            controller: AuteurControlleur
        });

        modalInstance.result.then(function () {
            $scope.user = $cookieStore.get('USER');
        }, function () {

        });
    };
    $scope.uploadArticles = function () {
        $scope.categories = Categorie.query();
        $location.path("/categorie/list");
    };
    $scope.logout = function () {
        $scope.user = null;
        $http.get('j_spring_security_logout').success(
            function (data, status, headers, config) {
                $cookieStore.put('USER', null);
            });
        $location.path("/");
    };
}
///////////////////////////////////////////////////////////////////////////
///                                                                     ///
///             Controleurs sur la gestion des auteurs                  ///
///                                                                     ///
///////////////////////////////////////////////////////////////////////////
function AuteurControlleur($scope, $http, $cookieStore, $modalInstance, $location, Auteur) {
    jQuery('#menuAdmin').hide();
    $scope.erreurLoginPassword = true;
    $scope.user = new Auteur();
    $scope.login = function () {

        var data = "j_username=" + $scope.user.email + "&j_password="
            + $scope.user.password + "&submit=Login";
        $http.post('j_spring_security_check', data, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        }).success(function (data, status, headers, config) {
            $scope.erreurLoginPassword = true;
            Auteur.get({
                id: data
            }, function (user) {
                $cookieStore.put('USER', user);
                $modalInstance.close();
                $scope.user = user;
            });

        }).error(function (data, status, headers, config) {
            $scope.erreurLoginPassword = false;
        });
    };
    $scope.annuler = function () {
        $modalInstance.dismiss('cancel');
    };
}

function AuteurListController($scope, $location, $filter, Auteur) {

    $scope.auteurs = Auteur.query();

    $scope.gotoAuteurNewPage = function () {
        $location.path("/auteur/new");
    };
    $scope.deleteAuteur = function (auteur) {
        auteur.$delete({
            'id': auteur.id
        }, function () {
            $scope.auteurs = Auteur.query();
            $location.path("/auteur/list");
        });
    };
}

function AuteurDetailController($scope, $routeParams, $location, Auteur) {
    $scope.auteur = Auteur.get({
        id: $routeParams.id
    }, function (auteur) {
    });
    $scope.gotoAuteurListPage = function () {
        $location.path("/");
    };
}

function AuteurNewController($http, $scope, $location, Auteur) {
    $scope.erreur = false;
    $scope.submit = function () {

        Auteur.save($scope.auteur, function (auteur) {
            if (auteur.id == null && auteur.email == null) {
                $scope.erreur = true;
            } else {
                $location.path("/auteur/list");
            }
        });
    };
    $scope.gotoAuteurListPage = function () {
        $location.path("/auteur/list");
    };
}

function AuteurUpdateController($scope, $location, $routeParams, Auteur) {
    $scope.auteur = Auteur.get({
        id: $routeParams.id
    }, function (auteur) {
        $scope.auteur = auteur;
    });
    $scope.submit = function () {
        Auteur.save($scope.auteur, function (auteurs) {
            $location.path("/auteur/list");
        });
    };
    $scope.gotoAuteurListPage = function () {
        $location.path("/auteur/list");
    };
}