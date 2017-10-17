import React, { PureComponent } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
import Card from '../../components/Card';

class CardsList extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { data: [] };
  }
  componentDidMount() {
    const { url } = this.props;
    axios.get(url)
      .then(({ data }) => {
        this.setState({ data });
      });
  }

  setCards = data => (data.map(element => (
    <Card data={element} key={element.id} />),
  )
  );

  render() {
    const { data } = this.state;
    return (
      <div className="tabs-container">
        {this.setCards(data)}
      </div>
    );
  }
}

CardsList.propTypes = {
  url: PropTypes.string.isRequired,
};
export default CardsList;
