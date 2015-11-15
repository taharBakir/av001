'use strict';

angular.module('av001App')
    .controller('ShippingAddressController', function ($scope, ShippingAddress, ShippingAddressSearch) {
        $scope.shippingAddresss = [];
        $scope.loadAll = function() {
            ShippingAddress.query(function(result) {
               $scope.shippingAddresss = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ShippingAddress.get({id: id}, function(result) {
                $scope.shippingAddress = result;
                $('#deleteShippingAddressConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ShippingAddress.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShippingAddressConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ShippingAddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.shippingAddresss = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.shippingAddress = {
                address1: null,
                address2: null,
                ville: null,
                codePostal: null,
                pays: null,
                id: null
            };
        };
    });
