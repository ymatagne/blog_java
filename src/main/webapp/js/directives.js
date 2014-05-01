'use strict';

/* Directives */

angular.module('blogApp.directives', []).directive('footer',function () {
    return {
        templateUrl: "partials/directives/footer.html"
    };
}).directive('edito',function () {
    return {
        templateUrl: "partials/directives/edito.html",
        controller: EditoCtrl
    };
}).directive('menu',function () {
    return {
        templateUrl: "partials/directives/menu.html",
        controller: MenuCtrl
    };
}).directive('authentification',function () {
    return {
        templateUrl: "partials/directives/login.html",
        controller: AuteurControlleur
    };
}).directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });
                event.preventDefault();
            }
        });
    };
}).directive('twitter', [
        function() {
            return {
                link: function(scope, element, attr) {
                    setTimeout(function() {
                        twttr.widgets.createShareButton(
                            attr.url,
                            element[0],
                            function(el) {}, {
                                count: 'horizontal',
                                text: attr.text
                            }
                        );
                    });
                }
            }
        }
    ]);
