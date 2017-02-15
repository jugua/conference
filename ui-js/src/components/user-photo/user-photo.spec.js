import UserPhotoModule from './user-photo';
import UserPhotoController from './user-photo.controller';
import UserPhotoComponent from './user-photo.component';
import UserPhotoService from './user-photo.service';


describe('UserPhoto', () => {
  describe('Controller', () => {
    let q;
    let userPhotoService;
    let Constants;
    const user = { photo: '' };
    let sut;

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register('UserPhotoController', UserPhotoController);
    }));

    beforeEach(angular.mock.inject(($injector, $controller) => {
      q = $injector.get('$q');
      userPhotoService = jasmine.createSpyObj('userPhotoService', ['uploadPhoto', 'deleteUserPhoto']);
      userPhotoService.uploadPhoto.and.returnValue(q.when([]));
      userPhotoService.deleteUserPhoto.and.returnValue(q.when([]));

      Constants = jasmine.createSpyObj('constants', ['ava']);

      sut = $controller('UserPhotoController', { userPhotoService, Constants }, { user });
    }));

    beforeEach(() => {
      sut.uploadForm = jasmine.createSpyObj('uploadForm', ['$setValidity', 'file', 'dragfile']);
      sut.uploadForm.file = { $setValidity: () => {} };
      sut.uploadForm.dragfile = { $setValidity: () => {} };
    });

    describe('Controller has all elements', () => {
      it('uploadPreview to equal false', () => {
        expect(sut.uploadPreview).toBeFalsy();
      });
      it('deletePreview to equal false', () => {
        expect(sut.deletePreview).toBeFalsy();
      });
      it('deleteMessage to equal false', () => {
        expect(sut.deleteMessage).toBeFalsy();
      });
      it('animation to equal false', () => {
        expect(sut.animation).toBeFalsy();
      });
      it('ava to equal null', () => {
        expect(sut.ava).toBeNull();
      });
      it('file to equal null', () => {
        expect(sut.file).toBeNull();
      });
      it('uploadForm to be defined', () => {
        expect(sut.uploadForm).toBeDefined();
      });
      it('currentPhotoStatus to be defined', () => {
        expect(sut.currentPhotoStatus).toBeDefined();
      });
      it('has function toggleSlide', () => {
        expect(typeof sut.toggleSlide).toBe('function');
      });
      it('has function toggleSlide', () => {
        expect(typeof sut.toggleSlide).toBe('function');
      });
      it('has function downloadByDrag', () => {
        expect(typeof sut.downloadByDrag).toBe('function');
      });
      it('has function downloadBySelect', () => {
        expect(typeof sut.downloadBySelect).toBe('function');
      });
      it('has function toggleAnimation', () => {
        expect(typeof sut.toggleAnimation).toBe('function');
      });
      it('has function successUpload', () => {
        expect(typeof sut.successUpload).toBe('function');
      });
      it('has function errorUpload', () => {
        expect(typeof sut.errorUpload).toBe('function');
      });
      it('has function uploadAva', () => {
        expect(typeof sut.uploadAva).toBe('function');
      });
      it('has function getCurrentPhotoStatus', () => {
        expect(typeof sut.getCurrentPhotoStatus).toBe('function');
      });
      it('has function toggleDeletePreview', () => {
        expect(typeof sut.toggleDeletePreview).toBe('function');
      });
      it('has function askToDeletePhoto', () => {
        expect(typeof sut.askToDeletePhoto).toBe('function');
      });
      it('has function deletePhoto', () => {
        expect(typeof sut.deletePhoto).toBe('function');
      });
      it('has function successDelete', () => {
        expect(typeof sut.deletePhoto).toBe('function');
      });
      it('has function errorDelete', () => {
        expect(typeof sut.deletePhoto).toBe('function');
      });
    });

    describe('toggle slide function', () => {
      let preview;
      beforeEach(() => {
        preview = sut.showLoad;
        sut.toggleSlide();
      });

      it('should toggle slide', () => {
        expect(sut.showLoad).toEqual(!preview);
      });
    });
    describe('downloadByDrag function', () => {
      beforeEach(() => {
        spyOn(sut, 'togglePreview');
        spyOn(sut.uploadForm.file, '$setValidity');
        sut.downloadByDrag();
      });

      it('should call togglePreview', () => {
        expect(sut.togglePreview).toHaveBeenCalled();
      });
      it('should call $setValidity for file', () => {
        expect(sut.uploadForm.file.$setValidity).toHaveBeenCalled();
      });
      it('should call $setValidity for file with maxSize true', () => {
        expect(sut.uploadForm.file.$setValidity).toHaveBeenCalledWith('maxSize', true);
      });
      it('should call $setValidity for file with minSize true', () => {
        expect(sut.uploadForm.file.$setValidity).toHaveBeenCalledWith('minSize', true);
      });
      it('should call $setValidity for file with pattern true', () => {
        expect(sut.uploadForm.file.$setValidity).toHaveBeenCalledWith('pattern', true);
      });
    });

    describe('downloadBySelect function', () => {
      beforeEach(() => {
        spyOn(sut, 'togglePreview');
        spyOn(sut.uploadForm.dragfile, '$setValidity');
        sut.downloadBySelect();
      });

      it('should call togglePreview', () => {
        expect(sut.togglePreview).toHaveBeenCalled();
      });
      it('should call $setValidity for file', () => {
        expect(sut.uploadForm.dragfile.$setValidity).toHaveBeenCalled();
      });
      it('should call $setValidity for file with maxSize true', () => {
        expect(sut.uploadForm.dragfile.$setValidity).toHaveBeenCalledWith('maxSize', true);
      });
      it('should call $setValidity for file with minSize true', () => {
        expect(sut.uploadForm.dragfile.$setValidity).toHaveBeenCalledWith('minSize', true);
      });
      it('should call $setValidity for file with pattern true', () => {
        expect(sut.uploadForm.dragfile.$setValidity).toHaveBeenCalledWith('pattern', true);
      });
    });

    describe('toggle preview function', () => {
      let preview;
      beforeEach(() => {
        preview = sut.animation;
        sut.toggleAnimation();
      });

      it('should toggle slide', () => {
        expect(sut.animation).toEqual(!preview);
      });
    });

    describe('toggle preview function', () => {
      let preview;
      beforeEach(() => {
        preview = sut.uploadPreview;
      });

      it('should toggle preview', () => {
        sut.uploadForm.$valid = true;
        sut.togglePreview();
        expect(sut.uploadPreview).toEqual(!preview);
      });
      it('shouldn`t toggle preview', () => {
        sut.uploadForm.$valid = false;
        sut.togglePreview();
        expect(sut.uploadPreview).toEqual(preview);
      });
      it('should call $setValidity when $valid true', () => {
        sut.uploadForm.$valid = true;
        sut.togglePreview();
        expect(sut.uploadForm.$setValidity).toHaveBeenCalled();
      });
      it('should call $setValidity when $valid false', () => {
        sut.uploadForm.$valid = false;
        sut.togglePreview();
        expect(sut.uploadForm.$setValidity).toHaveBeenCalled();
      });
    });

    describe('successUpload function', () => {
      beforeEach(() => {
        spyOn(sut, 'togglePreview');
        spyOn(sut, 'toggleSlide');
        spyOn(sut, 'toggleAnimation');
        spyOn(sut, 'getCurrentPhotoStatus');
        sut.file = 'file';
        sut.successUpload({ data: { result: 'link' } });
      });

      it('should call function togglePreview', () => {
        expect(sut.togglePreview).toHaveBeenCalled();
      });
      it('should call function toggleSlide', () => {
        expect(sut.toggleSlide).toHaveBeenCalled();
      });
      it('should call function toggleAnimation', () => {
        expect(sut.toggleAnimation).toHaveBeenCalled();
      });
      it('should call function getCurrentPhotoStatus', () => {
        expect(sut.getCurrentPhotoStatus).toHaveBeenCalled();
      });
      it('should change user.photo link', () => {
        expect(sut.user.photo).toEqual('link');
      });
      it('should ava change to file', () => {
        expect(sut.ava).toEqual(sut.file);
      });
    });

    describe('errorUpload function', () => {
      beforeEach(() => {
        spyOn(sut, 'togglePreview');
        spyOn(sut, 'toggleAnimation');
        sut.uploadForm = { file: { $setValidity: () => {} } };
        spyOn(sut.uploadForm.file, '$setValidity');
        sut.errorUpload({ data: { error: 'maxSize' } });
      });

      it('should call function togglePreview', () => {
        expect(sut.togglePreview).toHaveBeenCalled();
      });
      it('should call function toggleAnimation', () => {
        expect(sut.toggleAnimation).toHaveBeenCalled();
      });
      it('should call function $setValidity', () => {
        expect(sut.uploadForm.file.$setValidity).toHaveBeenCalled();
      });
      it('should file set to null', () => {
        expect(sut.file).toBeNull();
      });
    });

    describe('uploadAva function', () => {
      beforeEach(() => {
        spyOn(sut, 'toggleAnimation');
        sut.uploadAva();
      });

      it('should call function toggleAnimation', () => {
        expect(sut.toggleAnimation).toHaveBeenCalled();
      });
      it('should call function upload from service', () => {
        expect(sut.userPhotoService.uploadPhoto).toHaveBeenCalled();
      });
    });

    describe('call function on upload photo depended on server answer', () => {
      beforeEach(() => {
        spyOn(sut, 'successUpload');
        spyOn(sut, 'errorUpload');
      });

      it('should call function successUpload', () => {
        sut.userPhotoService.uploadPhoto().then(() => {
          expect(sut.successUpload).toHaveBeenCalled();
        });
      });
      it('shouldn`t call function errorUpload', () => {
        sut.userPhotoService.uploadPhoto().then(() => {
          expect(sut.errorUpload).not.toHaveBeenCalled();
        });
      });
      it('should call function errorUpload', () => {
        sut.userPhotoService.uploadPhoto.and.returnValue(q.when(false));
        sut.userPhotoService.uploadPhoto().then(() => {
          expect(sut.errorUpload).toHaveBeenCalled();
        });
      });
      it('shouldn`t call function successUpload', () => {
        sut.userPhotoService.uploadPhoto.and.returnValue(q.when(false));
        sut.userPhotoService.uploadPhoto().then(() => {
          expect(sut.successUpload).not.toHaveBeenCalled();
        });
      });
    });

    describe('getCurrentPhotoStatus function', () => {
      const isUserPhoto = { button: 'Update Photo', title: 'Update Your Photo' };
      const isNotUserPhoto = { button: 'Upload Photo', title: 'Upload new photo' };

      it('should button toEqual "Upload Photo" when does not have photo', () => {
        sut.user.photo = false;
        sut.currentPhotoStatus = sut.getCurrentPhotoStatus();
        expect(sut.currentPhotoStatus.button).toEqual(isNotUserPhoto.button);
      });
      it('should title toEqual "Upload new photo" when does not have photo', () => {
        sut.user.photo = false;
        sut.currentPhotoStatus = sut.getCurrentPhotoStatus();
        expect(sut.currentPhotoStatus.title).toEqual(isNotUserPhoto.title);
      });

      it('should button toEqual "Update Photo" when have photo', () => {
        sut.user.photo = 'photo link';
        sut.currentPhotoStatus = sut.getCurrentPhotoStatus();
        expect(sut.currentPhotoStatus.button).toEqual(isUserPhoto.button);
      });
      it('should title toEqual "Update Your Photo" when have photo', () => {
        sut.user.photo = 'photo link';
        sut.currentPhotoStatus = sut.getCurrentPhotoStatus();
        expect(sut.currentPhotoStatus.title).toEqual(isUserPhoto.title);
      });
    });

    describe('toggle delete function', () => {
      let preview;
      beforeEach(() => {
        preview = sut.deletePreview;
        sut.toggleDeletePreview();
      });

      it('should toggle deletePreview', () => {
        expect(sut.deletePreview).toEqual(!preview);
      });
    });

    describe('askToDeletePhoto function', () => {
      beforeEach(() => {
        spyOn(sut, 'toggleDeletePreview');
      });
      it('should call deletePreview', () => {
        sut.ava = 'ava';
        sut.user.photo = null;
        sut.askToDeletePhoto();
        expect(sut.toggleDeletePreview).toHaveBeenCalled();
      });
      it('should call deletePreview', () => {
        sut.ava = null;
        sut.user.photo = 'link to photo';
        sut.askToDeletePhoto();
        expect(sut.toggleDeletePreview).toHaveBeenCalled();
      });
      it('shouldn`t call deletePreview', () => {
        sut.ava = null;
        sut.user.photo = null;
        sut.askToDeletePhoto();
        expect(sut.toggleDeletePreview).not.toHaveBeenCalled();
      });
    });

    describe('deletePhoto function', () => {
      beforeEach(() => {
        spyOn(sut, 'toggleAnimation');
        sut.deletePhoto();
      });
      it('should call toggleAnimation', () => {
        expect(sut.toggleAnimation).toHaveBeenCalled();
      });
      it('should call deleteUserPhoto from service', () => {
        expect(sut.userPhotoService.deleteUserPhoto).toHaveBeenCalled();
      });
    });

    describe('call function on delete photo depended on server answer', () => {
      beforeEach(() => {
        spyOn(sut, 'successDelete');
        spyOn(sut, 'errorDelete');
      });

      it('should call function successDelete', () => {
        sut.userPhotoService.deleteUserPhoto().then(() => {
          expect(sut.successUpload).toHaveBeenCalled();
        });
      });
      it('shouldn`t call function errorDelete', () => {
        sut.userPhotoService.deleteUserPhoto().then(() => {
          expect(sut.errorUpload).not.toHaveBeenCalled();
        });
      });
      it('should call function errorDelete', () => {
        sut.userPhotoService.deleteUserPhoto.and.returnValue(q.when(false));
        sut.userPhotoService.deleteUserPhoto().then(() => {
          expect(sut.errorUpload).toHaveBeenCalled();
        });
      });
      it('shouldn`t call function successDelete', () => {
        sut.userPhotoService.deleteUserPhoto.and.returnValue(q.when(false));
        sut.userPhotoService.deleteUserPhoto().then(() => {
          expect(sut.successUpload).not.toHaveBeenCalled();
        });
      });
    });

    describe('successDelete function', () => {
      beforeEach(() => {
        spyOn(sut, 'toggleDeletePreview');
        spyOn(sut, 'toggleAnimation');
        spyOn(sut, 'getCurrentPhotoStatus');
        sut.successDelete();
      });
      it('should ava toBeNull', () => {
        expect(sut.ava).toBeNull();
      });
      it('should call toggleAnimation', () => {
        expect(sut.toggleAnimation).toHaveBeenCalled();
      });
      it('should call toggleDeletePreview', () => {
        expect(sut.toggleDeletePreview).toHaveBeenCalled();
      });
      it('should call getCurrentPhotoStatus', () => {
        expect(sut.getCurrentPhotoStatus).toHaveBeenCalled();
      });
      it('should user.photo to be empty string', () => {
        expect(sut.user.photo).toEqual('');
      });
    });
    describe('errorDelete function', () => {
      let errorMessage;
      beforeEach(() => {
        spyOn(sut, 'toggleAnimation');
        errorMessage = sut.deleteMessage;
        sut.errorDelete();
      });
      it('should toggle error message', () => {
        expect(sut.deleteMessage).toEqual(!errorMessage);
      });
    });
  });
});