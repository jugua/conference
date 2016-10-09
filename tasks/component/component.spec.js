import <%= pascalName %>Controller from './<%= kebabName %>.controller';

describe('<%= pascalName %>', () => {

it('has  name property == <%= camelName %>', () => {
    let controller = new TestController();
    expect(controller.name).toBe('<%= camelName %>');

 });


});