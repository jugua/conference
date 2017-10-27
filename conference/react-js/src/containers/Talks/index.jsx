import React, { Component } from 'react';
import { connect } from 'react-redux';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { bindActionCreators } from 'redux';

import FilterForm from './FilterForm';
import DisplayTalks from './DisplayTalks';
import TalksHeader from '../../components/Talks/TalksHeader/TalksHeader';

import action from '../../constants/actions-types';
import { topics, talk } from '../../constants/backend-url';
import loadData from '../../actions/load';

class Talks extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listOfTalks: [],
      filter: { topic: '', status: '' },
      listOfTopics: [],
      speaker: '',
      title: '',
      topic: '',
      status: '',
      comments: '',
    };
  }

  componentDidMount() {
    const { LOAD } = action;
    axios.get(topics)
      .then(({ data }) => {
        this.setState({ listOfTopics: data });
      });

    axios.get(talk)
      .then(({ data }) => {
        this.props.load(LOAD, data);
        this.setState({ listOfTalks: data });
      });
  }

  onChangeFilter = ({ target }) => {
    this.setState(prevState => ({
      filter: {
        ...prevState.filter,
        [target.name]: target.value,
      },
    }));
  };

  handleResetFiltersClick = () => {
    this.setState({
      filter: { topic: '', status: '' },
    }, this.doFilter);
  };

  handleFilterClick = () => {
    this.doFilter();
  };

  doFilter = () => {
    const { filter, listOfTalks } = this.state;
    const { APPLY_FILTERS } = action;
    this.props.load(APPLY_FILTERS, { filter, listOfTalks });
  };

  sortTalks = ({ target: { tagName, dataset: { name } } }) => {
    if (tagName === 'SPAN' && name) {
      const { ASC, SORT_USER_TALKS, SORT_ALL_TALKS } = action;
      const { [name]: sortField } = this.state;
      const { load, sort, talks, userTalks } = this.props;
      const value = sortField || ASC;
      const listForSort = sort === SORT_USER_TALKS ? userTalks : talks;
      const actionType = sort === SORT_USER_TALKS ?
        SORT_USER_TALKS : SORT_ALL_TALKS;
      load(actionType, { talks: listForSort, direction: value, field: name });
      this.setState({ [name]: value });
    }
  };

  render() {
    const { listOfTopics } = this.state;
    const { talks, columns, userTalks, sort } = this.props;
    const talksList = sort === 'talks' ? talks : userTalks;
    return (

      <div className="tabs-container">
        <div className="talks">
          <div className="talks__header">
            <a className="btn talks__button">export to excel </a>
          </div>
          <FilterForm
            topics={listOfTopics}
            onChangeFilter={this.onChangeFilter}
            handleFilterClick={this.handleFilterClick}
            handleResetFiltersClick={this.handleResetFiltersClick}
          />
          <div
            className="data-table"
          >
            <TalksHeader columns={columns} sortTalks={this.sortTalks} />
            <DisplayTalks talk={talksList} columns={columns} />
          </div>
          <div className="pagination">
            <div className="pagination__left-side">
              <div className="pagination__item-wrapper">
                <div className="pagination__item pagination__item_fast-back" />
                <div className="pagination__item pagination__item_back" />
                <div className="pagination__item pagination__item_current">
                  1
                </div>
                <div className="pagination__item pagination__item_forward" />
                <div className="pagination__item
                  pagination__item_fast-forward"
                />
              </div>
              <select className="pagination__select">
                <option defaultValue="" />
                <option value="5">5</option>
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
              </select>
              <div className="pagination__per-page">
                Items per page
              </div>
            </div>
            <div className="pagination__right-side">
              <p className="pagination__navi">1 - 4 of 4 items</p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Talks.propTypes = {
  load: PropTypes.func.isRequired,
  talks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  userTalks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  columns: PropTypes.arrayOf(PropTypes.string),
  sort: PropTypes.string,
};

Talks.defaultProps = {
  columns: [
    'id',
    'name',
    'title',
    'topic',
    'status',
    'comment',
  ],
  sort: 'talks',
};

const mapStateToProps = ({ talks, userTalks }) => (
  { talks,
    userTalks,
  });

const mapDispatchToProps = dispatch => ({
  load: bindActionCreators(
    loadData, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(Talks);
