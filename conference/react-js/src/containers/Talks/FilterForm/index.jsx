import React, { PureComponent } from 'react';
import { PropTypes } from 'prop-types';

class FilterForm extends PureComponent {
  setStatus = status => (status.map(element =>
    (<option key={status.indexOf(element)}>{element}</option>),
  ));

  render() {
    const { status, onChangeFilter, handleFilterClick,
      handleResetFiltersClick } = this.props;
    return (
      <div className="my-talk-settings">
        <form className="my-talk-settings__filters">
          <p className="my-talk-settings__title">filter by:</p>
          <div className="my-talk-settings__select-wrapper">
            <label
              htmlFor="my-talk-conference"
              className="form-label my-talk-settings__label"
            >Conference
            </label>
            <input
              type="text"
              name="conferenceName"
              id="my-talk-conference"
              className="my-talk-settings__select"
              onBlur={onChangeFilter}
            />
          </div>
          <div className="my-talk-settings__select-wrapper">
            <label
              htmlFor="my-talk-title"
              className="form-label my-talk-settings__label"
            >Title
            </label>
            <input
              type="text"
              name="title"
              id="my-talk-title"
              className="my-talk-settings__select"
              onBlur={onChangeFilter}
            />
          </div>
          <div className="my-talk-settings__select-wrapper">
            <label
              htmlFor="my-talk-status"
              className="form-label my-talk-settings__label"
            >Status
            </label>
            <select
              name="status"
              id="my-talk-status"
              className="my-talk-settings__select"
              onBlur={onChangeFilter}
            >
              <option defaultValue="" />
              {this.setStatus(status)}
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
  status: PropTypes.arrayOf(PropTypes.string).isRequired,
  onChangeFilter: PropTypes.func.isRequired,
  handleFilterClick: PropTypes.func.isRequired,
  handleResetFiltersClick: PropTypes.func.isRequired,
};

export default FilterForm;
