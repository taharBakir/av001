'use strict';

angular.module('av001App')
    .controller('AddressController', function ($scope, Address, AddressSearch) {
        $scope.addresss = [];
        $scope.loadAll = function() {
            Address.query(function(result) {
               $scope.addresss = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Address.get({id: id}, function(result) {
                $scope.address = result;
                $('#deleteAddressConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Address.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAddressConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.addresss = result;
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
            $scope.address = {
                address1: null,
                address2: null,
                ville: null,
                codePostal: null,
                pays: null,
                id: null
            };
        };
    });
