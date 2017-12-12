import React, { Component } from 'react';
import { connect } from 'react-redux';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { bindActionCreators } from 'redux';

import TalksTable from '../Talks/TalksTable/TalksTable';
import loadData from '../../actions/load';
import action from '../../constants/actions-types';
import { talk } from '../../constants/backend-url';

class MyTalks extends Component {
  constructor(props) {
    super(props);
    this.state = {
      talks: [],
      sorting: {
        title: '',
        conferenceName: '',
        eventDate: '',
        cfpDate: '',
        notificationDue: '',
        status: '',
      },
    };
  }

  componentDidMount() {
    const { LOAD } = action;
    axios.get(talk)
      .then(({ data }) => {
        this.props.load(LOAD, data);
        this.setState({ talks: data });
      });
  }

  sortTalks = ({ target }) => {
    const { classList, tagName, dataset: { name } } = target;
    const currentEl = classList.value;
    if (tagName === 'TH' && name) {
      if (currentEl.indexOf('table-header__item_active') !== -1) {
        classList.toggle('desc');
      } else {
        const prevActive = document.querySelector('.table-header__item_active');
        if (prevActive) {
          document
            .querySelector('.table-header__item_active')
            .classList
            .remove('table-header__item_active');
        }
        target.classList.add('table-header__item_active');
      }

      const { ASC, SORT_ALL_TALKS } = action;
      const { [name]: sortField } = this.state.sorting;
      const { load, talks } = this.props;
      const value = sortField === ASC ? '' : ASC;
      load(SORT_ALL_TALKS, { talks, direction: value, field: name });
      this.setState(prevState => ({
        sorting: {
          ...prevState.sorting,
          [name]: value,
        },
      }));
    }
  };

  render() {
    const { talks } = this.state;
    const { columns, onClick } = this.props;
    const className = 'my-talks-container';
    return (
      <div className="tabs-container">
        <TalksTable
          styleClass={className}
          listOfTalks={talks}
          columns={columns}
          onClick={onClick}
          sortTalks={this.sortTalks}
        />
      </div>
    );
  }
}

MyTalks.propTypes = {
  load: PropTypes.func.isRequired,
  talks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  columns: PropTypes.arrayOf(PropTypes.string),
  onClick: PropTypes.func.isRequired,
};

MyTalks.defaultProps = {
  columns: [
    'id',
    'title',
    'conferenceName',
    'eventDate',
    'cfpDate',
    'notificationDue',
    'status',
  ],
};

const mapStateToProps = ({ talks }) => (
  {
    talks,
  });

const mapDispatchToProps = dispatch => ({
  load: bindActionCreators(
    loadData, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(MyTalks);
