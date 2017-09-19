jest.unmock('../ComponentA/index.jsx');


import React from 'react';
import { shallow } from 'enzyme';

import MyComponent from '../ComponentA/index.jsx';

describe('<MyComponent />', () => {

  it('renders a <p> with a static text', () => {
    const wrapper = shallow(<MyComponent />);
    expect(wrapper.contains(<p>I am not a very smart component...</p>)).toBe(true);
  });

});
