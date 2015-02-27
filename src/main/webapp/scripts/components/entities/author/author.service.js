'use strict';

angular.module('jhipsterApp')
    .factory('Author', function ($resource) {
        return $resource('api/authors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birthday = new Date(data.birthday);
                    return data;
                }
            }
        });
    });
