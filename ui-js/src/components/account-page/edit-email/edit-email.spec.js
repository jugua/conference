import component from './edit-email.component';
import Controller from './edit-email.controller';
import template from './edit-email.html';

describe('EditEmail', () => {
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
    let sut;

    let $timeout;
    let $q;
    let $rootScope;

    let Constants;
    let EditEmailService;

    let deferred;
    let $scope;

    const editing = true;
    const user = {};

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register(Controller.name, Controller);
    }));

    beforeEach(angular.mock.inject(($injector, $controller) => {
      $timeout = $injector.get('$timeout');
      $q = $injector.get('$q');
      $rootScope = $injector.get('$rootScope');

      deferred = $q.defer();
      $scope = $rootScope.$new();

      EditEmailService = jasmine.createSpyObj('EditEmailService', ['updateEmail', 'checkPendingUpdate', 'getMessages']);
      EditEmailService.updateEmail.and.returnValue(deferred.promise);

      EditEmailService.checkPendingUpdate.and.returnValue($q.when({ data: { result: '' } }));

      EditEmailService.getMessages.and.returnValues({
        errInvalidEmail: 'Please enter a valid email address',
        errEmailIsTheSame: 'The new e-mail address you have provided is the same as your current e-mail address',
        errEmailAlreadyExists: 'There is an existing account associated with {{email}}',
        confirmationSent: 'We\'ve sent a confirmation link to your new email address, please check your inbox and confirm your changes'
      });

      Constants = jasmine.createSpyObj('Constants', ['email']);

      sut = $controller(Controller.name,
        { EditEmailService, Constants, $timeout, $scope },
        { editing, user });
    }));

    beforeEach(() => {
      sut.editEmailForm = jasmine.createSpyObj('editEmailForm',
        ['currentEmail', 'newEmail', '$setPristine']);
    });

    describe('has necessary methods defined', () => {
      it('has changeEmail() method defined', () => {
        expect(sut.changeEmail).toBeDefined();
        expect(typeof sut.changeEmail).toBe('function');
      });
      it('has showError() method defined', () => {
        expect(sut.showError).toBeDefined();
        expect(typeof sut.showError).toBe('function');
      });
      it('has showConfirm() method defined', () => {
        expect(sut.showConfirm).toBeDefined();
        expect(typeof sut.showConfirm).toBe('function');
      });
      it('has closeEditEmail() method defined', () => {
        expect(sut.closeEditEmail).toBeDefined();
        expect(typeof sut.closeEditEmail).toBe('function');
      });
      it('has showTooltip() method defined', () => {
        expect(sut.showTooltip).toBeDefined();
        expect(typeof sut.showTooltip).toBe('function');
      });
      it('has hideTooltip() method defined', () => {
        expect(sut.hideTooltip).toBeDefined();
        expect(typeof sut.hideTooltip).toBe('function');
      });
      it('has resetEmailValidity() method defined', () => {
        expect(sut.resetEmailValidity).toBeDefined();
        expect(typeof sut.resetEmailValidity).toBe('function');
      });
    });
    describe('changeEmail() method', () => {
      beforeEach(() => {
        spyOn(sut, 'showConfirm');
        spyOn(sut, 'showError');
        sut.editEmailForm.newEmail = {
          $error: {
            pattern: false,
            required: false
          },
          $setValidity: () => {}
        };
        spyOn(sut.editEmailForm.newEmail, '$setValidity');
      });
      it('show call showError() if newEmail field is not matching a pattern', () => {
        sut.editEmailForm.newEmail.$error.pattern = true;
        sut.changeEmail();
        expect(sut.showError).toHaveBeenCalled();
      });
      it('should call showError() with the correct error message [pattern case]', () => {
        sut.editEmailForm.newEmail.$error.pattern = true;
        sut.changeEmail();
        expect(sut.showError).toHaveBeenCalledWith('Please enter a valid email address');
      });
      it('show call showError() if newEmail field is empty', () => {
        sut.editEmailForm.newEmail.$error.required = true;
        sut.changeEmail();
        expect(sut.showError).toHaveBeenCalled();
      });
      it('should call showError() with the correct error message [required case]', () => {
        sut.editEmailForm.newEmail.$error.required = true;
        sut.changeEmail();
        expect(sut.showError).toHaveBeenCalledWith('Please enter a valid email address');
      });
      it('should call showError() if new email is the same as the old email', () => {
        sut.user.mail = 'same@same.com';
        sut.newEmail = 'same@same.com';
        sut.changeEmail();
        expect(sut.showError).toHaveBeenCalled();
      });
      it('should call showError() with the correct error message [same email case]', () => {
        sut.user.mail = 'same@same.com';
        sut.newEmail = 'same@same.com';
        sut.changeEmail();
        expect(sut.showError)
          .toHaveBeenCalledWith('The new e-mail address you have provided is the same as your current e-mail address');
      });
      it('should call $setValidity()', () => {
        sut.user.mail = 'same@same.com';
        sut.newEmail = 'same@same.com';
        sut.changeEmail();
        expect(sut.editEmailForm.newEmail.$setValidity).toHaveBeenCalled();
      });
      it('should set email_is_the_same validity to false', () => {
        sut.user.mail = 'same@same.com';
        sut.newEmail = 'same@same.com';
        sut.changeEmail();
        expect(sut.editEmailForm.newEmail.$setValidity)
          .toHaveBeenCalledWith('email_is_the_same', false);
      });
      it('should call EditEmailService service updateEmail() method', () => {
        sut.editEmailForm.$valid = true;
        sut.changeEmail();
        expect(EditEmailService.updateEmail).toHaveBeenCalled();
      });
      it('should call EditEmailService service updateEmail() method with newEmail', () => {
        sut.newEmail = 'new@email.com';
        sut.editEmailForm.$valid = true;
        sut.changeEmail();
        expect(EditEmailService.updateEmail).toHaveBeenCalledWith('new@email.com');
      });
      it('should set pendingChange to true upon resolve', () => {
        sut.user.mail = 'old@email.com';
        sut.newEmail = 'new@email.com';
        sut.editEmailForm.$valid = true;
        sut.pendingChange = false;
        sut.changeEmail();

        deferred.resolve();
        $scope.$apply();

        expect(sut.pendingChange).toEqual(true);
      });
      it('should call showConfirm() upon resolve', () => {
        sut.user.mail = 'old@email.com';
        sut.newEmail = 'new@email.com';

        sut.editEmailForm.$valid = true;
        sut.changeEmail();

        deferred.resolve();
        $scope.$apply();

        expect(sut.showConfirm).toHaveBeenCalled();
      });
      it('should call showConfirm() with the correct message', () => {
        sut.user.mail = 'old@email.com';
        sut.newEmail = 'new@email.com';

        sut.editEmailForm.$valid = true;
        sut.changeEmail();

        deferred.resolve();
        $scope.$apply();

        expect(sut.showConfirm)
          .toHaveBeenCalledWith('We\'ve sent a confirmation link to your new email address, please check your inbox and confirm your changes');
      });
      it('should show error upon reject [email_already_exists case]', () => {
        sut.user.mail = 'old@email.com';
        sut.newEmail = 'new@email.com';

        sut.editEmailForm.$valid = true;
        sut.changeEmail();

        deferred.reject({
          data: {
            error: 'email_already_exists'
          }
        });
        $scope.$apply();

        expect(sut.showError).toHaveBeenCalled();
      });
      it('should show the correct error upon reject [email_already_exists case]', () => {
        sut.user.mail = 'old@email.com';
        sut.newEmail = 'new@email.com';

        sut.editEmailForm.$valid = true;
        sut.changeEmail();

        deferred.reject({
          data: {
            error: 'email_already_exists'
          }
        });
        $scope.$apply();

        expect(sut.showError).toHaveBeenCalledWith('There is an existing account associated with new@email.com');
      });
      it('should call $setValidity()', () => {
        sut.user.mail = 'old@email.com';
        sut.newEmail = 'new@email.com';

        sut.editEmailForm.$valid = true;
        sut.changeEmail();

        deferred.reject({
          data: {
            error: 'email_already_exists'
          }
        });
        $scope.$apply();

        expect(sut.editEmailForm.newEmail.$setValidity).toHaveBeenCalled();
      });
      it('should set validity of email_already_exists to false', () => {
        sut.user.mail = 'old@email.com';
        sut.newEmail = 'new@email.com';

        sut.editEmailForm.$valid = true;
        sut.changeEmail();

        deferred.reject({
          data: {
            error: 'email_already_exists'
          }
        });
        $scope.$apply();

        expect(sut.editEmailForm.newEmail.$setValidity).toHaveBeenCalledWith('email_already_exists', false);
      });
      it('should display an unknown server error upon reject [default error handler case]', () => {
        sut.user.mail = 'old@email.com';
        sut.newEmail = 'new@email.com';

        sut.editEmailForm.$valid = true;
        sut.changeEmail();

        deferred.reject({
          data: {
            error: 'unknown_error'
          }
        });
        $scope.$apply();

        expect(sut.showError).toHaveBeenCalledWith('unknown_error');
      });
    });
    describe('showError() method', () => {
      beforeEach(() => {
        sut.error = false;
        sut.errorMessage = null;
        sut.showError('string');
      });
      it('should set error property to true', () => {
        expect(sut.error).toEqual(true);
      });
      it('should set errorMessage property to string', () => {
        expect(sut.errorMessage).toEqual('string');
      });
    });
    describe('showConfirm() method', () => {
      beforeEach(() => {
        sut.timeoutHandler = null;
        sut.confirmTimeout = 1000;
        sut.showConfirm('string');
      });
      it('should set error property to false', () => {
        expect(sut.error).toEqual(false);
      });
      it('should set errorMessage property to null', () => {
        expect(sut.errorMessage).toEqual(null);
      });
      it('should set confirm property to true', () => {
        expect(sut.confirm).toEqual(true);
      });
      it('should set confirmMessage property to argument string', () => {
        expect(sut.confirmMessage).toEqual('string');
      });
      it('upon timeout should change confirm proretry to false', () => {
        $timeout.flush();
        expect(sut.confirm).toEqual(false);
      });
      it('upon timeout should change confirmMessage proretry to null', () => {
        $timeout.flush();
        expect(sut.confirmMessage).toEqual(null);
      });
      it('upon timeout should call the closeEditEmail() method', () => {
        spyOn(sut, 'closeEditEmail');
        $timeout.flush();
        expect(sut.closeEditEmail).toHaveBeenCalled();
      });
    });
    describe('closeEditEmail() method', () => {
      beforeEach(() => {
        sut.closeEditEmail();
      });
      it('sets error property to false', () => {
        expect(sut.error).toEqual(false);
      });
      it('sets errorMessage property to null', () => {
        expect(sut.errorMessage).toEqual(null);
      });
      it('sets newEmail property to null', () => {
        expect(sut.newEmail).toEqual(null);
      });
      it('sets editing property to false', () => {
        expect(sut.editing).toEqual(false);
      });
      it('calls $setPristine', () => {
        expect(sut.editEmailForm.$setPristine).toHaveBeenCalled();
      });
    });
    describe('showTooltip() method', () => {
      beforeEach(() => {
        sut.tooltipVisible = false;
        sut.timeoutHandler = null;
        sut.showTooltip();
      });
      afterEach(() => {
        if (sut.timeoutHandler) {
          $timeout.cancel(sut.timeoutHandler);
        }
      });
      it('should set tooltipVisible property to true', () => {
        expect(sut.tooltipVisible).toEqual(true);
      });
      it('should set timeoutHandler', () => {
        expect(sut.timeoutHandler).toBeDefined();
      });
      it('upon timeout should change tooltipVisible property to false', () => {
        $timeout.flush(3000);
        expect(sut.tooltipVisible).toEqual(false);
      });
    });
    describe('hideTooltip() method', () => {
      beforeEach(() => {
        sut.tooltipVisible = true;

        sut.timeoutHandler = $timeout(() => {}, 1000);
        spyOn(sut.timeout, 'cancel');

        sut.hideTooltip();
      });
      it('should set tooltipVisible to false', () => {
        expect(sut.tooltipVisible).toEqual(false);
      });
      it('should cancel the timeout handler', () => {
        expect(sut.timeout.cancel).toHaveBeenCalled();
      });
    });
    describe('resetEmailValidity() method', () => {
      beforeEach(() => {
        sut.editEmailForm = { newEmail: { $setValidity: () => {} } };
        spyOn(sut.editEmailForm.newEmail, '$setValidity');
        sut.resetEmailValidity();
      });
      it('should call $setValidity()', () => {
        expect(sut.editEmailForm.newEmail.$setValidity).toHaveBeenCalled();
      });
      it('should set email_is_the_same validity to true', () => {
        expect(sut.editEmailForm.newEmail.$setValidity)
          .toHaveBeenCalledWith('email_is_the_same', true);
      });
      it('should set email_already_exists validity to true', () => {
        expect(sut.editEmailForm.newEmail.$setValidity)
          .toHaveBeenCalledWith('email_already_exists', true);
      });
    });
  });
});
