/* global describe, xdescribe, it, xit, expect, beforeEach, angular, jasmine */

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

  xdescribe('Controller', () => {
    let sut;

    let $timeout;

    let Constants;
    let EditEmailService;

    const editing = true;
    const user = {};

    beforeEach(angular.mock.module(($controllerProvider) => {
      $controllerProvider.register(Controller.name, Controller);
    }));

    beforeEach(angular.mock.inject(($injector, $controller) => {
      $timeout = $injector.get('$timeout');

      EditEmailService = jasmine.createSpyObj('EditEmailService', ['updateEmail', 'getMessages']);
      Constants = jasmine.createSpyObj('Constants', ['email']);

      sut = $controller(Controller.name,
        { EditEmailService, Constants, $timeout },
        { editing, user });
    }));


    it('has changeEmail() method defined in prototype', () => {
      expect(sut.changeEmail).toBeDefined();
      expect(typeof sut.changeEmail).toBe('function');
    });
    it('has showError() method defined in prototype', () => {
      expect(sut.showError).toBeDefined();
      expect(typeof sut.showError).toBe('function');
    });
    it('has showConfirm() method defined in prototype', () => {
      expect(sut.showConfirm).toBeDefined();
      expect(typeof sut.showConfirm).toBe('function');
    });
    it('has closeEditEmail() method defined in prototype', () => {
      expect(sut.closeEditEmail).toBeDefined();
      expect(typeof sut.closeEditEmail).toBe('function');
    });
    it('has showTooltip() method defined in prototype', () => {
      expect(sut.showTooltip).toBeDefined();
      expect(typeof sut.showTooltip).toBe('function');
    });
    it('has hideTooltip() method defined in prototype', () => {
      expect(sut.hideTooltip).toBeDefined();
      expect(typeof sut.hideTooltip).toBe('function');
    });
    it('has resetEmailValidity() method defined in prototype', () => {
      expect(sut.resetEmailValidity).toBeDefined();
      expect(typeof sut.resetEmailValidity).toBe('function');
    });
  });
});
