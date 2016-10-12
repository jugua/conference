var express    = require('express');
var app        = express();
var path       = require('path');
var bodyParser = require('body-parser');
var mongoose   = require('mongoose');
var User      = require('./model/user');
var auth      = require('./core/auth');

module.exports = (PORT) => {


  var router = express.Router();

  mongoose.connect('mongodb://mey:computers@ds015574.mlab.com:15574/mey_test');

  app.use(express.static(path.join(__dirname, './dist-dev')));
  app.use("/", express.static(path.join(__dirname, './dist-dev')));
  app.use(bodyParser.urlencoded({extended: true}));
  app.use(bodyParser.json());

  router.use(function (req, res, next) {
    console.log('Something is happening.');
    next();
  });


// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api

  router.route('/login')
    .post(auth);

  router.route('/users')
    .post(function (req, res) {

      var user = new User();      // create a new instance of the User
      user.mail = req.body.mail;
      user.password = req.body.password;
      // save the bear and check for errors
      user.save(function (err) {
        if (err)
          res.send(err);

        res.send({message: 'user created'});
      });

    })
    .get(function (req, res) {
      User.find(function (err, current) {
        if (err)
          res.send(err);

        res.json(current);
      });
    });
///////////////////////////////////// first roudes ends/////////////////////////

  router.route('/users/:user_id')

  // get the user  with that id (accessed at GET http://localhost:8089/api/users/:user_id)
    .get(function (req, res) {
      User.findById(req.params.user_id, function (err, current) {
        if (err)
          res.send(err);
        res.json(current);
      });
    });


  app.use('/api', router);

  app.get("/*", function (req, res, next) {

    next("Could not find page");
  });


// START THE SERVER
// =============================================================================
  app.listen(PORT);
  console.log('Magic happens on port ' + PORT);
}