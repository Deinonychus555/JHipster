'use strict';

angular.module('jhipsterApp')
    .controller('AnimalDetailController', function ($scope, $stateParams, Animal, Person) {
        $scope.animal = {};
        $scope.load = function (id) {
            Animal.get({id: id}, function(result) {
              $scope.animal = result;
            });
        };
        $scope.load($stateParams.id);
    });
