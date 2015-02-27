'use strict';

angular.module('jhipsterApp')
    .controller('AnimalController', function ($scope, Animal, Person) {
        $scope.animals = [];
        $scope.persons = Person.query();
        $scope.loadAll = function() {
            Animal.query(function(result) {
               $scope.animals = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Animal.save($scope.animal,
                function () {
                    $scope.loadAll();
                    $('#saveAnimalModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Animal.get({id: id}, function(result) {
                $scope.animal = result;
                $('#saveAnimalModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Animal.get({id: id}, function(result) {
                $scope.animal = result;
                $('#deleteAnimalConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Animal.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAnimalConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.animal = {name: null, breed: null, age: null, id: null};
        };
    });
