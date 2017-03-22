/* global describe, xdescribe, it, xit, expect, beforeEach, afterEach, spyOn, angular, jasmine */

import component from './my-info.component';
import Controller from './my-info.controller';
import template from './my-info.html';

describe('MyInfo', () => {
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

    let $rootScope;
    let $scope;
    let $state;
    let $stateParams;

    let Current;

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register(Controller.name, Controller);
    }));

    beforeEach(angular.mock.inject(($injector, $controller) => {
      $rootScope = $injector.get('$rootScope');
      $scope = $rootScope.$new();

      $state = jasmine.createSpyObj('state', ['go', 'reload', '$stateProvider']);
      $stateParams = jasmine.createSpyObj('stateParams', ['fwdState']);
      Current = jasmine.createSpyObj('Current', ['updateInfo']);

      sut = $controller(Controller.name,
        { Current, $scope, $state, $stateParams },
        { }
      );
      sut.currentUserService = Current;
    }));

    describe('has necessary methods defined', () => {
      it('has showAlert() method defined', () => {
        expect(sut.showAlert).toBeDefined();
        expect(typeof sut.showAlert).toBe('function');
      });
      it('has showConfirm() method defined', () => {
        expect(sut.showConfirm).toBeDefined();
        expect(typeof sut.showConfirm).toBe('function');
      });
      it('has hideMessage() method defined', () => {
        expect(sut.hideMessage).toBeDefined();
        expect(typeof sut.hideMessage).toBe('function');
      });
      it('has saveChangesBeforeOut() method defined', () => {
        expect(sut.saveChangesBeforeOut).toBeDefined();
        expect(typeof sut.saveChangesBeforeOut).toBe('function');
      });
      it('has cancelChanges() method defined', () => {
        expect(sut.cancelChanges).toBeDefined();
        expect(typeof sut.cancelChanges).toBe('function');
      });
    });

    describe('submit() method', () => {
      beforeEach(() => {
        sut.userInfoForm = jasmine.createSpyObj('userInfoForm', ['$invalid', '$setPristine']);
        spyOn(sut, 'showAlert');
      });
      it('should call showAlert with error for invalid form', () => {
        sut.userInfoForm.$invalid = true;
        sut.submit();
        expect(sut.showAlert).toHaveBeenCalledWith('error');
      });
      it('should call currentUserService.updateInfo() for valid form', () => {
        sut.userInfoForm.$invalid = false;
        sut.submit();
        expect(sut.currentUserService.updateInfo).toHaveBeenCalled();
      });
      it('should call showAlert with success for valid form', () => {
        sut.userInfoForm.$invalid = false;
        sut.submit();
        expect(sut.showAlert).toHaveBeenCalledWith('success');
      });
      it('should call $setPristine() for valid form', () => {
        sut.userInfoForm.$invalid = false;
        sut.submit();
        expect(sut.userInfoForm.$setPristine).toHaveBeenCalled();
      });
    });
    describe('showAlert() method', () => {
      it('should set message property to message string', () => {
        sut.messages = { string: 'string entry' };
        sut.message = '';
        sut.showAlert('string');
        expect(sut.message).toEqual('string entry');
      });
      it('should set alertVisible property to true', () => {
        sut.alertVisible = false;
        sut.showAlert('string');
        expect(sut.alertVisible).toEqual(true);
      });
    });
    describe('showConfirm() method', () => {
      it('should set message property to relevant messages array entry', () => {
        sut.messages = { string: 'string entry' };
        sut.message = '';
        sut.showConfirm('string');
        expect(sut.message).toEqual('string entry');
      });
      it('should set alertVisible property to true', () => {
        sut.alertVisible = false;
        sut.showConfirm('string');
        expect(sut.confirmVisible).toEqual(true);
      });
    });
    describe('hideMessage() method', () => {
      it('should set alertVisible to false', () => {
        sut.alertVisible = true;
        sut.hideMessage();
        expect(sut.alertVisible).toEqual(false);
      });
      it('should set confirmVisible to false', () => {
        sut.confirmVisible = true;
        sut.hideMessage();
        expect(sut.confirmVisible).toEqual(false);
      });
      it('should set message to empty object', () => {
        sut.message = 'string';
        sut.hideMessage();
        expect(angular.equals(sut.message, {})).toEqual(true);
      });
    });
    describe('saveChangesBeforeOut() method', () => {
      beforeEach(() => {
        spyOn(sut, 'hideMessage');
        spyOn(sut, 'submit');
        spyOn(sut, 'event');
        sut.nextState = jasmine.createSpyObj('nextState', ['name']);
        sut.userInfoForm = jasmine.createSpyObj('userInfoForm', ['$valid', '$setSubmitted']);
      });
      it('should call hideMessage() method', () => {
        sut.saveChangesBeforeOut();
        expect(sut.hideMessage).toHaveBeenCalled();
      });
      it('should call submit() method', () => {
        sut.saveChangesBeforeOut();
        expect(sut.submit).toHaveBeenCalled();
      });
      it('should call $setSubmitted() method', () => {
        sut.saveChangesBeforeOut();
        expect(sut.userInfoForm.$setSubmitted).toHaveBeenCalled();
      });
      it('should call event() for valid form', () => {
        sut.userInfoForm.$valid = true;
        sut.saveChangesBeforeOut();
        expect(sut.event).toHaveBeenCalled();
      });
      it('should call state.go() for valid form', () => {
        sut.userInfoForm.$valid = true;
        sut.saveChangesBeforeOut();
        expect(sut.state.go).toHaveBeenCalled();
      });
    });
    describe('cancelChanges() method', () => {
      beforeEach(() => {
        spyOn(sut, 'hideMessage');
        spyOn(sut, 'event');
        sut.nextState = jasmine.createSpyObj('nextState', ['name']);
      });
      it('should call hideMessage() method', () => {
        sut.cancelChanges();
        expect(sut.hideMessage).toHaveBeenCalled();
      });
      it('should call event() method', () => {
        sut.cancelChanges();
        expect(sut.event).toHaveBeenCalled();
      });
      it('should call state.reload() method', () => {
        sut.cancelChanges();
        expect(sut.state.reload).toHaveBeenCalled();
      });
      it('should call state.go() method', () => {
        sut.cancelChanges();
        expect(sut.state.go).toHaveBeenCalled();
      });
    });
    describe('event listener', () => {
      beforeEach(() => {
        spyOn($scope, '$broadcast').and.callThrough();
      });
      it('should set nextState property', () => {
        $rootScope.$broadcast('$stateChangeStart', 'toStateName');
        spyOn(sut, 'showConfirm');

        expect(sut.nextState).toEqual('toStateName');
      });
      it('should return for pristine form', () => {
        sut.nextState = 'pristine';
        sut.userInfoForm.$pristine = true;

        $rootScope.$broadcast('$stateChangeStart', 'toStateName');

        expect(sut.nextState).toEqual('pristine');
      });
    });
  });
});