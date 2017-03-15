import NewtalkPopupController from './newtalk.controller';

describe ('Submit New Talk popup controller test', () => {
  let sut;
  beforeEach(() => {
    sut = new NewtalkPopupController();
  });

  it ('has Close method', () => {
    expect(typeof sut.close).toBe('function');
  });
})