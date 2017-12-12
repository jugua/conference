import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

import RaisedButton from 'material-ui/RaisedButton';
import AttachImg from 'material-ui/svg-icons/editor/attach-file';
import Chip from 'material-ui/Chip';

class AttachFile extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      fileNameToSave: '',
    };
  }

  getName = (e) => {
    e.preventDefault();

    const file = e.target.files[0];
    this.setState({
      fileNameToSave: file.name,
    });
  };

  handleRequestDelete = () => {
    const file = document.querySelector('#attached-file');
    file.value = '';

    this.setState({
      fileNameToSave: '',
    });
  };

  uploadFile = (e) => {
    e.preventDefault();

    const { id } = this.props.talk;
    const attachedFileBtn = document.querySelector('#attached-file');
    const attachedFile = attachedFileBtn.files[0];
    console.log(attachedFile);

    const fileData = new FormData();
    fileData.append('file', attachedFile);
    console.log(fileData);

    if (attachedFileBtn.files.length > 0) {
      axios.post(`/talk/${id}/uploadFile`, fileData)
        .then((res) => {
          console.log(res);
          this.setState({
            file: attachedFile,
          });
        });
    }
  };

  render() {
    const { fileNameToSave } = this.state;
    const { fileName } = this.props;

    return (
      <div className="attach-file_wrapper">
        <form encType="multipart/form-data">
          <RaisedButton
            containerElement="label"
            label="Attach"
            icon={<AttachImg />}
            primary
          >
            <input
              type="file"
              name="file"
              id="attached-file"
              onChange={this.getName}
            />
          </RaisedButton>
          { fileNameToSave !== '' ?
            <Chip
              onRequestDelete={this.handleRequestDelete}
            >
              {fileNameToSave}
            </Chip>
            : null
          }
          <RaisedButton
            label="Save"
            primary
            onClick={this.uploadFile}
          />
        </form>
        <span> {fileName} </span>
      </div>
    );
  }
}

AttachFile.defaultProps = {
  fileName: '',
};

AttachFile.propTypes = {
  talk: PropTypes.shape({
    id: PropTypes.number.isRequired,
  }).isRequired,
  fileName: PropTypes.string,
};

export default AttachFile;
