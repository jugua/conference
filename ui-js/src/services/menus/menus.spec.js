/* global describe, beforeEach, it, expect */

import MenuService from './menus.service';

describe('menuService', () => {
  let sut;
  beforeEach(()=> {
    sut = new MenuService();
  });

  it('has property getMenu', () => {
    expect(typeof sut.getMenu).toBe('function');
  });

  it('has property getTopics', () => {
    expect(typeof sut.getTopics).toBe('function');
  });

  it('has property getTypes', () => {
    expect(typeof sut.getTypes).toBe('function');
  });

  it('has property getLang', () => {
    expect(typeof sut.getLang).toBe('function');
  });

  it('has property getTalksLevels', () => {
    expect(typeof sut.getTalksLevels).toBe('function');
  });

  it('has property getStatus', () => {
    expect(typeof sut.getStatus).toBe('function');
  });

  it('getTopics returns array', () => {
    expect(sut.getTopics() instanceof Array).toBe(true);
  });

  it('getTypes reurns array', () => {
    expect(sut.getTypes() instanceof Array).toBe(true);
  });

  it('getLang returns array', () => {
    expect(sut.getLang() instanceof Array).toBe(true);
  });

  it('getTalksLevel returns array', () => {
    expect(sut.getTalksLevels() instanceof Array).toBe(true);
  });

  it('getStatus returns array', () => {
    expect(sut.getStatus() instanceof Array).toBe(true);
  });

  describe('getMenu() method', () => {
    it('should return false when called with empty role', () => {
      expect(sut.getMenu(false)).toBeFalsy();
    });
    it('should return false when called with empty role', () => {
      expect(sut.getMenu('')).toBeFalsy();
    });
    it('should return speaker-specific menu for speaker', () => {
      expect(sut.getMenu('s')).toContain({ link: 'tabs.myInfo', name: 'My Info' });
      expect(sut.getMenu('s')).toContain({ link: 'tabs.myTalks', name: 'My Talks' });
      expect(sut.getMenu('s')).toContain({ link: 'account', name: 'Settings' });
    });
    it('should return organizer-specific menu for oranizer', () => {
      expect(sut.getMenu('o')).toContain({ link: 'talks', name: 'Talks' });
      expect(sut.getMenu('o')).toContain({ link: 'account', name: 'Settings' });
    });
  });
});