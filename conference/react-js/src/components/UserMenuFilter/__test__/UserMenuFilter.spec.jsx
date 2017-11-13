import React from 'react';
import { shallow } from 'enzyme';
import { speaker, admin } from '../../../constants/roles';
import UserMenuFilter from '../UserMenuFilter';

describe('Component: UserMenu', () => {
  let wrapper;
  const minProp = {
    role: speaker,
    logout: () => {},
    close: () => {} };

  it('++ render UserMenuFilter with role speacker', () => {
    minProp.role = speaker;
    wrapper = shallow(<UserMenuFilter {...minProp} />);
    expect(wrapper.find('UserMenu').prop('data').length).toEqual(3);
  });

  it('++ render UserMenuFilter with role admin', () => {
    minProp.role = admin;
    wrapper = shallow(<UserMenuFilter {...minProp} />);
    expect(wrapper.find('UserMenu').prop('data').length).toEqual(5);
  });
});
