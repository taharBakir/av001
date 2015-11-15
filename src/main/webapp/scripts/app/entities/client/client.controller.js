'use strict';

angular.module('av001App')
    .controller('ClientController', function ($scope, Client, ClientSearch) {
        $scope.clients = [];
        $scope.loadAll = function() {
            Client.query(function(result) {
               $scope.clients = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
                $('#deleteClientConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Client.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClientConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ClientSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.clients = result;
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
            $scope.client = {
                nom: null,
                prenom: null,
                dateNaissance: null,
                id: null
            };
        };
    });
