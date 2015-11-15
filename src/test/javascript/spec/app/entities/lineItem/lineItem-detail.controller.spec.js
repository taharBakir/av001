'use strict';

describe('LineItem Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockLineItem, MockCommande, MockProduct;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockLineItem = jasmine.createSpy('MockLineItem');
        MockCommande = jasmine.createSpy('MockCommande');
        MockProduct = jasmine.createSpy('MockProduct');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'LineItem': MockLineItem,
            'Commande': MockCommande,
            'Product': MockProduct
        };
        createController = function() {
            $injector.get('$controller')("LineItemDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'av001App:lineItemUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
