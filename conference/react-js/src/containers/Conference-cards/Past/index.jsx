import React, { PureComponent } from 'react';
import axios from 'axios';
import baseUrl from '../../../constants/backend-url';
import Card from '../../../components/Card';

class Past extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: null,
    };
  }

  componentWillMount() {
    axios.get(`${baseUrl}/api/conference/past`)
      .then(({ data }) => {
        this.setState({ data });
      });
  }

  setCards = data => Object.values(data)
    .map(element => (
      <Card data={element} key={element.id} />),
    );
  render() {
    const { data } = this.state;
    if (!data) {
      return <div />;
    }
    return (
      <div className="tabs-container">
        {this.setCards(data)}
      </div>
    );
  }
}

export default Past;
