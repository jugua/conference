import <%= pascalName %>Module from './<%= kebabName %>'
import <%= pascalName %>Controller from './<%= kebabName %>.controller';
import <%= pascalName %>Component from './<%= kebabName %>.component';
import <%= pascalName %>Template from './<%= kebabName %>.html';

describe('<%= pascalName %>', () => {

it('has  name property == <%= camelName %>', () => {
    let controller = new <%= pascalName %>Controller();
    expect(controller.name).toBe('<%= camelName %>');

 });


});