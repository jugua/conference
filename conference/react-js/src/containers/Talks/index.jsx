import React, { Component } from 'react';
import { connect } from 'react-redux';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { bindActionCreators } from 'redux';
import FilterForm from './FilterForm';
import DisplayTalks from './DisplayTalks';
import TalksHeader from '../../components/Talks/TalksHeader/TalksHeader';
import Pagination from './Pagination/Pagination';
import loadData from '../../actions/load';
import action from '../../constants/actions-types';
import { topics, talk } from '../../constants/backend-url';

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
      currentPage: 1,
      quantityTalks: '20',
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
        this.doFilter();
      }).catch(() => {
        this.doFilter();
      },
      );
  }

  onChangeFilter = ({ target: { name, value } }) => {
    this.setState(prevState => ({
      filter: {
        ...prevState.filter,
        [name]: value,
      },
    }));
  };

  onChangeQuantityTalks = ({ target: { value } }) => {
    this.setState({ quantityTalks: value },
      this.doFilter);
  };

  onChangecurrentPage = ({ target: { classList: { value } } }) => {
    if (value.indexOf('back') > -1) {
      if (this.state.currentPage > 1) {
        this.setState(prevValue => (
          {
            ...prevValue,
            currentPage: Number(prevValue.currentPage) - 1,
          }
        ),
        this.doFilter,
        );
      }
    } else {
      this.setState(prevValue => (
        {
          ...prevValue,
          currentPage: Number(prevValue.currentPage) + 1,
        }
      ),
      this.doFilter,
      );
    }
  };

  doFilter = () => {
    const { filter, listOfTalks, currentPage, quantityTalks } = this.state;
    const { APPLY_FILTERS } = action;
    this.props.load(APPLY_FILTERS,
      { filter,
        listOfTalks,
        page: currentPage,
        quantity: quantityTalks,
      });
  };

  handleResetFiltersClick = () => {
    this.setState({
      filter: { topic: '', status: '' },
    }, this.doFilter);
  };

  handleFilterClick = () => {
    this.doFilter();
  };

  sortTalks = ({ target: { tagName, dataset: { name } } }) => {
    if (tagName === 'SPAN' && name !== undefined) {
      const { ASC, SORT_USER_TALKS, SORT_ALL_TALKS } = action;
      const { [name]: sortField } = this.state;
      const { load, sort, talks, userTalks } = this.props;
      const value = sortField === '' ? ASC : '';
      const listForSort = sort === SORT_USER_TALKS ? userTalks : talks;
      const actionType = sort === SORT_USER_TALKS ?
        SORT_USER_TALKS : SORT_ALL_TALKS;
      load(actionType, { talks: listForSort, direction: value, field: name });
      this.setState({ [name]: value });
    }
  };

  render() {
    const { listOfTopics, quantityTalks, currentPage } = this.state;
    const { talks, coloms, userTalks, sort } = this.props;
    const { SORT_ALL_TALKS } = action;
    const talksList = sort === SORT_ALL_TALKS ? talks : userTalks;
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
            <TalksHeader coloms={coloms} sortTalks={this.sortTalks} />
            <DisplayTalks talk={talksList} coloms={coloms} />
          </div>
          <Pagination
            currentPage={currentPage}
            onChangeCurrentPage={this.onChangecurrentPage}
            quantityTalks={quantityTalks}
            onChangeQuantityTalks={this.onChangeQuantityTalks}
          />
        </div>
      </div>
    );
  }
}

Talks.propTypes = {
  load: PropTypes.func.isRequired,
  talks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  userTalks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  coloms: PropTypes.arrayOf(PropTypes.string),
  sort: PropTypes.string,
};

Talks.defaultProps = {
  coloms: [
    'id',
    'name',
    'title',
    'topic',
    'status',
    'comment',
  ],
  sort: action.SORT_ALL_TALKS,
};

function mapStateToProps(state) {
  return {
    talks: state.talks,
    userTalks: state.userTalks,
  };
}

const mapDispatchToProps = dispatch => ({

  load: bindActionCreators(
    loadData, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(Talks);
