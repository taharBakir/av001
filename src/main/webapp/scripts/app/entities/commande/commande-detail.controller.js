'use strict';

angular.module('av001App')
    .controller('CommandeDetailController', function ($scope, $rootScope, $stateParams, entity, Commande, Client, Lineitem, ShippingAddress, InvoiceAddress) {
        $scope.commande = entity;
        $scope.load = function (id) {
            Commande.get({id: id}, function(result) {
                $scope.commande = result;
            });
        };
        var unsubscribe = $rootScope.$on('av001App:commandeUpdate', function(event, result) {
            $scope.commande = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
