'use strict';

angular.module('av001App').controller('AddressDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Address', 'Client',
        function($scope, $stateParams, $modalInstance, entity, Address, Client) {

        $scope.address = entity;
        $scope.clients = Client.query();
        $scope.load = function(id) {
            Address.get({id : id}, function(result) {
                $scope.address = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('av001App:addressUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.address.id != null) {
                Address.update($scope.address, onSaveFinished);
            } else {
                Address.save($scope.address, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
