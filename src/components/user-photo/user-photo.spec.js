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


describe('UserPhoto', () => {
  let q;
  let deferred;
  let serviceMock;
  let sut;

  beforeEach(inject(($q) => {
    q = $q;
    deferred = $q.defer();
  }));

  beforeEach(() => {
    serviceMock = {
      uploadPhoto: () => {},
      deletePhoto: () => {}
    };
  });

  beforeEach(() => {
    sut = new UserPhotoController(serviceMock);
    sut.user = {};
    sut.user.photo = '';
  });

  describe('Controller', () => {
    it('should toggle preview', () => {
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
  })
});
