'use strict';

describe(
		'Test du controlleur permettant la gestion des actions sur les articles',
		function() {

			beforeEach(function() {
				this.addMatchers({
					toEqualData : function(expected) {
						return angular.equals(this.actual, expected);
					}
				});
			});

			beforeEach(module('blogApp.services'));

			describe(
					'Liste des articles - Test du controlleur : ArticleListController',
					function() {

						var scope, controller, $httpBackend, location;

						var listArticlesData = function() {
							return [ {
								id : '1',
								titre : 'premier article'
							}, {
								id : '2',
								titre : 'deuxieme article'
							} ];
						};

						beforeEach(inject(function(_$httpBackend_, $rootScope,
								$location, $controller) {
							$httpBackend = _$httpBackend_;
							location = $location;
							scope = $rootScope.$new();
							controller = $controller;
						}));

						it('Test de la liste des articles', function() {

							$httpBackend.expectGET('rest/articles').respond(
									listArticlesData());
							controller(ArticleListController, {
								$scope : scope
							});
							$httpBackend.flush();
							expect(scope.articles).toEqualData(
									listArticlesData());

						});

					});

		});
