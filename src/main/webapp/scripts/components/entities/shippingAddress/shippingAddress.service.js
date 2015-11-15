'use strict';

angular.module('av001App')
    .factory('ShippingAddress', function ($resource, DateUtils) {
        return $resource('api/shippingAddresss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
