'use strict';

angular.module('av001App').controller('CommandeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Commande', 'Client', 'Lineitem', 'ShippingAddress', 'InvoiceAddress',
        function($scope, $stateParams, $modalInstance, entity, Commande, Client, Lineitem, ShippingAddress, InvoiceAddress) {

        $scope.commande = entity;
        $scope.clients = Client.query();
        $scope.lineitems = Lineitem.query();
        $scope.shippingaddresss = ShippingAddress.query();
        $scope.invoiceaddresss = InvoiceAddress.query();
        $scope.load = function(id) {
            Commande.get({id : id}, function(result) {
                $scope.commande = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('av001App:commandeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.commande.id != null) {
                Commande.update($scope.commande, onSaveFinished);
            } else {
                Commande.save($scope.commande, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
