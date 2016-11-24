/* global inject */
/* global describe */
/* global xdescribe */
/* global expect */
/* global it */
/* global spyOn */
/* global jasmine */
/* global angular */
/* global beforeEach */

import EditPasswordController from './edit-password.controller';

xdescribe('EditPassword', () => {
  describe('Controller', () => {
    let q;
    let EditPasswordService;
    // const user = { photo: 'df' };
    let sut;

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register('EditPasswordController', EditPasswordController);
    }));

    beforeEach(angular.mock.inject(($injector, $controller) => {
      q = $injector.get('$q');
      EditPasswordService = jasmine.createSpyObj('EditPasswordService', ['changePassword']);
      EditPasswordService.changePassword.and.returnValue(q.when([]));

      // sut = $controller('EditPasswordController', { EditPasswordService }, { user });
    }));
  });
});