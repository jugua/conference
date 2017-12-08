import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

import RaisedButton from 'material-ui/RaisedButton';
import AttachImg from 'material-ui/svg-icons/editor/attach-file';

class AttachFile extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      fileName: '',
      file: null,
    };
  }

  componentDidMount() {
    this.getTalkFile(this.props.talk.id);
  }

  getTalkFile = (id) => {
    axios.get(`/talk/${id}/takeFileName`)
      .then((file) => {
        this.setState(prevState => ({
          ...prevState,
          file,
        }));
      });
  };

  getName = (e) => {
    e.preventDefault();

    const file = e.target.files[0];
    this.setState({
      fileName: file.name,
    });
  };

  uploadFile = (e) => {
    e.preventDefault();

    // const { id } = this.props.talk;
    const attachedFileBtn = document.querySelector('#attached-file');
    const attachedFile = attachedFileBtn.files[0];

    const data = new FormData();
    data.append('file', attachedFile);
    console.log(data);
    // if (attachedFileBtn.files.length > 0) {
    //   axios.post(`/talk/${id}/uploadFile`, data)
    //     .then((res) => {
    //       console.log(res);
    //     });
    // }
  };

  render() {
    const { fileName } = this.state;

    return (
      <div className="attach-file_wrapper">
        <RaisedButton
          containerElement="label"
          label="Attach"
          icon={<AttachImg />}
          primary
        >
          <input
            type="file"
            id="attached-file"
            onChange={this.getName}
          />
        </RaisedButton>
        <span>
          {fileName}
        </span>
        <RaisedButton
          label="Save"
          primary
          onClick={this.uploadFile}
        />
      </div>
    );
  }
}

AttachFile.propTypes = {
  talk: PropTypes.shape({
    id: PropTypes.number.isRequired,
  }).isRequired,
};

export default AttachFile;
