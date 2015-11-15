'use strict';

angular.module('av001App')
    .controller('LineItemController', function ($scope, LineItem, LineItemSearch) {
        $scope.lineItems = [];
        $scope.loadAll = function() {
            LineItem.query(function(result) {
               $scope.lineItems = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            LineItem.get({id: id}, function(result) {
                $scope.lineItem = result;
                $('#deleteLineItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            LineItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteLineItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            LineItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lineItems = result;
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
            $scope.lineItem = {
                productName: null,
                id: null
            };
        };
    });
