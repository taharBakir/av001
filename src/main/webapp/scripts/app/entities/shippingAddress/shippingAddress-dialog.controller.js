'use strict';

angular.module('av001App').controller('ShippingAddressDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ShippingAddress', 'Commande',
        function($scope, $stateParams, $modalInstance, entity, ShippingAddress, Commande) {

        $scope.shippingAddress = entity;
        $scope.commandes = Commande.query();
        $scope.load = function(id) {
            ShippingAddress.get({id : id}, function(result) {
                $scope.shippingAddress = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('av001App:shippingAddressUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.shippingAddress.id != null) {
                ShippingAddress.update($scope.shippingAddress, onSaveFinished);
            } else {
                ShippingAddress.save($scope.shippingAddress, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
