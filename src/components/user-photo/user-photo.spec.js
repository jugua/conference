import UserPhotoModule from './user-photo'
import UserPhotoController from './user-photo.controller';
import UserPhotoComponent from './user-photo.component';
import UserPhotoTemplate from './user-photo.html';


//describe('MyInfo', () => {
//  let makeController, q, deferred, $rootScope, currentServiceMock, scope;
//
//  beforeEach(inject(($q)=>{
//      q = $q;
//      deferred = $q.defer();
//  }));
//
//  beforeEach(() => {
//    currentServiceMock = {
//      getInfo: () => {},
//      updateInfo:()=> {},
//      uploadPhoto: ()=> {},
//      logout:()=> {}
//    }
//  });
//
//  beforeEach(inject((_$rootScope_) => {
//    $rootScope = _$rootScope_;
//    scope = $rootScope.$new();
//    makeController = (serviceMock, scope) => {
//      return new MyInfoController(serviceMock, scope);
//    };
//  }));
//
//  describe('Controller', () => {
//    it('should instantiate', () => {
//      let controller = makeController(currentServiceMock, scope);
//    });
//
//    it('should toggle preview', () => {
//      let controller = makeController(currentServiceMock, scope);
//      let preview = controller.uploadPreview;
//      controller.uploadForm.$valid = true;
//      controller.uploadForm.$setValidity = () => {};
//
//      controller.togglePreview();
//      expect(controller.uploadPreview).toEqual(!preview);
//    });
//
//    it('shouldn`t toggle preview', () => {
//      let controller = makeController(currentServiceMock, scope);
//      let preview = controller.uploadPreview;
//      controller.uploadForm.$valid = false;
//      controller.uploadForm.$setValidity = () => {};
//
//      controller.togglePreview();
//      expect(controller.uploadPreview).toEqual(preview);
//    });
//
//    // it('should call functions in successUpload', () => {
//    //   let controller = makeController(currentServiceMock, scope);
//    //   spyOn(controller, 'togglePreview');
//    //   spyOn(controller, 'toggleSlide');
//    //   spyOn(controller, 'toggleAnimation');
//    //
//    //   controller.successUpload({data:{answer:'link'}});
//    //   expect(controller.togglePreview).toHaveBeenCalled();
//    //   expect(controller.toggleSlide).toHaveBeenCalled();
//    //   expect(controller.toggleAnimation).toHaveBeenCalled();
//    // });
//
//    it('should call functions in errorUpload', () => {
//      let controller = makeController(currentServiceMock, scope);
//      let file = {};
//      controller.uploadForm.$setValidity = () => {};
//
//      spyOn(controller, 'togglePreview');
//      spyOn(controller, 'toggleAnimation');
//      spyOn(controller.uploadForm, '$setValidity');
//      controller.errorUpload({data:{error: 'maxSize'}});
//
//      expect(controller.togglePreview).toHaveBeenCalled();
//      expect(controller.toggleAnimation).toHaveBeenCalled();
//      expect(controller.uploadForm.$setValidity).toHaveBeenCalled();
//      expect(controller.file).toEqual(file);
//    });
//  })
//});