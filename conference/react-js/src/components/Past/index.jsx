import React from 'react';
import CardsList from '../../containers/CardsList';
import { past } from '../../constants/backend-url';

const Past = () => <CardsList url={past} />;

export default Past;

