import HomeController from './home.controller';

describe('Home controller', () => {
  const controller = new HomeController();

  it('has defaultLanding property set to true', () => {
    expect(controller.defaultLanding).toEqual(true);
  });
});