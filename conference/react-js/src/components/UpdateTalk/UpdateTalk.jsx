import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import ImageEdit from 'material-ui/svg-icons/image/edit';
import AttachFile from 'material-ui/svg-icons/editor/attach-file';
import { cyan500 } from 'material-ui/styles/colors';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';

import getTopics from '../../actions/getTopics';
import getTypes from '../../actions/getTypes';
import getLanguages from '../../actions/getLanguages';

const buttonStyle = {
  background: '#B22746',
  color: '#fff',
};

class UpdateTalk extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      listOfTopics: [],
      listOfTypes: [],
      listOfLanguages: [],
      title: '',
      description: '',
      topic: '',
      type: '',
      lang: '',
      level: '',
      addon: '',
    };
  }

  componentDidMount() {
    getTopics().then(({ data }) => {
      this.setState({
        listOfTopics: data,
        topic: data[0].name,
      });
    });

    getTypes().then(({ data }) => {
      this.setState({
        listOfTypes: data,
        type: data[0].name,
      });
    });

    getLanguages().then(({ data }) => {
      this.setState({
        listOfLanguages: data,
        lang: data[0].name,
      });
    });
  }

  render() {
    const { talk } = this.props;
    const { listOfTopics } = this.state;
    const { listOfTypes } = this.state;
    const { listOfLanguages } = this.state;

    return (
      <div className="update-talk_wrapper">

        <div className="update-talk__title">
          <TextField
            floatingLabelText="Title"
            defaultValue={talk.title}
            style={{ flexGrow: 1 }}
          />

          <RaisedButton
            className="update-talk__button"
            label="File"
            icon={<AttachFile />}
            primary
          />

          <RaisedButton
            className="update-talk__button"
            label="Update"
            icon={<ImageEdit />}
            primary
          />

          <RaisedButton
            className="update-talk__button"
            label="Close"
            buttonStyle={buttonStyle}
            primary
          />
        </div>

        <TextField
          floatingLabelText="Description"
          defaultValue={talk.description}
          multiLine
          rows={2}
          fullWidth
        />

        <div className="select-field_wrapper">
          <SelectField
            floatingLabelText="Topic"
            value={this.state.topic}
            autoWidth
            selectedMenuItemStyle={{ color: cyan500 }}
          >
            {
              listOfTopics.map(({ id, name }) => (
                <MenuItem
                  value={name}
                  primaryText={name}
                  key={id}
                />),
              )
            }
          </SelectField>

          <SelectField
            floatingLabelText="Type"
            value={this.state.type}
            autoWidth
            selectedMenuItemStyle={{ color: cyan500 }}
          >
            {
              listOfTypes.map(({ id, name }) => (
                <MenuItem
                  value={name}
                  primaryText={name}
                  key={id}
                />),
              )
            }
          </SelectField>

          <SelectField
            floatingLabelText="Language"
            value={this.state.lang}
            autoWidth
            selectedMenuItemStyle={{ color: cyan500 }}
          >
            {
              listOfLanguages.map(({ id, name }) => (
                <MenuItem
                  value={name}
                  primaryText={name}
                  key={id}
                />),
              )
            }
          </SelectField>

          <SelectField
            floatingLabelText="Level"
            value={4}
            autoWidth
            selectedMenuItemStyle={{ color: cyan500 }}
          >
            <MenuItem value={1} primaryText="Auto width" />
            <MenuItem value={2} primaryText="Every Night" />
            <MenuItem value={3} primaryText="Weeknights" />
            <MenuItem value={4} primaryText="Weekends" />
            <MenuItem value={5} primaryText="Weekly" />
          </SelectField>
        </div>

        <TextField
          floatingLabelText="Additional info"
          defaultValue={talk.addon}
          multiLine
          rows={2}
          fullWidth
        />

      </div>
    );
  }
}

function mapStateToProps({ userTalks }) {
  return { userTalks };
}

UpdateTalk.propTypes = {
  talk: PropTypes.PropTypes.shape({
    title: PropTypes.string,
    description: PropTypes.string,
    // topic: PropTypes.string,
    // type: PropTypes.string,
    // lang: PropTypes.string,
    // level: PropTypes.string,
    addon: PropTypes.string,
  }).isRequired,
};

// function mapDispatchToProps(dispatch) {
//   return { edit: () => dispatch({}) }
// }

export default connect(mapStateToProps)(UpdateTalk);
