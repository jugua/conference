import controller from './talks.controller';

describe('Talks controller', () => {
  let $q;

  let deferred;

  let sut;

  let Current;
  let Talks;
  let User;
  let Topic;

  beforeEach(angular.mock.module(($controllerProvider) => {
    $controllerProvider.register(controller.name, controller);
  }));

  beforeEach(angular.mock.inject(($injector, $controller) => {
    $q = $injector.get('$q');

    deferred = $q.defer();

    Current = jasmine.createSpyObj('Current', ['current']);
    Current.current.and.returnValue(deferred.promise);

    Talks = jasmine.createSpyObj('Talks', ['get', 'getAll']);
    Talks.get.and.returnValue(deferred.promise);
    Talks.getAll.and.returnValue(deferred.promise);

    User = jasmine.createSpyObj('User', ['get']);
    User.get.and.returnValue($q.when({ name: 'John' }));

    Topic = jasmine.createSpyObj('Topic', ['query']);

    sut = $controller(controller.name, { Current, Talks, User, Topic }, {});
  }));

  describe('has necessary methods defined', () => {
    it('showSettings() method is defined', () => {
      expect(sut.showSettings).toBeDefined();
      expect(typeof sut.showSettings).toBe('function');
    });
    it('review() method is defined', () => {
      expect(sut.review).toBeDefined();
      expect(typeof sut.review).toBe('function');
    });
    it('hideReviewPopup() method is defined', () => {
      expect(sut.hideReviewPopup).toBeDefined();
      expect(typeof sut.hideReviewPopup).toBe('function');
    });
    it('userInfo() method is defined', () => {
      expect(sut.userInfo).toBeDefined();
      expect(typeof sut.userInfo).toBe('function');
    });
    it('hideUserInfoPopup() method is defined', () => {
      expect(sut.hideUserInfoPopup).toBeDefined();
      expect(typeof sut.hideUserInfoPopup).toBe('function');
    });
  });
  describe('showSettings() method', () => {
    it('inverts showFilters value', () => {
      sut.showFilters = true;
      sut.showSettings();
      expect(sut.showFilters).toEqual(false);
    });
  });
  describe('review() method', () => {
    it('sets reviewTalkObj to argument object', () => {
      sut.reviewTalkObj = {};
      sut.review({ a: 1 });
      expect(sut.reviewTalkObj).toEqual({ a: 1 });
    });
    it('sets showReviewPopup to true', () => {
      sut.showReviewPopup = false;
      sut.review({ a: 1 });
      expect(sut.showReviewPopup).toEqual(true);
    });
  });
  describe('hideReviewPopup() method', () => {
    it('sets reviewTalkObj to empty object', () => {
      sut.reviewTalkObj = { a: 1 };
      sut.hideReviewPopup();
      expect(sut.reviewTalkObj).toEqual({});
    });
    it('sets showReviewPopup to false', () => {
      sut.showReviewPopup = true;
      sut.hideReviewPopup();
      expect(sut.showReviewPopup).toEqual(false);
    });
  });
  describe('userInfo() method', () => {
    it('sets userInfoObj to the returned promise', () => {
      sut.userInfoObj = {};
      sut.userInfo();

      expect(sut.userInfoObj.$$state.status).toEqual(1);
      expect(sut.userInfoObj.$$state.value).toEqual({ name: 'John' });
    });
  });
  describe('hideUserInfoPopup() method', () => {
    it('sets userInfoId to null', () => {
      sut.userInfoId = 123;
      sut.hideUserInfoPopup();
      expect(sut.userInfoId).toEqual(null);
    });
    it('sets showUserInfoPopup to false', () => {
      sut.showUserInfoPopup = true;
      sut.hideUserInfoPopup();
      expect(sut.showUserInfoPopup).toEqual(false);
    });
  });
});