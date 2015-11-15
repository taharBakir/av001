'use strict';

angular.module('av001App')
    .controller('ProductDetailController', function ($scope, $rootScope, $stateParams, entity, Product, Lineitem) {
        $scope.product = entity;
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
            });
        };
        var unsubscribe = $rootScope.$on('av001App:productUpdate', function(event, result) {
            $scope.product = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
