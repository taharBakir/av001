'use strict';

describe('Commande Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCommande, MockClient, MockLineitem, MockShippingAddress, MockInvoiceAddress;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCommande = jasmine.createSpy('MockCommande');
        MockClient = jasmine.createSpy('MockClient');
        MockLineitem = jasmine.createSpy('MockLineitem');
        MockShippingAddress = jasmine.createSpy('MockShippingAddress');
        MockInvoiceAddress = jasmine.createSpy('MockInvoiceAddress');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Commande': MockCommande,
            'Client': MockClient,
            'Lineitem': MockLineitem,
            'ShippingAddress': MockShippingAddress,
            'InvoiceAddress': MockInvoiceAddress
        };
        createController = function() {
            $injector.get('$controller')("CommandeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'av001App:commandeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
