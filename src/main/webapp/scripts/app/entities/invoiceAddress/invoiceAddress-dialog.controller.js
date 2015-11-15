'use strict';

angular.module('av001App').controller('InvoiceAddressDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InvoiceAddress', 'Commande',
        function($scope, $stateParams, $modalInstance, entity, InvoiceAddress, Commande) {

        $scope.invoiceAddress = entity;
        $scope.commandes = Commande.query();
        $scope.load = function(id) {
            InvoiceAddress.get({id : id}, function(result) {
                $scope.invoiceAddress = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('av001App:invoiceAddressUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.invoiceAddress.id != null) {
                InvoiceAddress.update($scope.invoiceAddress, onSaveFinished);
            } else {
                InvoiceAddress.save($scope.invoiceAddress, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
