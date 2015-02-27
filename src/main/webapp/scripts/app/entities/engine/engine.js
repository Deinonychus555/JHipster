'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('engine', {
                parent: 'entity',
                url: '/engine',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/engine/engines.html',
                        controller: 'EngineController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('engine');
                        return $translate.refresh();
                    }]
                }
            })
            .state('engineDetail', {
                parent: 'entity',
                url: '/engine/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/engine/engine-detail.html',
                        controller: 'EngineDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('engine');
                        return $translate.refresh();
                    }]
                }
            });
    });
