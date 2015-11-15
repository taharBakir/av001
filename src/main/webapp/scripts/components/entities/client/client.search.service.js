'use strict';

angular.module('av001App')
    .factory('ClientSearch', function ($resource) {
        return $resource('api/_search/clients/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
