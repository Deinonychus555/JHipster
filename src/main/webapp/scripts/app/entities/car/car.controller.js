'use strict';

angular.module('jhipsterApp')
    .controller('CarController', function ($scope, Car, Engine) {
        $scope.cars = [];
        $scope.engines = Engine.query();
        $scope.loadAll = function() {
            Car.query(function(result) {
               $scope.cars = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Car.save($scope.car,
                function () {
                    $scope.loadAll();
                    $('#saveCarModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Car.get({id: id}, function(result) {
                $scope.car = result;
                $('#saveCarModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Car.get({id: id}, function(result) {
                $scope.car = result;
                $('#deleteCarConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Car.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCarConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.car = {model: null, numberPlate: null, id: null};
        };
    });
