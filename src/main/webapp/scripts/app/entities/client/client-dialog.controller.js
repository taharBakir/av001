'use strict';

angular.module('av001App').controller('ClientDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Client', 'Commande', 'Address',
        function($scope, $stateParams, $modalInstance, entity, Client, Commande, Address) {

        $scope.client = entity;
        $scope.commandes = Commande.query();
        $scope.addresss = Address.query();
        $scope.load = function(id) {
            Client.get({id : id}, function(result) {
                $scope.client = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('av001App:clientUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveFinished);
            } else {
                Client.save($scope.client, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
