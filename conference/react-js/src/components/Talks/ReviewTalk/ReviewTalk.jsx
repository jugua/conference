import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import axios from 'axios';

class ReviewTalk extends Component {
  constructor(props) {
    super(props);
    this.state = {
      status: false,
    };
  }

  changeStatus = () => {
    axios.patch('/talk',
      { id: 1,
        status: 'PENDING' })
      .then(() => {
        console.log('success');
      });
  };

  render() {
    const { close } = this.props;
    return (
      <div className="tabs-container">
        <div className="review-talk__topic">
          JVM Languages and new programming paradigms
        </div>
        <div className="review-talk__type">
          Regular Talk:
        </div>
        <div className="review-talk__title">
          Why Java
        </div>
        <p className="review-talk__description">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore
          magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
          consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
          Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <p className="review-talk__language">
          Language: English
        </p>
        <p className="review-talk__level">
          Level: Begginer
        </p>
        <div className="review-talk__additional-info">
          <a className="additional-info__title link">
            Additional info
          </a>
          <p className="additional-info__text">
            Additional info Additional info Additional info Additional info
          </p>
        </div>
        <div className="review-talk__comments" />
        <div className="review-talk__button-group">
          <button className="review-talk__button" onClick={this.changeStatus}>Approve</button>
          <button className="review-talk__button review-talk__button_reject" onClick={this.changeStatus}>Reject</button>
          <button
            className="review-talk__button"
            onClick={close}
          >
            Cancel
          </button>
        </div>
      </div>
    );
  }
}

ReviewTalk.propTypes = {
  close: PropTypes.func.isRequired,
};

export default ReviewTalk;

// const ReviewTalk = ({ close }) => (
//   <div className="tabs-container">
//     <div className="review-talk__topic">
//       JVM Languages and new programming paradigms
//     </div>
//     <div className="review-talk__type">
//       Regular Talk:
//     </div>
//     <div className="review-talk__title">
//       Why Java
//     </div>
//     <p className="review-talk__description">
//       Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore
//       magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
//       consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
//       Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
//     </p>
//     <p className="review-talk__language">
//       Language: English
//     </p>
//     <p className="review-talk__level">
//       Level: Begginer
//     </p>
//     <div className="review-talk__additional-info">
//       <a className="additional-info__title link">
//         Additional info
//       </a>
//       <p className="additional-info__text">
//         Additional info Additional info Additional info Additional info
//       </p>
//     </div>
//     <div className="review-talk__comments" />
//     <div className="review-talk__button-group">
//       <button className="review-talk__button">Approve</button>
//       <button className="review-talk__button review-talk__button_reject">Reject</button>
//       <button
//         className="review-talk__button"
//         onClick={close}
//       >
//         Cancel
//       </button>
//     </div>
//   </div>
// );
//
// ReviewTalk.propTypes = {
//   close: PropTypes.func.isRequired,
// };
//
// export default ReviewTalk;
