'use strict';

angular.module('av001App')
    .factory('Address', function ($resource, DateUtils) {
        return $resource('api/addresss/:id', {}, {
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
