'use strict';

angular.module('av001App')
    .factory('CommandeSearch', function ($resource) {
        return $resource('api/_search/commandes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
