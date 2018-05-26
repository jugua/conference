import React, { PureComponent } from 'react';
import classNames from 'classnames';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

import { root } from '../../constants/route-url';
import SignInForm from '../../components/SignInForm/SignInForm';
import UserMenu from '../../components/UserMenu/UserMenu';
import logout from '../../actions/logout';
import load from '../../actions/load';
import forgotPasswordActions from '../../actions/forgot-password';

class Header extends PureComponent {
  constructor() {
    super();
    this.state = {
      dropdown: false,
    };
  }

  componentWillUnmount() {
    document.removeEventListener('click', this.closeSignIn);
  }

  onButtonAccountClick = () => {
    document.removeEventListener('click', this.closeSignIn);
    if (!this.state.dropdown) {
      document.addEventListener('click', this.closeSignIn);
    }

    this.setState(prevState => ({
      dropdown: !prevState.dropdown,
    }));
  };

  setForgotPasswordVisibility = visibility => (
    this.props.dispatch(
      forgotPasswordActions.setForgotPasswordVisibility(visibility),
    )
  );

  closeSignIn = (event) => {
    const formContainer = document.querySelector('.menu-container__content');
    if (!this.isDescendant(formContainer, event.target)) {
      this.closeDropDown();
      document.removeEventListener('click', this.closeSignIn);
    }
  };

  closeDropDown = () => {
    this.setState({
      dropdown: false,
    });
  };

  isDescendant = (parent, child) => {
    let node = child.parentNode;
    while (node != null) {
      if (node === parent) {
        return true;
      }
      node = node.parentNode;
    }
    return false;
  };

  load = (actionType, payload) => this.props.dispatch(
    load(actionType, payload),
  );

  render() {
    const { user: { role, firstName }, dispatch } = this.props;
    const logoutAction = () => logout(dispatch);

    return (
      <header className="header">
        <div className="header-wrapper">
          <div className="header__title">
            <Link
              className="link_header"
              to={root}
            >
              conference management
            </Link>
          </div>
          <div className="menu-container">
            <button
              className="menu-container__button js-dropdown"
              onClick={this.onButtonAccountClick}
            >{ firstName ? `${firstName}'s` : 'Your'} Account
            </button>
            <div className={classNames({
              'menu-container__content': true,
              none: !this.state.dropdown,
            })}
            >
              {
                role ?
                  <UserMenu
                    close={this.closeDropDown}
                    logout={logoutAction}
                  /> :
                  <SignInForm
                    load={this.load}
                    close={this.closeDropDown}
                    setForgotPasswordVisibility={
                      this.setForgotPasswordVisibility
                    }
                  />
              }
            </div>
          </div>
        </div>
      </header>
    );
  }
}

Header.propTypes = {
  dispatch: PropTypes.func.isRequired,
  user: PropTypes.shape({
    firstName: PropTypes.string,
    role: PropTypes.string,
    conferenceCount: PropTypes.number,
    talksCount: PropTypes.number,
  }).isRequired,
};

export default connect(
  ({ user }) => ({ user }),
)(Header);
