// import React, { PureComponent } from 'react';
// import axios from 'axios';
// import TalksList from '../../../components/Talks/TalksList';
// import { talk } from '../../../constants/backend-url';
//
// class TalksObj extends PureComponent {
//   constructor(props) {
//     super(props);
//     this.state = { talkComponent: '' };
//   }
//   componentDidMount() {
//     axios.get(talk)
//       .then(resp => (console.log(resp)));
//   }
//
//   setTalks = talkComp => Object.values(talkComp)
//     .map((elem) => {
//       /* eslint-disable */
//      console.log(elem);
//       return (<TalksList talkComp={elem} key={elem._id} />)
//       /* eslint-enable  */
//     });
//
//   render() {
//     const { talkComp } = this.state;
//     return (
//       <div className="data-table__inner-wrapper">
//         {this.setTalks(talkComp)}
//       </div>
//     );
//   }
// }
//
// export default TalksObj;
