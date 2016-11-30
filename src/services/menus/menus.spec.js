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


})