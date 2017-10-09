import React, { PureComponent } from 'react';
import classNames from 'classnames';
// import { connect } from 'react-redux';
// import { bindActionCreators } from 'redux';
import {
  Link,
} from 'react-router-dom';
import {
  baseUrl,
} from '../../constants/route-url';
// import SignInForm from '../../containers/SignInForm';
// import UserMenu from '../User-menu';

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
    // const user = this.props.user;
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
          >Your Account
          </button>
          <div className={classNames({
            'menu-container__content': true,
            none: !this.state.visible,
          })}
          >
            {/* { */}
            {/* user ? */}
            {/* <UserMenu */}
            {/* data={[ */}
            {/* { Conferences: `${baseUrl}` }, */}
            {/* { Talks: `${talks}` }, */}
            {/* { Settings: `${settings}` }, */}
            {/* { 'Manage user': `${manageUser}` }, */}
            {/* { 'Sign Out': `${baseUrl}` }, */}
            {/* ]} */}
            {/* : <SignInForm /> */}
            {/* } */}

          </div>
        </div>
      </header>
    );
  }
}

// const mapStateToProps = state => ({
//   user: state.user,
// });

// export default connect(mapStateToProps)(Header);
export default Header;
