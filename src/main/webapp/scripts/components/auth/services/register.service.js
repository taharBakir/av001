'use strict';

angular.module('av001App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


