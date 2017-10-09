const loginValidation = ({ email, password }) => {
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

  if (password.length < 6) return false;
  return emailPattern.test(email);
};

export default loginValidation;
