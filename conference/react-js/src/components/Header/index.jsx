import React, { PureComponent } from 'react';
import classNames from 'classnames';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

import { baseUrl } from '../../constants/route-url';
import SignInForm from '../../containers/SignInForm';
import UserMenuFilter from '../User-menu-filter';

class Header extends PureComponent {
  constructor() {
    super();
    this.state = {
      visible: false,
    };
  }

  componentWillUnmount() {
    document.removeEventListener('click', this.closeSignIn);
  }

  onButtonAccountClick = () => {
    document.removeEventListener('click', this.closeSignIn);
    if (!this.state.visible) {
      document.addEventListener('click', this.closeSignIn);
    }

    this.setState(prevState => ({
      visible: !prevState.visible,
    }));
  };

  closeSignIn = (event) => {
    const formContainer = document.querySelector('.menu-container__content');
    if (!this.isDescendant(formContainer, event.target)) {
      this.setState({
        visible: false,
      });
      document.removeEventListener('click', this.closeSignIn);
    }
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

  render() {
    const { user: { roles, fname } } = this.props;
    return (
      <header className="header">
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
          >{ fname ? `${fname}'s` : 'Your'} Account
          </button>
          <div className={classNames({
            'menu-container__content': true,
            none: !this.state.visible,
          })}
          >
            {
              roles.length > 0 ?
                <UserMenuFilter
                  roles={roles}
                />
                : <SignInForm />
            }

          </div>
        </div>
      </header>
    );
  }
}

Header.propTypes = {
  user: PropTypes.shape({
    id: PropTypes.number,
    roles: PropTypes.array,
    mail: PropTypes.string,
    fname: PropTypes.string,
    lname: PropTypes.string,
    bio: PropTypes.string,
    job: PropTypes.string,
    company: PropTypes.string,
    past: PropTypes.string,
    photo: PropTypes.string,
    linkedin: PropTypes.string,
    twitter: PropTypes.string,
    facebook: PropTypes.string,
    blog: PropTypes.string,
    info: PropTypes.string,
  }).isRequired,
};

const mapStateToProps = state => ({
  user: state.user,
});

export default connect(mapStateToProps)(Header);
