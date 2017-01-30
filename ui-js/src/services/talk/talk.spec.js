import TalkService from './talk.service';

describe('Talk Service', () => {
  let sut;
  let q;
  let LocalStorage;
  let mockResource;

  beforeEach(inject((_$q_) => {
    q = _$q_;
    LocalStorage = {
      getToken: () => {

      }
    };

    mockResource = function () {
      return {
        add: () => {
          let deferred = q.defer();
          deferred.resolve({id: 1, name: 'test'});
          return deferred.promise;
        },
        getAll: () => {
          let deferred = q.defer();
          deferred.resolve({id: 1, name: 'test'});
          return deferred.promise;
        }
      }

    };

    sut = new TalkService(mockResource, LocalStorage);
    spyOn(sut.talks, 'getAll').and.callThrough();
    spyOn(sut.talks, 'add').and.callThrough();

  }));

  it('TalkService is object', () => {
    expect(sut instanceof Object).toEqual(true);
  });

  it('TalkService.add is defined ', () => {
    expect(sut.add).toBeDefined(true);
  });

  it('TalkService.add is Fuction ', () => {
    expect(typeof sut.add).toBe('function');
  });

  it('TalkService.getAll is defined ', () => {
    expect(sut.getAll).toBeDefined(true);
  });

  it('TalkService.getAll is Fuction ', () => {
    expect(typeof sut.getAll).toBe('function');
  });

  it('TalksService getAll() returns object if resourse returned data', () => {
    expect(sut.getAll() instanceof Object).toBe(true);
  });

  it('TalksService getAll() send request if _talks is empty', () => {
    sut.getAll();
    expect(sut.talks.getAll).toHaveBeenCalled();
  });

  it('TalksService add() send a requrest to backend with talk in params', () => {
    const talk = ['test'];
    sut.add(talk);
    expect(sut.talks.add).toHaveBeenCalledWith(talk, jasmine.any(Function), jasmine.any(Function));
  });



});
