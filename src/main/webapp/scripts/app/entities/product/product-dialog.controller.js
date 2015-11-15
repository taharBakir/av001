'use strict';

angular.module('av001App').controller('ProductDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Product', 'Lineitem',
        function($scope, $stateParams, $modalInstance, entity, Product, Lineitem) {

        $scope.product = entity;
        $scope.lineitems = Lineitem.query();
        $scope.load = function(id) {
            Product.get({id : id}, function(result) {
                $scope.product = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('av001App:productUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.product.id != null) {
                Product.update($scope.product, onSaveFinished);
            } else {
                Product.save($scope.product, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
