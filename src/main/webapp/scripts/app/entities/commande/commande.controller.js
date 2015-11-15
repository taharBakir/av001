'use strict';

angular.module('av001App')
    .controller('CommandeController', function ($scope, Commande, CommandeSearch, ParseLinks) {
        $scope.commandes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Commande.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.commandes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Commande.get({id: id}, function(result) {
                $scope.commande = result;
                $('#deleteCommandeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Commande.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCommandeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            CommandeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.commandes = result;
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
            $scope.commande = {
                date: null,
                id: null
            };
        };
    });
