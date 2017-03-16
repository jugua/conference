import MenuService from './menus.service';

describe('menuService', () => {
  let sut;
  beforeEach(() => {
    sut = new MenuService();
  });

  it('has property getMenu', () => {
    expect(typeof sut.getMenu).toBe('function');
  });

  describe('getMenu() method', () => {
    it('should return false when called with empty role', () => {
      expect(sut.getMenu(false)).toBeFalsy();
    });
    it('should return false when called with empty role', () => {
      expect(sut.getMenu('')).toBeFalsy();
    });
    it('should return speaker-specific menu for speaker', () => {
      expect(sut.getMenu('ROLE_SPEAKER')).toContain({ link: 'tabs.myInfo', name: 'My Info' });
      expect(sut.getMenu('ROLE_SPEAKER')).toContain({ link: 'tabs.myTalks', name: 'My Talks' });
      expect(sut.getMenu('ROLE_SPEAKER')).toContain({ link: 'account', name: 'Settings' });
    });
    it('should return organizer-specific menu for oraniser', () => {
      expect(sut.getMenu('ROLE_ORGANISER')).toContain({ link: 'talks', name: 'Talks' });
      expect(sut.getMenu('ROLE_ORGANISER')).toContain({ link: 'account', name: 'Settings' });
    });
    it('should return admin-specific menu for admin', () => {
      expect(sut.getMenu('ROLE_ADMIN')).toContain({ link: 'manageUsers', name: 'Manage Users' });
    });
  });
});