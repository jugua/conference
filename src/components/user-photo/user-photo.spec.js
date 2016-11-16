/* global beforeEach */
/* global inject */
/* global describe */
/* global expect */
/* global it */
/* global spyOn */

import UserPhotoModule from './user-photo';
import UserPhotoController from './user-photo.controller';
import UserPhotoComponent from './user-photo.component';
import UserPhotoService from './user-photo.service';


xdescribe('UserPhoto', () => {

  describe('Controller', () => {
    let q;
    let deferred;
    let serviceMock;
    let sut;
    let elm;
    let scope;

    beforeEach(inject(($rootScope, $compile) => {
      scope = $rootScope.$new();
      scope.userMock = { photo: '' };
      elm = $compile('<userMock user="userMock"></userMock>')(scope);
      scope.$digest();
    }));

    beforeEach(() => {
      serviceMock = {
        uploadPhoto: () => {},
        deletePhoto: () => {}
      };

      sut = new UserPhotoController(serviceMock);
    });

    it('should toggle preview', () => {
      console.log(sut);
      console.log(UserPhotoController);
      let preview = sut.uploadPreview;
      sut.uploadForm.$valid = true;
      sut.uploadForm.$setValidity = () => {};
      sut.togglePreview();
      expect(sut.uploadPreview).toEqual(!preview);
    });

    it('shouldn`t toggle preview', () => {
     let preview = sut.uploadPreview;
     sut.uploadForm.$valid = false;
     sut.uploadForm.$setValidity = () => {};

     sut.togglePreview();
     expect(sut.uploadPreview).toEqual(preview);
    });

    it('should call functions in successUpload', () => {
      spyOn(sut, 'togglePreview');
      spyOn(sut, 'toggleSlide');
      spyOn(sut, 'toggleAnimation');

      sut.successUpload({data:{answer:'link'}});
      expect(sut.togglePreview).toHaveBeenCalled();
      expect(sut.toggleSlide).toHaveBeenCalled();
      expect(sut.toggleAnimation).toHaveBeenCalled();
    });

    it('should call functions in errorUpload', () => {
     let file = {};
     sut.uploadForm.$setValidity = () => {};

     spyOn(sut, 'togglePreview');
     spyOn(sut, 'toggleAnimation');
     spyOn(sut.uploadForm, '$setValidity');
     sut.errorUpload({data:{error: 'maxSize'}});

     expect(sut.togglePreview).toHaveBeenCalled();
     expect(sut.toggleAnimation).toHaveBeenCalled();
     expect(sut.uploadForm.$setValidity).toHaveBeenCalled();
     expect(sut.file).toEqual(file);
    });
  });
});