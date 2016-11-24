const User = require('./../model/User');

function logout(req, res) {
  if (req.headers.token) {
    User.findOne({
      hash: req.headers.token
    },
      (err, user) => {
        if (!user) {
          res.status(404).json({ error: 'session_not_found' });
          return;
        }
        user.hash = user.hash.filter(elem => elem !== req.headers.token);
        user.save();
        res.status(200).json({ result: 'logged_out_successfully' });
      });

  } else {
    res.status(404).json({ error: 'session_not_found' });
  }
}

module.exports = logout;