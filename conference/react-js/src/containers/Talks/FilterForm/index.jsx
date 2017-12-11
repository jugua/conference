import React, { PureComponent } from 'react';
import { PropTypes } from 'prop-types';
import InputBlock from '../../../components/InputBlock/InputBlock';

class FilterForm extends PureComponent {
  getOptions = data => (data.map(({ id, status }) =>
    (<option key={id}>{status}</option>),
  ));

  render() {
    const { status, onChangeFilter, handleFilterClick,
      handleResetFiltersClick } = this.props;
    return (
      <div className="my-talk-settings">
        <form className="my-talk-settings__filters">
          <p className="my-talk-settings__title">filter by:</p>
          <div className="my-talk-settings__select-wrapper">
            <InputBlock
              label="Conference"
              id="my-talk-conference"
              labelClass="form-label my-talk-settings__label"
              name="conferenceName"
              inputClass="my-talk-settings__select"
              onBlur={onChangeFilter}
            />
          </div>
          <div className="my-talk-settings__select-wrapper">
            <InputBlock
              label="Title"
              id="my-talk-title"
              labelClass="form-label my-talk-settings__label"
              name="title"
              inputClass="my-talk-settings__select"
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
              {this.getOptions(status)}
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
  status: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  onChangeFilter: PropTypes.func.isRequired,
  handleFilterClick: PropTypes.func.isRequired,
  handleResetFiltersClick: PropTypes.func.isRequired,
};

export default FilterForm;
