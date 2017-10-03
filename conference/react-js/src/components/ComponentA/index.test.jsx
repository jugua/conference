import React from 'react';
import renderer from 'react-test-renderer';
import ComponentA from './index';

jest.unmock('./index');
describe('ComponentA', () => {
  it('ComponentA renders correctly', () => {
    const tree = renderer.create(
      <ComponentA />,
    ).toJSON();
    expect(tree).toMatchSnapshot();
  });
});
