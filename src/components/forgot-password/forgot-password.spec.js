/* global describe, it, expect */

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
});


