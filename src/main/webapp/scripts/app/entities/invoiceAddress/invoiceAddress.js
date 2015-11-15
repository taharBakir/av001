'use strict';

angular.module('av001App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('invoiceAddress', {
                parent: 'entity',
                url: '/invoiceAddresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'av001App.invoiceAddress.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceAddress/invoiceAddresss.html',
                        controller: 'InvoiceAddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoiceAddress');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('invoiceAddress.detail', {
                parent: 'entity',
                url: '/invoiceAddress/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'av001App.invoiceAddress.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceAddress/invoiceAddress-detail.html',
                        controller: 'InvoiceAddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoiceAddress');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InvoiceAddress', function($stateParams, InvoiceAddress) {
                        return InvoiceAddress.get({id : $stateParams.id});
                    }]
                }
            })
            .state('invoiceAddress.new', {
                parent: 'invoiceAddress',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/invoiceAddress/invoiceAddress-dialog.html',
                        controller: 'InvoiceAddressDialogController',
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
                        $state.go('invoiceAddress', null, { reload: true });
                    }, function() {
                        $state.go('invoiceAddress');
                    })
                }]
            })
            .state('invoiceAddress.edit', {
                parent: 'invoiceAddress',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/invoiceAddress/invoiceAddress-dialog.html',
                        controller: 'InvoiceAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InvoiceAddress', function(InvoiceAddress) {
                                return InvoiceAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('invoiceAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
