
function Constants() {
  // regex validation
  const email = /^[a-z0-9_!#%&'/=?^`}~$*+{|-]+(\.[a-z0-9_!#%&'/=?^`}~$*+{|-]+)*@[a-z0-9.-]{1,63}\.[a-z]{2,6}$/i;
  const password = /^\S+\s*$/i;

  // default ava path
  const ava = 'assets/img/default_ava.jpg';

  return {
    email,
    password,
    ava,
  };
}

export default Constants;