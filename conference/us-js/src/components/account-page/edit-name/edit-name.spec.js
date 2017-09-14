import EditNameController from './edit-name.controller';
import EditNameComponent from './edit-name.component';
import EditNameTemplate from './edit-name.html';


describe('EditName', () => {
  describe('Controller', () => {
    let q;
    let sut;
    let timeout;
    let Current;
    let $state;
    const changename = true;
    const user = {
      fname: 'fname',
      lname: 'lname',
    };

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register('EditNameController', EditNameController);
    }));

    beforeEach(angular.mock.inject(($controller, $injector) => {
      timeout = $injector.get('$timeout');
      Current = jasmine.createSpyObj('Current', ['updateInfo']);
      $state = jasmine.createSpyObj('state', ['go', '$stateProvider']);

      sut = $controller('EditNameController', { Current, timeout, $state }, { changename, user });
    }));

    beforeEach(() => {
      sut.editNameForm = jasmine.createSpyObj('editNameForm', ['$setPristine', '$invalid']);
    });

    describe('Controller has needed property and function', () => {
      it('has property editNameForm', () => {
        expect(sut.editNameForm).toBeDefined();
      });
      it('has binding property changename', () => {
        expect(sut.changename).toBeDefined();
      });
      it('has property changed to equal false', () => {
        expect(sut.changed).toBe(false);
      });
      it('has property changed to equal false', () => {
        expect(sut.userInfo).toBeDefined();
      });
      it('has property error to equal false', () => {
        expect(sut.error).toBe(false);
      });
      it('has function close', () => {
        expect(typeof sut.close).toBe('function');
      });
      it('has function submit', () => {
        expect(typeof sut.submit).toBe('function');
      });
      it('has function showAlert', () => {
        expect(typeof sut.showAlert).toBe('function');
      });
    });

    describe('userInfo fields has needed information', () => {
      it('userInfo fname to equal user fname', () => {
        expect(sut.userInfo.fname).toEqual(user.fname);
      });
      it('userInfo lname to equal user lname', () => {
        expect(sut.userInfo.lname).toEqual(user.lname);
      });
    });

    describe('close function', () => {
      beforeEach(() => {
        sut.close();
      });

      it('should set changename property to false', () => {
        expect(sut.changename).toBeFalsy();
      });
    });

    describe('submit function', () => {
      beforeEach(() => {
        spyOn(sut, 'showAlert');
        sut.userInfo.fname = 'changed fname';
        sut.userInfo.lname = 'changed lname';
        sut.editNameForm.$invalid = false;
      });

      it('should call showAlert with "error"', () => {
        sut.editNameForm.$invalid = true;
        sut.submit();
        expect(sut.showAlert).toHaveBeenCalledWith('error');
      });
      it('should call showAlert with "success"', () => {
        sut.submit();
        expect(sut.showAlert).toHaveBeenCalledWith('success');
      });
      it('should call updateInfo from service', () => {
        sut.submit();
        expect(sut.currentUserService.updateInfo).toHaveBeenCalled();
      });
      it('should call $setPristine', () => {
        sut.submit();
        expect(sut.editNameForm.$setPristine).toHaveBeenCalled();
      });
      it('should change user fname from userInfo', () => {
        sut.submit();
        expect(sut.user.fname).toEqual(sut.userInfo.fname);
      });
      it('should change user lname from userInfo', () => {
        sut.submit();
        expect(sut.user.lname).toEqual(sut.userInfo.lname);
      });
    });

    describe('showAlert function', () => {
      it('should set error to true when call with "error"', () => {
        sut.showAlert('error');
        expect(sut.error).toBeTruthy();
      });
      it('should set changed to true when call with "success"', () => {
        sut.showAlert('success');
        expect(sut.changed).toBeTruthy();
      });
      it('should set error to false when call with "success"', () => {
        sut.showAlert('success');
        expect(sut.error).toBeFalsy();
      });
      it('should set changed to false when call with "success"', () => {
        sut.showAlert('success');
        sut.timeout.flush();
        expect(sut.changed).toBeFalsy();
      });
      it('should set changename to false when call with "success"', () => {
        sut.showAlert('success');
        sut.timeout.flush();
        expect(sut.changename).toBeFalsy();
      });
      it('should call state.go when call with "success"', () => {
        sut.showAlert('success');
        sut.timeout.flush();
        expect(sut.state.go).toHaveBeenCalled();
      });
      it('should call state.go with parameters when call with "success"', () => {
        sut.showAlert('success');
        sut.timeout.flush();
        expect(sut.state.go).toHaveBeenCalledWith('header.account', {}, { reload: true });
      });
    });
  });
});
