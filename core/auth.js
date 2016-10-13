var User      = require('../model/user');

var  auth = function auth(req, res) {
  if (req.body.password && req.body.mail) {
    User.findOne ({
        mail:req.body.mail,
        password:req.body.password},
      function(err, user ){
        res.json(user);
      });

  } else {
    res.json ('not ok');
  }
}

module.exports = auth;