import React from 'react';
import baseUrl from '../../constants/backend-url';
import CardsList from '../../containers/CardsList';

const Upcoming = () => <CardsList url={`${baseUrl}/conference/upcoming`} />;

export default Upcoming;
