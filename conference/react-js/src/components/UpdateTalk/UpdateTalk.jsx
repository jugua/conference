import React from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import ImageEdit from 'material-ui/svg-icons/image/edit';
import AttachFile from 'material-ui/svg-icons/editor/attach-file';
import { cyan500 } from 'material-ui/styles/colors';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';

const buttonStyle = {
  background: '#B22746',
  color: '#FFF',
};

function UpdateTalk() {
  return (
    <div className="update-talk_wrapper">

      <div className="update-talk__title">
        <TextField
          floatingLabelText="Title"
          defaultValue="Why Java sucks"
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
        />
      </div>

      <TextField
        floatingLabelText="Description"
        defaultValue="Lorem ipsum dolor sit amet, consectetur adipisicing elit,
        sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
        enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
        aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit
        in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur
        sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est laborum."
        multiLine
        rows={2}
        fullWidth
      />

      <div className="select-field_wrapper">
        <SelectField
          floatingLabelText="Topic"
          value={1}
          autoWidth
          selectedMenuItemStyle={{ color: cyan500 }}
        >
          <MenuItem value={1} primaryText="Auto width" />
          <MenuItem value={2} primaryText="Every Night" />
          <MenuItem value={3} primaryText="Weeknights" />
          <MenuItem value={4} primaryText="Weekends" />
          <MenuItem value={5} primaryText="Weekly" />
        </SelectField>

        <SelectField
          floatingLabelText="Topic"
          value={2}
          autoWidth
          selectedMenuItemStyle={{ color: cyan500 }}
        >
          <MenuItem value={1} primaryText="Auto width" />
          <MenuItem value={2} primaryText="Every Night" />
          <MenuItem value={3} primaryText="Weeknights" />
          <MenuItem value={4} primaryText="Weekends" />
          <MenuItem value={5} primaryText="Weekly" />
        </SelectField>

        <SelectField
          floatingLabelText="Topic"
          value={3}
          autoWidth
          selectedMenuItemStyle={{ color: cyan500 }}
        >
          <MenuItem value={1} primaryText="Auto width" />
          <MenuItem value={2} primaryText="Every Night" />
          <MenuItem value={3} primaryText="Weeknights" />
          <MenuItem value={4} primaryText="Weekends" />
          <MenuItem value={5} primaryText="Weekly" />
        </SelectField>

        <SelectField
          floatingLabelText="Topic"
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
        defaultValue="Lorem ipsum dolor sit amet, consectetur adipisicing elit,
        sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
        enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
        aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit
        in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur
        sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est laborum."
        multiLine
        rows={2}
        fullWidth
      />

    </div>
  );
}

export default UpdateTalk;
