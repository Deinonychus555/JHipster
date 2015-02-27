'use strict';

angular.module('jhipsterApp')
    .controller('PersonDetailController', function ($scope, $stateParams, Person, Animal) {
        $scope.person = {};
        $scope.load = function (id) {
            Person.get({id: id}, function(result) {
              $scope.person = result;
            });
        };
        $scope.load($stateParams.id);
    });
