import component from './forgot-password.component';
import Controller from './forgot-password.controller';
import template from './forgot-password.html';

describe('ForgotPassword', () => {
  const constantsServiceMock = { passwordConstants: '' };
  const ForgotPasswordServiceMock = {};

  it('has template defined', () => {
    expect(component.template).toBeDefined();
  });
  it('has controller defined', () => {
    expect(component.controller).toBeDefined();
  });
  it('has controller configured to the same controller we check', () => {
    expect(component.controller).toBe(Controller);
  });
  it('has template configured to the same template we check', () => {
    expect(component.template).toBe(template);
  });

  describe('Controller', () => {
    it('is a contructor', () => {
      expect(typeof Controller).toBe('function');
      expect(/class/.test(Controller.toString())).toBe(true);
    });
    it('has restore() method defined in prototype', () => {
      expect(Controller.prototype.restore).toBeDefined();
    });
    it('has resetEmailNotFound() method defined in prototype', () => {
      expect(Controller.prototype.resetEmailNotFound).toBeDefined();
    });
  });

  describe('Controller instance', () => {
    const controller = new Controller(ForgotPasswordServiceMock, constantsServiceMock);

    it('has forgotten property defined', () => {
      expect(controller.forgotten).toBeDefined();
    });
    it('has forgotten property set to false by default', () => {
      expect(controller.forgotten).toBe(false);
    });
    it('has restore() method defined', () => {
      expect(controller.restore).toBeDefined();
    });
    it('has resetEmailNotFound() method defined', () => {
      expect(controller.resetEmailNotFound).toBeDefined();
    });
  });

  describe('controller methods', () => {

    let sut;

    let $q;
    let $rootScope;

    let $scope;
    let deferred;

    let Constants;
    let ForgotPasswordService;

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register(Controller.name, Controller);
    }));

    beforeEach(angular.mock.inject(($injector, $controller) => {
      $q = $injector.get('$q');
      $rootScope = $injector.get('$rootScope');

      deferred = $q.defer();
      $scope = $rootScope.$new();

      ForgotPasswordService = jasmine.createSpyObj('ForgotPasswordService', ['restore']);
      ForgotPasswordService.restore.and.returnValue($q.when([]));

      Constants = jasmine.createSpyObj('Constants', ['email']);

      sut = $controller(Controller.name, { ForgotPasswordService, Constants, $scope });
    }));

    beforeEach(() => {
      sut.userForm = { mail: { $setValidity: () => {} } };
      spyOn(sut.userForm.mail, '$setValidity');
    });
    describe('resetEmailNotFound() method', () => {
      it('should set validity of email_not_found to true', () => {
        sut.resetEmailNotFound();
        expect(sut.userForm.mail.$setValidity).toHaveBeenCalledWith('email_not_found', true);
      });
    });
  });
});
