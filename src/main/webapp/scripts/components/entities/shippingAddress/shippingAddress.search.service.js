'use strict';

angular.module('av001App')
    .factory('ShippingAddressSearch', function ($resource) {
        return $resource('api/_search/shippingAddresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
