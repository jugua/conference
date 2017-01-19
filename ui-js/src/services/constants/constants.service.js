
function Constants() {
  // validation constants
  const email = new RegExp('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$', 'i');
  const password = new RegExp('\\S+\\s*');

  // default ava path
  const ava = 'assets/img/default_ava.jpg';


  return {
    email,
    password,
    ava,
  };
}

export default Constants;