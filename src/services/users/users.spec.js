import UsersModule from './users'
import UsersService from './users.service';

describe('Users', () => {
    let $rootScope, makeController;

    beforeEach(window.module('app'));

    describe('Service', () => {
        // component/directive specs
        let service = UsersService();


        it('the name property has the correct value', () => {
          //  expect(service.name).toBe('users');
        });
    });
});
