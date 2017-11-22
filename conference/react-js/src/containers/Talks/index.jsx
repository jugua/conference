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
import { talk, talksStatus } from '../../constants/backend-url';

class Talks extends Component {
  constructor(props) {
    super(props);
    this.state = {
      talks: [],
      filter: { conferenceName: '', title: '', status: '' },
      speaker: '',
      status: [],
      comments: '',
      currentPage: 1,
      quantityTalks: 20,
      quantityAllPages: 0,
    };
  }

  componentDidMount() {
    const { LOAD } = action;
    axios.get(talksStatus)
      .then(({ data }) => {
        this.setState({ status: data });
      });

    const url = this.props.url || talk;
    axios.get(url)
      .then(({ data }) => {
        this.props.load(LOAD, data);
        this.setState({ talks: data });
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
    const { talks: { length } } = this.state;
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
      talks,
      currentPage,
      quantityTalks,
    } = this.state;
    const { APPLY_FILTERS } = action;
    this.props.load(APPLY_FILTERS,
      {
        filter,
        talks,
        page: currentPage,
        quantity: quantityTalks,
      });
  };

  handleResetFiltersClick = () => {
    this.setState({
      filter: { conferenceName: '', title: '', status: '' },
    }, this.doFilter);
  };

  handleFilterClick = () => {
    this.doFilter();
  };

  sortTalks = ({ target }) => {
    const { classList, tagName, dataset: { name } } = target;
    const currentEl = classList.value;
    if (tagName === 'TH' && name) {
      if (currentEl.indexOf('table-header__item_active') !== -1) {
        classList.toggle('desc');
      } else {
        const prevActive = document.querySelector('.table-header__item_active');
        if (prevActive) {
          document.querySelector('.table-header__item_active').classList.remove('table-header__item_active');
        }
        target.classList.add('table-header__item_active');
      }

      const { ASC, SORT_ALL_TALKS } = action;
      const { [name]: sortField } = this.state;
      const { load, talks } = this.props;
      const value = sortField === ASC ? '' : ASC;
      load(SORT_ALL_TALKS, { talks, direction: value, field: name });
      this.setState({ [name]: value });
    }
  };

  render() {
    const {
      status,
      quantityTalks,
      currentPage,
      quantityAllPages,
    } = this.state;
    const { talks, columns, onClick } = this.props;
    return (
      <div
        className="talks tabs-container"
        role="menu"
        tabIndex="0"
      >
        <FilterForm
          status={status}
          onChangeFilter={this.onChangeFilter}
          handleFilterClick={this.handleFilterClick}
          handleResetFiltersClick={this.handleResetFiltersClick}
        />
        <TalksTable
          listOfTalks={talks}
          columns={columns}
          onClick={onClick}
          sortTalks={this.sortTalks}
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
  talks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  columns: PropTypes.arrayOf(PropTypes.string),
  onClick: PropTypes.func,
  url: PropTypes.string,
};

Talks.defaultProps = {
  columns: [
    'id',
    'name',
    'title',
    'topic',
    'conference',
    'status',
    'comment',
  ],
  url: null,
  onClick() {},
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
