/* global beforeEach */
/* global inject */
/* global describe */
/* global expect */
/* global it */
/* global spyOn */
/* global jasmine */
/* global angular */

import UserPhotoModule from './user-photo';
import UserPhotoController from './user-photo.controller';
import UserPhotoComponent from './user-photo.component';
import UserPhotoService from './user-photo.service';


describe('UserPhoto', () => {
  describe('Controller', () => {
    let q;
    let userPhotoService;
    const user = { photo: 'df' };
    let sut;

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register('UserPhotoController', UserPhotoController);
    }));

    beforeEach(angular.mock.inject(($injector, $controller) => {
      q = $injector.get('$q');
      userPhotoService = jasmine.createSpyObj('userPhotoService', ['uploadPhoto', 'deleteUserPhoto']);
      userPhotoService.uploadPhoto.and.returnValue(q.when([]));
      userPhotoService.deleteUserPhoto.and.returnValue(q.when([]));

      sut = $controller('UserPhotoController', { userPhotoService }, { user });
    }));

    it('should toggle preview', () => {
      const preview = sut.uploadPreview;
      sut.uploadForm.$valid = true;
      sut.uploadForm.$setValidity = () => {};

      sut.togglePreview();
      expect(sut.uploadPreview).toEqual(!preview);
    });

    it('shouldn`t toggle preview', () => {
      const preview = sut.uploadPreview;
      sut.uploadForm.$valid = false;
      sut.uploadForm.$setValidity = () => {};

      sut.togglePreview();
      expect(sut.uploadPreview).toEqual(preview);
    });

    it('should call functions in successUpload', () => {
      spyOn(sut, 'togglePreview');
      spyOn(sut, 'toggleSlide');
      spyOn(sut, 'toggleAnimation');

      sut.successUpload({ data: { answer: 'link' } });
      expect(sut.togglePreview).toHaveBeenCalled();
      expect(sut.toggleSlide).toHaveBeenCalled();
      expect(sut.toggleAnimation).toHaveBeenCalled();
    });

    it('should call functions in errorUpload', () => {
      sut.uploadForm.$setValidity = () => {};

      spyOn(sut, 'togglePreview');
      spyOn(sut, 'toggleAnimation');
      spyOn(sut.uploadForm, '$setValidity');
      sut.errorUpload({ data: { error: 'maxSize' } });

      expect(sut.togglePreview).toHaveBeenCalled();
      expect(sut.toggleAnimation).toHaveBeenCalled();
      expect(sut.uploadForm.$setValidity).toHaveBeenCalled();
    });
  });
});