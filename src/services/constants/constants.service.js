
function Constants() {
  // validation constants
  const email = new RegExp('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,6}$', 'i');
  const password = new RegExp('\\S+\\s*');


  return {
    email,
    password
  };
}

export default Constants;