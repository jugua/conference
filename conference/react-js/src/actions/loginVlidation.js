import { emailPattern, passwordPattern } from '../constants/patterns';

const loginValidation = ({ email, password }) => (
  emailPattern.test(email) && passwordPattern.test(password)
);

export default loginValidation;
