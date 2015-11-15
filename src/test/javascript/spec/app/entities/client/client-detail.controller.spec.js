'use strict';

describe('Client Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockClient, MockCommande, MockAddress;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockClient = jasmine.createSpy('MockClient');
        MockCommande = jasmine.createSpy('MockCommande');
        MockAddress = jasmine.createSpy('MockAddress');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Client': MockClient,
            'Commande': MockCommande,
            'Address': MockAddress
        };
        createController = function() {
            $injector.get('$controller')("ClientDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'av001App:clientUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
