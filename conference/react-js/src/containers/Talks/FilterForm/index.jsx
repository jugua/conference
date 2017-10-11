import React, { PureComponent } from 'react';
import { PropTypes } from 'prop-types';
import { connect } from 'react-redux';
import axios from 'axios';
import { bindActionCreators } from 'redux';
import load from '../../../actions/load';
// import applyFilters from '../../../actions/filter';
import Calendar from '../../../components/Talks/Calendar';
import { topics } from '../../../constants/backend-url';

class FilterForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { listOfTopics: '' };
  }

  componentDidMount() {
    axios.get(topics)
      .then(({ data }) => {
        this.setState({ listOfTopics: data });
        console.log(data, 'axios get');
      })
      .catch(({ response: { listOfTopics } }) => (
        console.log(listOfTopics)
      ));
  }

  setTopics = listOfTopics => (
    Object.values(listOfTopics).map(({ name }) => (
      <option>{name}</option>),
    ));

  handleFilterClick = (e) => {
    e.preventDefault();
    this.doFilter();
  };

  handleResetFiltersClick = () => {
    this.state = {};
    this.doFilter();
  };

  doFilter = () => {
    // this.props.dispatch(applyFilters(this.state));
    const filter = 'New';
    console.log('doFilter', filter, this.state.listOfTopics);
    const { talks: list } = this.props;
    this.props.load('filter', { filter, list });
  };

  changeStatusFilter = (e) => {
    this.state.status = e.target.value;
  };

  render() {
    console.log(this.state, 'render');
    const { listOfTopics } = this.state;
    return (
      <div className="my-talk-settings">
        <form className="my-talk-settings__filters">
          <p className="my-talk-settings__title">filter by:</p>
          <div className="my-talk-settings__select-wrapper">
            <label
              htmlFor="my-talk-status"
              className="form-label my-talk-settings__label
              my-talk-settings__label_status"
            >Status
            </label>
            <select
              id="my-talk-status"
              className="my-talk-settings__select"
              onChange={this.changeStatusFilter}
            >
              <option />
              <option>New</option>
              <option>In Progress</option>
              <option>Approved</option>
              <option>Rejected</option>
            </select>
          </div>
          <div className="my-talk-settings__select-wrapper
            my-talk-settings__select-wrapper_topic"
          >
            <label
              htmlFor="my-talk-topic"
              className="form-label
              my-talk-settings__label "
            >Topic</label>
            <select
              id="my-talk-topic"
              className="my-talk-settings__select
              my-talk-settings__select_topic"
            >
              {this.setTopics(listOfTopics)}
            </select>
          </div>
          <div className="my-talk-settings__date-wrapper">
            <div className="form-label my-talk-settings__date-label">
              submitted date
            </div>
            <div className="calendars-wrapper">
              <Calendar />
              <Calendar />
            </div>
          </div>
          <div className="my-talk-settings__button-wrapper">
            <input
              type="button"
              className="my-talk-settings__button"
              value="apply"
              onClick={this.handleFilterClick}
            />
            <input
              type="reset"
              className="my-talk-settings__button"
              value="reset"
              onClick={this.handleResetFiltersClick}
            />
          </div>
        </form>
      </div>
    );
  }
}

FilterForm.propTypes = {
  load: PropTypes.func.isRequired,
  talks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
};

// mapStateToProps = state => (
//
// )

const mapDispatchToProps = dispatch => ({

  load: bindActionCreators(
    load, dispatch),
});

const mapStateToProps = state => ({
  talks: state.talks,
});

export default connect(mapStateToProps, mapDispatchToProps)(FilterForm);
