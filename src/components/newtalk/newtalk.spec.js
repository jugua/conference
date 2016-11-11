import NewtalkController from './newtalk.controller';


describe('NewTalk state', () => {
  let sut, userServiceMock;

  beforeEach(() => {
    userServiceMock = {
      bio: 'aa',
      job: 'aaaa',
      past: 'a'
    };
    sut = new NewtalkController(userServiceMock);
  });
  it('check if currentUser object exists', () => {
    expect(sut.current).toBeDefined();
  });

  it('check if currentUser mandatory fields non empty', () => {
    if (sut.current.bio && sut.current.job && sut.current.past) {
      expect(sut.isEmptyBio).toEqual(false);
    } else {
      expect(sut.isEmptyBio).toEqual(true);
    }
  });

});



