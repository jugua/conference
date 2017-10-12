import React, { Component } from 'react';
import { connect } from 'react-redux';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { bindActionCreators } from 'redux';
import FilterForm from '../../containers/Talks/FilterForm';
import DisplayTalks from '../../containers/Talks/DisplayTalks';
import load from '../../actions/load';
import action from '../../constants/actions-types';
import { topics, talk } from '../../constants/backend-url';

class TalksRender extends Component {
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
        console.log(data);
        this.setState({ listOfTopics: data });
        console.log(data, 'axios get');
      })
      .catch(({ response: { listOfTopics } }) => (
        console.log(listOfTopics)
      ));

    axios.get(talk)
      .then(({ data }) => {
        this.props.load(LOAD, data);
        this.setState({ listOfTalks: data });
      })
      .catch(({ response: { data } }) => (
        console.log(data)
      ));
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

  changeStatusFilter = (e) => {
    this.state.status = e.target.value;
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
          <div className="data-table">
            <div className="table-header">
              <div className="table-header__item
                table-header__item_check-talk"
              >
                <input type="checkbox" />
              </div>
              <div className="table-header__item
                table-header__item_speaker-talk"
              >
                speaker
              </div>
              <div className="table-header__item
                table-header__item_title-talk"
              >
                title
              </div>
              <div className="table-header__item
                table-header__item_topic-talk"
              >
                topic
              </div>
              <div className="table-header__item table-header__item_date-talk">
                submitted date
              </div>
              <div className="table-header__item
                table-header__item_status-talk"
              >
                status
              </div>
              <div className="table-header__item
                table-header__item_comments-talk"
              >
                organizer comments
              </div>
              <div className="table-header__item
                table-header__item_assign-talk"
              >
                assigned to
              </div>
              <div className="table-header__item table-header__scroll-fix" />
            </div>
            <DisplayTalks talk={talks} />
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
                <div className="pagination__item pagination__item_fast-forward" />
              </div>
              <select className="pagination__select">
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
                <option selected value="50">50</option>
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

TalksRender.propTypes = {
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
    load, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(TalksRender);
