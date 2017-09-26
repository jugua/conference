import React, { PureComponent } from 'react';
import SignInForm from '../../containers/SignInForm';

class Header extends PureComponent {
  constructor() {
    super();

    this.onButtonAccountClick = this.onButtonAccountClick.bind(this);
    this.state = { visible: true };
  }

  onButtonAccountClick() {
    const currentState = this.state.visible;
    this.setState({ visible: !currentState });
  }
  render() {
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
          <div className={`menu-container__content
              ${this.state.visible ? 'none' : ''}`}
          >
            <SignInForm />
          </div>
        </div>
      </header>
    );
  }
}

export default Header;
