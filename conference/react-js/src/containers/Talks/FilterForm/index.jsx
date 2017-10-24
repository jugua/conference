import React, { PureComponent } from 'react';
import { PropTypes } from 'prop-types';
import ReactInputSelect from 'react-input-select/lib/ReactInputSelect';

class FilterForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      topicValue: '',
      listOfTopics: [],
    };
  }

  componentWillReceiveProps(nextProps) {
    const { topics } = nextProps;
    const listOfTopics = [];
    topics.map(({ name }) => (
      listOfTopics.push(name)
    ),
    );
    this.setState({ listOfTopics });
  }

  onBlurTopic = (e) => {
    const name = 'topic';
    const value = e.target.value;
    this.props.onChangeFilter({ target: { name, value } });
  }

  updateTopicValue = (e) => {
    this.setState({ topicValue: e.target.value });
  }

  chooseTopic = (item) => {
    this.setState({ topicValue: item });
  }

  resetTopics = () => {
    this.props.handleResetFiltersClick();
    this.setState({ topicValue: '' });
  }

  render() {
    const { topicValue, listOfTopics } = this.state;
    const {
      onChangeFilter,
      handleFilterClick } = this.props;
    return (
      <div className="my-talk-settings">
        <form className="my-talk-settings__filters">
          <p className="my-talk-settings__title">filter by:</p>
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
            <ReactInputSelect
              containerClass="my-talk-settings__select
                my-talk-settings__select_topic-container"
              dropdownClass="my-talk-settings__select
                my-talk-settings__select_topic-drop"
              name="topic"
              containerId="my-talk-topic"
              inputClass="my-talk-settings__select
                my-talk-settings__select_topic"
              data={listOfTopics}
              onChange={this.updateTopicValue}
              value={topicValue}
              onOptionClick={this.chooseTopic}
              onBlur={this.onBlurTopic}
            />
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
              onClick={this.resetTopics}
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
