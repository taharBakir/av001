'use strict';

angular.module('av001App')
    .factory('LineItemSearch', function ($resource) {
        return $resource('api/_search/lineItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
