import React from 'react';
import renderer from 'react-test-renderer';

import SignInForm from './index';

jest.unmock('./index');

describe('Sign in form', () => {
  it('Sign in form renders correctly', () => {
    const tree = renderer.create(
      <SignInForm />,
    ).toJSON();
    expect(tree).toMatchSnapshot();
  });
});
