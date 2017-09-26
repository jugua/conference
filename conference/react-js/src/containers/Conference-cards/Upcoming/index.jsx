import React, { PureComponent } from 'react';
import axios from 'axios';
import baseUrl from '../../../constants/backend-url';
import Card from '../../../components/Card';

class Upcoming extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: null,
    };
  }
  componentWillMount() {
    axios.get(`${baseUrl}/api/conference/upcoming`)
      .then(({ data }) => {
        this.setState({ data });
      });
  }
  render() {
    const { data } = this.state;
    if (!data) {
      return <div />;
    }
    const dataArray = Object.values(data);
    return (
      <div className="tabs-container">
        {dataArray.map(element => (
          <Card data={element} key={element.id} />
        ))}
      </div>
    );
  }
}

export default Upcoming;
