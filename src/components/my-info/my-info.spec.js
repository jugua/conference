import MyInfoModule from './my-info'
import MyInfoController from './my-info.controller';
import MyInfoComponent from './my-info.component';
import MyInfoTemplate from './my-info.html';

describe('MyInfo', () => {
  let makeController, q, deferred, $rootScope, currentServiceMock, scope;

  beforeEach(inject(($q)=>{
      q = $q;
      deferred = $q.defer();
  }));

  beforeEach(() => {
    currentServiceMock = {
      getInfo: () => {},
      updateInfo:()=> {},
      uploadPhoto: ()=> {},
      getPhotoStatus: ()=> {return deferred.promise},
      logout:()=> {}
    }
  });

  beforeEach(inject((_$rootScope_) => {
    $rootScope = _$rootScope_;
    scope = $rootScope.$new();
    makeController = (serviceMock, scope) => {
      return new MyInfoController(serviceMock, scope);
    };
  }));

  describe('Controller', () => {
    it('should instantiate', () => {
      let controller = makeController(currentServiceMock, scope);
    });

    it('should toggle preview', () => {
      let controller = makeController(currentServiceMock, scope);
      let preview = controller.uploadPreview;
      controller.uploadForm.$valid = true;

      controller.togglePreview();
      expect(controller.uploadPreview).toEqual(!preview);
    });

    it('shouldn`t toggle preview', () => {
      let controller = makeController(currentServiceMock, scope);
      let preview = controller.uploadPreview;
      controller.uploadForm.$valid = false;

      controller.togglePreview();
      expect(controller.uploadPreview).toEqual(preview);
    });
  })
});