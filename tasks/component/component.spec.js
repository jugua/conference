import <%= pascalName %>Controller from './<%= kebabName %>.controller';

describe('<%= pascalName %>', () => {

it('has  name property == <%= camelName %>', () => {
    let controller = new <%= pascalName %>Controller();
    expect(controller.name).toBe('<%= camelName %>');

 });


});