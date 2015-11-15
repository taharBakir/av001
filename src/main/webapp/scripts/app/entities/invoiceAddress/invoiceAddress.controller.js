'use strict';

angular.module('av001App')
    .controller('InvoiceAddressController', function ($scope, InvoiceAddress, InvoiceAddressSearch) {
        $scope.invoiceAddresss = [];
        $scope.loadAll = function() {
            InvoiceAddress.query(function(result) {
               $scope.invoiceAddresss = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InvoiceAddress.get({id: id}, function(result) {
                $scope.invoiceAddress = result;
                $('#deleteInvoiceAddressConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InvoiceAddress.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInvoiceAddressConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InvoiceAddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.invoiceAddresss = result;
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
            $scope.invoiceAddress = {
                address1: null,
                address2: null,
                ville: null,
                codePostal: null,
                pays: null,
                id: null
            };
        };
    });
