import React, { PureComponent } from 'react';
import classNames from 'classnames';
import SignInForm from '../../containers/SignInForm';

class Header extends PureComponent {
  constructor() {
    super();
    this.state = {
      visible: false,
    };
  }

  onButtonAccountClick = () => {
    this.setState(
      { visible: !this.state.visible },
      () => {
        document.removeEventListener('click', this.closeSignIn);
        if (this.state.visible) {
          document.addEventListener('click', this.closeSignIn);
        }
      },
    );
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
    // console.log(this.state.visible);
    return (
      <header className="header">
        <div className="header__title"><a href="/#/" className="link_header">
            conference management</a></div>
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
            <SignInForm />
          </div>
        </div>
      </header>
    );
  }
}

export default Header;
