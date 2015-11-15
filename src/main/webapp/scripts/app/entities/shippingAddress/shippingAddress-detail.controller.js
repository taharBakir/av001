'use strict';

angular.module('av001App')
    .controller('ShippingAddressDetailController', function ($scope, $rootScope, $stateParams, entity, ShippingAddress, Commande) {
        $scope.shippingAddress = entity;
        $scope.load = function (id) {
            ShippingAddress.get({id: id}, function(result) {
                $scope.shippingAddress = result;
            });
        };
        var unsubscribe = $rootScope.$on('av001App:shippingAddressUpdate', function(event, result) {
            $scope.shippingAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
