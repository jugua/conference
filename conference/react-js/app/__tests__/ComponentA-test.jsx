import React from 'react';
import { shallow } from 'enzyme';

import Foo from '../src/ComponentA/index.jsx';

jest.unmock('../src/ComponentA/index.jsx');

describe('<Foo />', () => {
  it('renders a <p> with a static text', () => {
    const wrapper = shallow(<Foo />);
    expect(wrapper.contains(<p>I am not a very smart component...</p>)).toBe(true);
  });
});
