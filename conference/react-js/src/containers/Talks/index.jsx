import React, { Component } from 'react';
import { connect } from 'react-redux';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { bindActionCreators } from 'redux';
import FilterForm from './FilterForm';
import DisplayTalks from './DisplayTalks';
import loadData from '../../actions/load';
import action from '../../constants/actions-types';
import { topics, talk } from '../../constants/backend-url';

class Talks extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listOfTalks: [],
      filter: { topic: '', status: '' },
      listOfTopics: [] };
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

  onChangeFilter = ({ target: { name, value } }) => {
    this.setState(prevState => ({
      filter: {
        ...prevState.filter,
        [name]: value,
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

  render() {
    const { listOfTopics } = this.state;
    const { talks } = this.props;

    return (
      <div className="talks-container">
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
          <table className="data-table">
            <thead className="data-table__header-block">
              <tr className="table-header">
                <th className="table-header__item
                  table-header__item_check-talk"
                >
                  <input type="checkbox" />
                </th>
                <th className="table-header__item
                  table-header__item_speaker-talk"
                >
                  speaker
                </th>
                <th className="table-header__item
                  table-header__item_title-talk"
                >
                  title
                </th>
                <th className="table-header__item
                  table-header__item_topic-talk"
                >
                  topic
                </th>
                <th className="table-header__item table-header__item_date-talk">
                  submitted date
                </th>
                <th className="table-header__item
                  table-header__item_status-talk"
                >
                  status
                </th>
                <th className="table-header__item
                  table-header__item_comments-talk"
                >
                  organizer comments
                </th>
                <th className="table-header__item
                  table-header__item_assign-talk"
                >
                  assigned to
                </th>
                <th className="table-header__item table-header__scroll-fix" />
              </tr>
            </thead>
            <DisplayTalks talk={talks} />
          </table>
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
};

function mapStateToProps(state) {
  return {
    talks: state.talks,
  };
}

const mapDispatchToProps = dispatch => ({

  load: bindActionCreators(
    loadData, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(Talks);
