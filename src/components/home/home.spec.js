import HomeModule from './home'
import HomeController from './home.controller';
import HomeComponent from './home.component';
import HomeTemplate from './home.html';

describe('Home', () => {

it('has  name property == home', () => {
    let controller = new HomeController();
    expect(controller.name).toBe('homes');

 });


});