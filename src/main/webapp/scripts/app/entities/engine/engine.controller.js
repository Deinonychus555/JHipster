'use strict';

angular.module('jhipsterApp')
    .controller('EngineController', function ($scope, Engine, Car) {
        $scope.engines = [];
        $scope.cars = Car.query();
        $scope.loadAll = function() {
            Engine.query(function(result) {
               $scope.engines = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Engine.save($scope.engine,
                function () {
                    $scope.loadAll();
                    $('#saveEngineModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Engine.get({id: id}, function(result) {
                $scope.engine = result;
                $('#saveEngineModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Engine.get({id: id}, function(result) {
                $scope.engine = result;
                $('#deleteEngineConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Engine.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEngineConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.engine = {model: null, power: null, id: null};
        };
    });
