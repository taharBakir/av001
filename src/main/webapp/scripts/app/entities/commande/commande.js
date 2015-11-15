'use strict';

angular.module('av001App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('commande', {
                parent: 'entity',
                url: '/commandes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'av001App.commande.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/commande/commandes.html',
                        controller: 'CommandeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('commande');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('commande.detail', {
                parent: 'entity',
                url: '/commande/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'av001App.commande.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/commande/commande-detail.html',
                        controller: 'CommandeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('commande');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Commande', function($stateParams, Commande) {
                        return Commande.get({id : $stateParams.id});
                    }]
                }
            })
            .state('commande.new', {
                parent: 'commande',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/commande/commande-dialog.html',
                        controller: 'CommandeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('commande', null, { reload: true });
                    }, function() {
                        $state.go('commande');
                    })
                }]
            })
            .state('commande.edit', {
                parent: 'commande',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/commande/commande-dialog.html',
                        controller: 'CommandeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Commande', function(Commande) {
                                return Commande.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('commande', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
