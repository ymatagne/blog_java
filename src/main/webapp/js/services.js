'use strict';
angular.module('blogApp.services', ['ngResource']).
        factory('Article', function ($resource) {
            return $resource('rest/article/:id', {}, {
                'save': {method:'PUT'}
            });
        });