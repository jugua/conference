import React from 'react';
import { shallow } from 'enzyme';
import MyTalks from '../';

describe('>>>> MyTalks  -- Shallow Render React Component', () => {
  let wrapper;
  const columns = [
    'conferenceName',
    'id',
    'title',
    'status',
  ];
  const onClick = () => {};

  beforeEach(() => {
    wrapper = shallow(<MyTalks columns={columns} onClick={onClick} />);
  });

  it('++++ render the DUMB Component', () => {
    expect(wrapper.length).toEqual(1);
  });
});
