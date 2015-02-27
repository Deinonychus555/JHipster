'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('animal', {
                parent: 'entity',
                url: '/animal',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/animal/animals.html',
                        controller: 'AnimalController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('animal');
                        return $translate.refresh();
                    }]
                }
            })
            .state('animalDetail', {
                parent: 'entity',
                url: '/animal/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/animal/animal-detail.html',
                        controller: 'AnimalDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('animal');
                        return $translate.refresh();
                    }]
                }
            });
    });
