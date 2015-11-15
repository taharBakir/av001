'use strict';

angular.module('av001App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shippingAddress', {
                parent: 'entity',
                url: '/shippingAddresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'av001App.shippingAddress.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shippingAddress/shippingAddresss.html',
                        controller: 'ShippingAddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shippingAddress');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shippingAddress.detail', {
                parent: 'entity',
                url: '/shippingAddress/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'av001App.shippingAddress.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shippingAddress/shippingAddress-detail.html',
                        controller: 'ShippingAddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shippingAddress');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ShippingAddress', function($stateParams, ShippingAddress) {
                        return ShippingAddress.get({id : $stateParams.id});
                    }]
                }
            })
            .state('shippingAddress.new', {
                parent: 'shippingAddress',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/shippingAddress/shippingAddress-dialog.html',
                        controller: 'ShippingAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    address1: null,
                                    address2: null,
                                    ville: null,
                                    codePostal: null,
                                    pays: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('shippingAddress', null, { reload: true });
                    }, function() {
                        $state.go('shippingAddress');
                    })
                }]
            })
            .state('shippingAddress.edit', {
                parent: 'shippingAddress',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/shippingAddress/shippingAddress-dialog.html',
                        controller: 'ShippingAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ShippingAddress', function(ShippingAddress) {
                                return ShippingAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('shippingAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
