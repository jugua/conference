import MenuService from './menus.service';

describe('menuService', () => {
  let cut;
  beforeEach(()=> {
    cut = new MenuService();
  });

  it('has property getMenu', () => {
    expect(typeof cut.getMenu).toBe('function');
  });

  it('has property getTopics', () => {
    expect(typeof cut.getTopics).toBe('function');
  });

  it('has property getTypes', () => {
    expect(typeof cut.getTypes).toBe('function');
  });

  it('has property getLang', () => {
    expect(typeof cut.getLang).toBe('function');
  });

  it('has property getTalksLevels', () => {
    expect(typeof cut.getTalksLevels).toBe('function');
  });

  it('getTopics returns array', () => {
    expect(cut.getTopics() instanceof Array).toBe(true);
  });

  it('has property getTypes', () => {
    expect(cut.getTypes() instanceof Array).toBe(true);
  });

  it('getLang returns array', () => {
    expect(cut.getLang() instanceof Array).toBe(true);
  });

  it('getTalksLevel returns array', () => {
    expect(cut.getTalksLevels() instanceof Array).toBe(true);
  });


})