import React, { PureComponent } from 'react';
import { PropTypes } from 'prop-types';

class FilterForm extends PureComponent {
  setTopics = listOfTopics => (
    listOfTopics.map(({ name, id }) => (
      <option key={id}>{name}</option>),
    ));

  render() {
    const { topics,
      onChangeFilter,
      handleFilterClick,
      handleResetFiltersClick } = this.props;
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
              name="status"
              id="my-talk-status"
              className="my-talk-settings__select"
              onChange={onChangeFilter}
            >
              <option
                defaultValue=""
              />
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
              name="topic"
              id="my-talk-topic"
              className="my-talk-settings__select
              my-talk-settings__select_topic"
              onChange={onChangeFilter}
            >
              <option
                defaultValue=""
              />
              {this.setTopics(topics)}
            </select>
          </div>
          <div className="my-talk-settings__button-wrapper">
            <input
              type="button"
              className="my-talk-settings__button"
              value="apply"
              onClick={handleFilterClick}
            />
            <input
              type="reset"
              className="my-talk-settings__button"
              value="reset"
              onClick={handleResetFiltersClick}
            />
          </div>
        </form>
      </div>
    );
  }
}

FilterForm.propTypes = {
  topics: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  onChangeFilter: PropTypes.func.isRequired,
  handleFilterClick: PropTypes.func.isRequired,
  handleResetFiltersClick: PropTypes.func.isRequired,
};

export default FilterForm;
