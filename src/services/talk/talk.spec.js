import TalkService from './talk.service';

describe('Talk Service', () => {
  let sut;
  let q;
  let LocalStorage;

  beforeEach(inject((_$q_) => {

    q = _$q_;
    mockResource = {
      add: () => {
        deferred = $q.defer();
        deferred.resolve({id: 1, :name:'test'});
        return deferred.promise;
      },
      getAll: () => {
        deferred = $q.defer();
        deferred.resolve({id: 1, :name:'test'});
        return deferred.promise;
      }
    };

    sut = new TalkService(LocalStorage, mockResource);
  }));


  it('TalkService has method clear', () => {

  });
});

