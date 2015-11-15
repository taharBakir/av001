'use strict';

angular.module('av001App')
    .controller('InvoiceAddressDetailController', function ($scope, $rootScope, $stateParams, entity, InvoiceAddress, Commande) {
        $scope.invoiceAddress = entity;
        $scope.load = function (id) {
            InvoiceAddress.get({id: id}, function(result) {
                $scope.invoiceAddress = result;
            });
        };
        var unsubscribe = $rootScope.$on('av001App:invoiceAddressUpdate', function(event, result) {
            $scope.invoiceAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
