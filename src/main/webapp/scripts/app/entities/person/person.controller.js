'use strict';

angular.module('jhipsterApp')
    .controller('PersonController', function ($scope, Person, Animal) {
        $scope.persons = [];
        $scope.animals = Animal.query();
        $scope.loadAll = function() {
            Person.query(function(result) {
               $scope.persons = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Person.save($scope.person,
                function () {
                    $scope.loadAll();
                    $('#savePersonModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Person.get({id: id}, function(result) {
                $scope.person = result;
                $('#savePersonModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Person.get({id: id}, function(result) {
                $scope.person = result;
                $('#deletePersonConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Person.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePersonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.person = {firstName: null, lastName: null, age: null, id: null};
        };
    });
