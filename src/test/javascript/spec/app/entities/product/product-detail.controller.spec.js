'use strict';

describe('Product Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProduct, MockLineitem;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProduct = jasmine.createSpy('MockProduct');
        MockLineitem = jasmine.createSpy('MockLineitem');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Product': MockProduct,
            'Lineitem': MockLineitem
        };
        createController = function() {
            $injector.get('$controller')("ProductDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'av001App:productUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
