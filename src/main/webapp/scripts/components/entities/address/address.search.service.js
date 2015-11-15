'use strict';

angular.module('av001App')
    .factory('AddressSearch', function ($resource) {
        return $resource('api/_search/addresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
