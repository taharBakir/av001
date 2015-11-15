'use strict';

angular.module('av001App').controller('LineItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'LineItem', 'Commande', 'Product',
        function($scope, $stateParams, $modalInstance, entity, LineItem, Commande, Product) {

        $scope.lineItem = entity;
        $scope.commandes = Commande.query();
        $scope.products = Product.query();
        $scope.load = function(id) {
            LineItem.get({id : id}, function(result) {
                $scope.lineItem = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('av001App:lineItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.lineItem.id != null) {
                LineItem.update($scope.lineItem, onSaveFinished);
            } else {
                LineItem.save($scope.lineItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
