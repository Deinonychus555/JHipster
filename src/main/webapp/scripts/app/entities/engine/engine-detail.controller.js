'use strict';

angular.module('jhipsterApp')
    .controller('EngineDetailController', function ($scope, $stateParams, Engine, Car) {
        $scope.engine = {};
        $scope.load = function (id) {
            Engine.get({id: id}, function(result) {
              $scope.engine = result;
            });
        };
        $scope.load($stateParams.id);
    });
