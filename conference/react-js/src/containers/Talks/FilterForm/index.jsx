import React, { PureComponent } from 'react';
import { PropTypes } from 'prop-types';
import InputBlock from '../../../components/InputBlock/InputBlock';

class FilterForm extends PureComponent {
  setTopics = data => (data.map(({ id, name }) =>
    (<option key={id}>{name}</option>),
  ));

  setStatus = data => (data.map(({ id, status }) =>
    (<option key={id}>{status}</option>),
  ));

  render() {
    const { status, topics, onChangeFilter, handleFilterClick,
      handleResetFiltersClick } = this.props;
    return (
      <div className="my-talk-settings">
        <form className="my-talk-settings__filters">
          <div className="my-talk-settings__select-wrapper">
            <InputBlock
              label="Speaker"
              id="my-talk-name"
              labelClass="form-label my-talk-settings__label"
              name="name"
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
              htmlFor="my-talk-topic"
              className="form-label my-talk-settings__label"
            >Topic
            </label>
            <select
              name="topic"
              id="my-talk-topic"
              className="my-talk-settings__select"
              onBlur={onChangeFilter}
            >
              <option defaultValue="" />
              {this.setTopics(topics)}
            </select>
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
  status: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  topics: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  onChangeFilter: PropTypes.func.isRequired,
  handleFilterClick: PropTypes.func.isRequired,
  handleResetFiltersClick: PropTypes.func.isRequired,
};

export default FilterForm;
