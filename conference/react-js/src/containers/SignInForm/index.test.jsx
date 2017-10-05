import React from 'react';
import renderer from 'react-test-renderer';
import { BrowserRouter as Router, withRouter } from 'react-router-dom';
import SignInForm from './index';

jest.unmock('./index');

describe('Sign in form', () => {
  it('Sign in form renders correctly', () => {
    const Component = withRouter(SignInForm);
    const tree = renderer.create(
      <Router><Component /></Router>,
    ).toJSON();
    expect(tree).toMatchSnapshot();
  });
});
