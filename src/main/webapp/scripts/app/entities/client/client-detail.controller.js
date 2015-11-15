'use strict';

angular.module('av001App')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, entity, Client, Commande, Address) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        var unsubscribe = $rootScope.$on('av001App:clientUpdate', function(event, result) {
            $scope.client = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
