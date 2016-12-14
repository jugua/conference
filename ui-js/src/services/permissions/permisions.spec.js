import Permissions from './permissions.service';

describe('Permissions service', () => {
  let sut;
  let rootScope;

  beforeEach(inject((_$rootScope_,) => {
    rootScope = _$rootScope_.$new();
    spyOn(rootScope, '$broadcast');
    sut = Permissions(rootScope);
  }));

  it('called SignIn event if no user', () => {
    sut.permitted('s', null);
    expect(rootScope.$broadcast).toHaveBeenCalledWith('signInEvent');
  });

  it('SignIn event not called if role is in user roles', () => {
    sut.permitted('s', {roles: ['s', 'u']});
    expect(rootScope.$broadcast).not.toHaveBeenCalled();
  });

  it('SignIn event called if role is not in user roles', () => {
    sut.permitted('s', {roles: ['a', 'o']});
    expect(rootScope.$broadcast).toHaveBeenCalled();
  });

});
