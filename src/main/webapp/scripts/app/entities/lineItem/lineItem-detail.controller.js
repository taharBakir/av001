'use strict';

angular.module('av001App')
    .controller('LineItemDetailController', function ($scope, $rootScope, $stateParams, entity, LineItem, Commande, Product) {
        $scope.lineItem = entity;
        $scope.load = function (id) {
            LineItem.get({id: id}, function(result) {
                $scope.lineItem = result;
            });
        };
        var unsubscribe = $rootScope.$on('av001App:lineItemUpdate', function(event, result) {
            $scope.lineItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
