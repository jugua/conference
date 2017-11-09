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
      .then(({ data }) => {
        this.setState({ data });
      });
  }

  setConference = (data) => {
    const { SET_CONFERENCE } = action;
    this.props.load(SET_CONFERENCE, data);
  };

  setCards = (data, role) => (data.map(element =>
    (<Card
      data={element}
      key={element.id}
      role={role}
      setConference={this.setConference}
    />),
  ));

  render() {
    const { data } = this.state;
    const { user: { role } } = this.props;
    return (
      <div className="tabs-container">
        {this.setCards(data, role)}
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
