'use strict';

angular.module('av001App')
    .controller('AddressDetailController', function ($scope, $rootScope, $stateParams, entity, Address, Client) {
        $scope.address = entity;
        $scope.load = function (id) {
            Address.get({id: id}, function(result) {
                $scope.address = result;
            });
        };
        var unsubscribe = $rootScope.$on('av001App:addressUpdate', function(event, result) {
            $scope.address = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
