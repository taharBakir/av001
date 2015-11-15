'use strict';

angular.module('av001App')
    .factory('InvoiceAddressSearch', function ($resource) {
        return $resource('api/_search/invoiceAddresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
