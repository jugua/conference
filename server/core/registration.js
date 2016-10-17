let User = require('./../model/User');

function registration(req, res) {
  let token;
  if (req.body.mail
    && req.body.password
    && req.body.fname
    && req.body.lname) {
    User.findOne({
        mail: req.body.mail
      },
      (err, user) => {
        if (user) {
          res.status(409).json({error: 'email_already_exists'});
          return;
        } else {
          let user = new User();
          token = user._id + 123;    // create a new instance of the User
          user.mail = req.body.mail;
          user.password = req.body.password;
          user.roles.push('s');

          user.hash.push(token);
          user.save((err) => {
            if (err) {
              res.send(err);
            } else {
              res.send({token});
            }
          });
        }
      });
  } else {
    res.status(400).send({message: 'empty_fields'});
  }
}

module.exports = registration;