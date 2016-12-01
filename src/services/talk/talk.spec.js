import TalkService from './talk.service';

describe('Talk Service', () => {
  let sut;
  let q;
  let LocalStorage;
  let mockResource;

  beforeEach(inject((_$q_) => {

    q = _$q_;
    LocalStorage = {
      getToken: ()=> {

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
  }));


  it('TalkService method clear clears _talks', () => {
    sut._talks = ['test'];
    sut.clear();
    expect(sut._talks).toEqual(null);
  });
});

