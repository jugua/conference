import React, { PureComponent } from 'react';
import { PropTypes } from 'prop-types';
import { connect } from 'react-redux';
import applyFilters from '../../../action';
import Calendar from '../../../components/Talks/Calendar';

class FilterForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {};
  }

  handleFilterClick = (e) => {
    e.preventDefault();
    this.doFilter();
  }

  handleResetFiltersClick = () => {
    this.state = {};
    this.doFilter();
  }

  doFilter = () => {
    this.props.dispatch(applyFilters(this.state));
  }

  changeStatusFilter = (e) => {
    this.state.status = e.target.value;
  }

  render() {
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
            />
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
  dispatch: PropTypes.func.isRequired,
};

export default connect()(FilterForm);
