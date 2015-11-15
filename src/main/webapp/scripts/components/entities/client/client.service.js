'use strict';

angular.module('av001App')
    .factory('Client', function ($resource, DateUtils) {
        return $resource('api/clients/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateNaissance = DateUtils.convertDateTimeFromServer(data.dateNaissance);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
