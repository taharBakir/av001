'use strict';

describe('InvoiceAddress Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInvoiceAddress, MockCommande;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInvoiceAddress = jasmine.createSpy('MockInvoiceAddress');
        MockCommande = jasmine.createSpy('MockCommande');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InvoiceAddress': MockInvoiceAddress,
            'Commande': MockCommande
        };
        createController = function() {
            $injector.get('$controller')("InvoiceAddressDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'av001App:invoiceAddressUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
