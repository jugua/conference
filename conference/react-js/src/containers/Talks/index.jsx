import React, { Component } from 'react';
import { connect } from 'react-redux';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { bindActionCreators } from 'redux';

import FilterForm from './FilterForm';
import TalksTable from '../../components/Talks/TalksTable/TalksTable';
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
      quantityTalks: 20,
      quantityAllPages: 0,
    };
  }

  componentDidMount() {
    const { LOAD } = action;
    axios.get(topics)
      .then(({ data }) => {
        this.setState({ listOfTopics: data });
      });

    const url = this.props.url || talk;
    axios.get(url)
      .then(({ data }) => {
        this.props.load(LOAD, data);
        this.setState({ listOfTalks: data });
        this.doFilter();
        const countPages = Math.ceil(data.length / this.state.quantityTalks);
        this.setState({
          quantityAllPages: data.length,
          maxPages: countPages,
        });
      }).catch(() => {
        this.doFilter();
      },
      );
  }

  onChangeFilter = ({ target }) => {
    this.setState(prevState => ({
      filter: {
        ...prevState.filter,
        [target.name]: target.value,
      },
    }));
  };

  onChangeQuantityTalks = ({ target: { value } }) => {
    const { listOfTalks: { length } } = this.state;
    this.setState({
      quantityTalks: Number(value),
      currentPage: 1,
      maxPages: Math.ceil(length / Number(value)),
    },
    this.doFilter);
  };

  onChangeCurrentPage = ({ target: { classList: { value } } }) => {
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
    } else if (this.state.currentPage < this.state.maxPages) {
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

  fastForwardPages = ({ target: { classList: { value } } }) => {
    const { maxPages } = this.state;
    if (value.indexOf('forward') > -1) {
      this.setState({ currentPage: maxPages }, this.doFilter);
    } else {
      this.setState({ currentPage: 1 }, this.doFilter);
    }
  };

  doFilter = () => {
    const {
      filter,
      listOfTalks,
      currentPage,
      quantityTalks,
    } = this.state;
    const { APPLY_FILTERS } = action;
    this.props.load(APPLY_FILTERS,
      {
        filter,
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

  // sortTalks = ({ target: { tagName, dataset: { name } } }) => {
  //   if (tagName === 'TH' && name) {
  //     const { ASC, SORT_ALL_TALKS } = action;
  //     const { [name]: sortField } = this.state;
  //     const { load, talks } = this.props;
  //     const value = sortField === ASC ? '' : ASC;
  //     load(SORT_ALL_TALKS, { talks, direction: value, field: name });
  //     this.setState({ [name]: value });
  //   }
  // };

  render() {
    const {
      listOfTalks,
      listOfTopics,
      quantityTalks,
      currentPage,
      quantityAllPages,
    } = this.state;
    const { columns, onClick } = this.props;
    return (
      <div
        className="talks tabs-container"
        role="menu"
        tabIndex="0"
      >
        <div className="talks__header">
          <a className="btn talks__button">export to excel </a>
        </div>
        <FilterForm
          topics={listOfTopics}
          onChangeFilter={this.onChangeFilter}
          handleFilterClick={this.handleFilterClick}
          handleResetFiltersClick={this.handleResetFiltersClick}
        />
        <TalksTable
          listOfTalks={listOfTalks}
          columns={columns}
          onClick={onClick}
        />
        <Pagination
          quantityAllPages={quantityAllPages}
          currentPage={currentPage}
          onChangeCurrentPage={this.onChangeCurrentPage}
          quantityTalks={quantityTalks}
          onChangeQuantityTalks={this.onChangeQuantityTalks}
          fastForwardPages={this.fastForwardPages}
        />
      </div>
    );
  }
}

Talks.propTypes = {
  load: PropTypes.func.isRequired,
  // talks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  columns: PropTypes.arrayOf(PropTypes.string),
  onClick: PropTypes.func.isRequired,
  url: PropTypes.string,
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
  url: null,
};

const mapStateToProps = ({ talks, userTalks }) => (
  {
    talks,
    userTalks,
  });

const mapDispatchToProps = dispatch => ({
  load: bindActionCreators(
    loadData, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(Talks);
