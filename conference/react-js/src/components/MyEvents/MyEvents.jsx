import React from 'react';
import MyEventsList from '../../containers/MyEventsList/MyEventsList';
import { myEvents } from '../../constants/backend-url';

const MyEvents = () => <MyEventsList url={myEvents} />;

export default MyEvents;
