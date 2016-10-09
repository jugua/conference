import TestMeController from './test-me.controller';

describe('TestMe', () => {

it('has  name property == testMe', () => {
    let controller = new TestMeController();
    expect(controller.name).toBe('testMe');

 });


});