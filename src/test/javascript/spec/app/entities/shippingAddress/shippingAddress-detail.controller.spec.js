'use strict';

describe('ShippingAddress Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockShippingAddress, MockCommande;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockShippingAddress = jasmine.createSpy('MockShippingAddress');
        MockCommande = jasmine.createSpy('MockCommande');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ShippingAddress': MockShippingAddress,
            'Commande': MockCommande
        };
        createController = function() {
            $injector.get('$controller')("ShippingAddressDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'av001App:shippingAddressUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
