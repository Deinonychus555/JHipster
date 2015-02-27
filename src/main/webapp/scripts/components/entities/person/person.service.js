'use strict';

angular.module('jhipsterApp')
    .factory('Person', function ($resource) {
        return $resource('api/people/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
