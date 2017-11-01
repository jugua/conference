import React, { PureComponent } from 'react';
import axios from 'axios';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Card from '../../components/Card/Card';
import loadData from '../../actions/load';
import action from '../../constants/actions-types';

class CardsList extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { data: [] };
  }

  componentDidMount() {
    const { url } = this.props;
    axios.get(url)
      .then(({ data }) => { this.setState({ data }); });
  }

  setConference = (data) => {
    console.log(data, 'data');
    const { SET_CONFERENCE } = action;
    this.props.load(SET_CONFERENCE, data);
  }

  setCards = (data, id) => (data.map((element) => {
    const { SET_CONFERENCE } = action;
    this.props.load(SET_CONFERENCE, data);
    return (<Card
      data={element}
      key={element.id}
      id={id}
      setConference={this.setConference}
    />);
  }));

  render() {
    const { data } = this.state;
    const { user: { id } } = this.props;
    return (
      <div className="tabs-container">
        {this.setCards(data, id)}
      </div>
    );
  }
}

CardsList.propTypes = {
  url: PropTypes.string.isRequired,
  load: PropTypes.func.isRequired,
  user: PropTypes.shape({
    id: PropTypes.number,
    roles: PropTypes.arrayOf(PropTypes.string),
    fname: PropTypes.string,
  }).isRequired,
};

const mapStateToProps = user => user;

const mapDispatchToProps = dispatch => ({
  load: bindActionCreators(
    loadData, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(CardsList);
