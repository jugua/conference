import NewtalkPopupController from './newtalk-popup.controller';

describe('Submit New Talk popup controller test', () => {
  let sut;

  let Talks;
  let Topic;
  let Type;
  let Language;
  let Level;

  beforeEach(() => {
    Talks = jasmine.createSpyObj('Talks', ['add']);
    Topic = jasmine.createSpyObj('Topic', ['query']);
    Type = jasmine.createSpyObj('Type', ['query']);
    Language = jasmine.createSpyObj('Language', ['query']);
    Level = jasmine.createSpyObj('Level', ['query']);

    sut = new NewtalkPopupController(Talks, Topic, Type, Language, Level);
  });

  it('has Close method', () => {
    expect(typeof sut.close).toBe('function');
  });
});