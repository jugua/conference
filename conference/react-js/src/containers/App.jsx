import React, { Component } from 'react';
import Header from '../components/Header';
import Talks from '../components/Talks';
import '../scss/main.scss';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = new Date();
  }
  render() {
    return (
      <div>
        <Header />
        <Talks />
      </div>
    );
  }
}

export default App;
