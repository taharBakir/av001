'use strict';

angular.module('av001App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('product', {
                parent: 'entity',
                url: '/products',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'av001App.product.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/products.html',
                        controller: 'ProductController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('product');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('product.detail', {
                parent: 'entity',
                url: '/product/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'av001App.product.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/product-detail.html',
                        controller: 'ProductDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('product');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Product', function($stateParams, Product) {
                        return Product.get({id : $stateParams.id});
                    }]
                }
            })
            .state('product.new', {
                parent: 'product',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/product/product-dialog.html',
                        controller: 'ProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    prixHT: null,
                                    prixTTC: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('product');
                    })
                }]
            })
            .state('product.edit', {
                parent: 'product',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/product/product-dialog.html',
                        controller: 'ProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Product', function(Product) {
                                return Product.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
