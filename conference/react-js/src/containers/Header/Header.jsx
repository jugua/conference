import React, { PureComponent } from 'react';
import classNames from 'classnames';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { bindActionCreators } from 'redux';

import { baseUrl } from '../../constants/route-url';
import SignInForm from '../../components/SignInForm/SignInForm';
import UserMenuFilter from '../../components/UserMenuFilter/UserMenuFilter';
import logout from '../../actions/logout';
import load from '../../actions/load';

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

  closeSignIn = (event) => {
    const formContainer = document.querySelector('.menu-container__content');
    if (!this.isDescendant(formContainer, event.target)) {
      this.setState({
        dropdown: false,
      });
      document.removeEventListener('click', this.closeSignIn);
    }
  };

  closeDropDown = () => {
    this.setState({
      dropdown: false,
    });
  }

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
    const logoutAction = bindActionCreators(logout, dispatch);

    return (
      <header className="header">
        <div className="header-wrapper">
          <div className="header__title">
            <Link
              className="link_header"
              to={baseUrl}
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
                  <UserMenuFilter
                    close={this.closeDropDown}
                    role={role}
                    logout={logoutAction}
                  /> :
                  <SignInForm
                    load={this.load}
                    close={this.closeDropDown}
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

const mapStateToProps = state => ({
  user: state.user,
});

export default connect(mapStateToProps)(Header);
