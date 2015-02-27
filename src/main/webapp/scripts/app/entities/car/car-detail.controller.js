'use strict';

angular.module('jhipsterApp')
    .controller('CarDetailController', function ($scope, $stateParams, Car, Engine) {
        $scope.car = {};
        $scope.load = function (id) {
            Car.get({id: id}, function(result) {
              $scope.car = result;
            });
        };
        $scope.load($stateParams.id);
    });
