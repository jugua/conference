import EditPasswordController from './edit-password.controller';

describe('EditPassword', () => {
  describe('Controller', () => {
    let q;
    let EditPasswordService;
    let Constants;
    let sut;
    let timeout;
    const editing = true;
    const errors = {
      nonSpace: 'Please use at least one non-space character in your password.',
      minLength: 'Your password must be at least 6 characters long. Please try another',
      maxLength: 'Your password should not be longer than 30 characters. Please try another.',
      require: 'All fields are mandatory. Please make sure all required fields are filled out.',
      passwordsMatch: 'Passwords do not match.',
      wrongPassword: 'You entered an invalid current password'
    };

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register('EditPasswordController', EditPasswordController);
    }));

    beforeEach(angular.mock.inject(($controller, $injector) => {
      q = $injector.get('$q');
      timeout = $injector.get('$timeout');
      EditPasswordService = jasmine.createSpyObj('EditPasswordService', ['changePassword', 'getErrors']);
      EditPasswordService.getErrors.and.returnValue(errors);
      EditPasswordService.changePassword.and.returnValue(q.when([]));
      Constants = jasmine.createSpyObj('Constants', ['password']);

      sut = $controller('EditPasswordController', { EditPasswordService, Constants, timeout }, { editing });
    }));

    beforeEach(() => {
      sut.editPasswordForm = jasmine.createSpyObj('editPasswordForm',
        ['currentPassword', 'newPassword', 'confirmNewPassword', '$valid', '$setPristine']);
    });

    describe('Controller has needed property and function', () => {
      it('has property editPasswordForm', () => {
        expect(sut.editPasswordForm).toBeDefined();
      });
      it('has binding property editPassword', () => {
        expect(sut.editing).toBeDefined();
      });
      it('has property passwords to equal empty object', () => {
        expect(sut.passwords).toEqual({});
      });
      it('has property changed to equal false', () => {
        expect(sut.changed).toBe(false);
      });
      it('has property error to equal false', () => {
        expect(sut.error).toBe(false);
      });
      it('has property error to equal false', () => {
        expect(sut.errorMessage).toBeNull();
      });
      it('has function closeEditPassword', () => {
        expect(typeof sut.closeEditPassword).toBe('function');
      });
      it('has function changePassword', () => {
        expect(typeof sut.changePassword).toBe('function');
      });
      it('has function successChanged', () => {
        expect(typeof sut.successChanged).toBe('function');
      });
      it('has function errorChanged', () => {
        expect(typeof sut.errorChanged).toBe('function');
      });
      it('has function checkMatchPasswords', () => {
        expect(typeof sut.checkMatchPasswords).toBe('function');
      });
      it('has function setToDefault', () => {
        expect(typeof sut.setToDefault).toBe('function');
      });
      it('has function showError', () => {
        expect(typeof sut.showError).toBe('function');
      });
    });

    describe('closeEditPassword function', () => {
      beforeEach(() => {
        sut.closeEditPassword();
      });

      it('should set passwords object to empty object', () => {
        expect(sut.passwords).toEqual({});
      });
      it('should error property to false', () => {
        expect(sut.error).toEqual(false);
      });
      it('should set errorMessage property to null', () => {
        expect(sut.errorMessage).toBeNull();
      });
      it('should call $setPristine', () => {
        expect(sut.editPasswordForm.$setPristine).toHaveBeenCalled();
      });
      it('should set binding property editPassword to false', () => {
        expect(sut.editing).toEqual(false);
      });
    });

    describe('changePassword function', () => {
      beforeEach(inject(() => {
        spyOn(sut, 'setToDefault');
        spyOn(sut, 'checkMatchPasswords');
        spyOn(sut, 'showError');
      }));

      it('should call setToDefault', () => {
        sut.changePassword();
        expect(sut.setToDefault).toHaveBeenCalled();
      });

      it('should call showError when $valid false and checkMatchPasswords return true', () => {
        sut.editPasswordForm.$valid = false;
        sut.checkMatchPasswords.and.returnValue(true);
        sut.changePassword();
        expect(sut.showError).toHaveBeenCalled();
      });
      it('should call showError when $valid true and checkMatchPasswords return false', () => {
        sut.editPasswordForm.$valid = true;
        sut.checkMatchPasswords.and.returnValue(false);
        sut.changePassword();
        expect(sut.showError).toHaveBeenCalled();
      });
      it('should call showError when $valid false and checkMatchPasswords return false', () => {
        sut.editPasswordForm.$valid = false;
        sut.checkMatchPasswords.and.returnValue(false);
        sut.changePassword();
        expect(sut.showError).toHaveBeenCalled();
      });

      it('shouldn`t call showError when $valid true and checkMatchPasswords return true', () => {
        sut.editPasswordForm.$valid = true;
        sut.checkMatchPasswords.and.returnValue(true);
        sut.changePassword();
        expect(sut.showError).not.toHaveBeenCalled();
      });
      it('should call changePassword from service when $valid true and checkMatchPasswords return true', () => {
        sut.editPasswordForm.$valid = true;
        sut.checkMatchPasswords.and.returnValue(true);
        sut.changePassword();
        expect(sut.editPasswordService.changePassword).toHaveBeenCalled();
      });
    });

    describe('call functions when call changePassword from service', () => {
      beforeEach(() => {
        spyOn(sut, 'successChanged');
        spyOn(sut, 'errorChanged');
      });
      it('should call successUpload', () => {
        sut.editPasswordService.changePassword().then(() => {
          expect(sut.successChanged).toHaveBeenCalled();
        });
      });
      it('shouldn`t call errorUpload', () => {
        sut.editPasswordService.changePassword().then(() => {
          expect(sut.errorChanged).not.toHaveBeenCalled();
        });
      });
      it('should call errorUpload', () => {
        EditPasswordService.changePassword.and.returnValue(q.when(false));
        sut.editPasswordService.changePassword().then(() => {
          expect(sut.errorChanged).toHaveBeenCalled();
        });
      });
      it('should call errorUpload', () => {
        EditPasswordService.changePassword.and.returnValue(q.when(false));
        sut.editPasswordService.changePassword().then(() => {
          expect(sut.successChanged).not.toHaveBeenCalled();
        });
      });
    });

    describe('successChanged function', () => {
      beforeEach(() => {
        sut.successChanged();
      });
      it('should set passwords to empty object', () => {
        expect(sut.passwords).toEqual({});
      });
      it('should call setPristine to editPasswordForm', () => {
        expect(sut.editPasswordForm.$setPristine).toHaveBeenCalled();
      });
      it('should set changed to true', () => {
        expect(sut.changed).toEqual(true);
      });
      it('should set changed to false after sometime', () => {
        timeout.flush();
        expect(sut.changed).toEqual(false);
      });
      it('should set changed to false after sometime', () => {
        timeout.flush();
        expect(sut.editing).toEqual(false);
      });
    });

    describe('errorChanged function', () => {
      beforeEach(() => {
        spyOn(sut, 'showError');
      });
      it('shouldn`t call showError', () => {
        const error = {
          error: 'error'
        };
        sut.errorChanged(error);
        expect(sut.showError).not.toHaveBeenCalled();
      });
      it('should call showError', () => {
        const error = {
          error: 'error',
          fields: ['currentPassword']
        };
        sut.editPasswordForm = { currentPassword: { $setValidity: () => {} } };
        sut.errorChanged(error);
        expect(sut.showError).toHaveBeenCalled();
      });
      it('should call setValidity', () => {
        const error = {
          error: 'error',
          fields: ['currentPassword']
        };
        sut.editPasswordForm = { currentPassword: { $setValidity: () => {} } };
        spyOn(sut.editPasswordForm.currentPassword, '$setValidity');
        sut.errorChanged(error);
        expect(sut.editPasswordForm.currentPassword.$setValidity).toHaveBeenCalled();
      });
    });

    describe('checkMatchPassword function', () => {
      beforeEach(() => {
        sut.editPasswordForm.newPassword = { $setValidity: () => {} };
        sut.editPasswordForm.confirmNewPassword = { $setValidity: () => {} };
      });

      it('should return true when passwords equal', () => {
        sut.passwords = {
          newPassword: 'password',
          confirmNewPassword: 'password'
        };

        expect(sut.checkMatchPasswords()).toEqual(true);
      });
      it('should return false when passwords not equal', () => {
        sut.passwords = {
          newPassword: 'password1',
          confirmNewPassword: 'password2'
        };
        expect(sut.checkMatchPasswords()).toEqual(false);
      });
      it('when passwords not equal should call $setValidity on newPassword', () => {
        sut.passwords = {
          newPassword: 'password1',
          confirmNewPassword: 'password2'
        };
        spyOn(sut.editPasswordForm.newPassword, '$setValidity');

        sut.checkMatchPasswords();
        expect(sut.editPasswordForm.newPassword.$setValidity).toHaveBeenCalled();
      });
      it('when passwords not equal should call $setValidity on confirmNewPassword', () => {
        sut.passwords = {
          newPassword: 'password1',
          confirmNewPassword: 'password2'
        };
        spyOn(sut.editPasswordForm.confirmNewPassword, '$setValidity');

        sut.checkMatchPasswords();
        expect(sut.editPasswordForm.confirmNewPassword.$setValidity).toHaveBeenCalled();
      });
    });

    describe('setToDefault function', () => {
      beforeEach(() => {
        sut.editPasswordForm.currentPassword = { $setValidity: () => {} };
        sut.editPasswordForm.newPassword = { $setValidity: () => {} };
        sut.editPasswordForm.confirmNewPassword = { $setValidity: () => {} };
      });

      it('should error to equal false', () => {
        sut.setToDefault();
        expect(sut.error).toEqual(false);
      });
      it('should errorMessage to equal null', () => {
        sut.setToDefault();
        expect(sut.errorMessage).toEqual(null);
      });
      it('should call $setValidity for currentPassword', () => {
        spyOn(sut.editPasswordForm.currentPassword, '$setValidity');
        sut.setToDefault();
        expect(sut.editPasswordForm.currentPassword.$setValidity).toHaveBeenCalled();
      });
      it('should call $setValidity for newPassword', () => {
        spyOn(sut.editPasswordForm.newPassword, '$setValidity');
        sut.setToDefault();
        expect(sut.editPasswordForm.newPassword.$setValidity).toHaveBeenCalled();
      });
      it('should call $setValidity for currentPassword', () => {
        spyOn(sut.editPasswordForm.confirmNewPassword, '$setValidity');
        sut.setToDefault();
        expect(sut.editPasswordForm.confirmNewPassword.$setValidity).toHaveBeenCalled();
      });
    });

    describe('showError function', () => {
      beforeEach(() => {
        sut.editPasswordForm.currentPassword = { $error: '' };
        sut.editPasswordForm.newPassword = { $error: '' };
        sut.editPasswordForm.confirmNewPassword = { $error: '' };
      });

      it('should toggle error', () => {
        const error = sut.error;
        sut.showError();
        expect(sut.error).toEqual(!error);
      });

      it('should change text to required error when currentPassword has error required', () => {
        sut.editPasswordForm.currentPassword.$error = { required: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.require);
      });
      it('should change text to required error when newPassword has error required', () => {
        sut.editPasswordForm.newPassword.$error = { required: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.require);
      });
      it('should change text to required error when confirmNewPassword has error required', () => {
        sut.editPasswordForm.confirmNewPassword.$error = { required: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.require);
      });

      it('should change text to minlength error when newPassword has error minlength', () => {
        sut.editPasswordForm.newPassword.$error = { minlength: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.minLength);
      });

      it('should change text to maxlength error when newPassword has error maxlength', () => {
        sut.editPasswordForm.newPassword.$error = { maxlength: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.maxLength);
      });

      it('should change text to pattern error when newPassword has error pattern', () => {
        sut.editPasswordForm.newPassword.$error = { pattern: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.nonSpace);
      });

      it('should change text to passwords_match error when newPassword has error passwords_match', () => {
        sut.editPasswordForm.newPassword.$error = { passwords_match: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.passwordsMatch);
      });
      it('should change text to passwords_match error when confirmNewPassword has error passwords_match', () => {
        sut.editPasswordForm.confirmNewPassword.$error = { passwords_match: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.passwordsMatch);
      });
      it('should change text to passwords_match error when confirmNewPassword has error passwords_match', () => {
        sut.editPasswordForm.currentPassword.$error = { wrong_password: true };
        sut.showError();
        expect(sut.errorMessage).toEqual(sut.errorMessages.wrongPassword);
      });
    });
  });
});