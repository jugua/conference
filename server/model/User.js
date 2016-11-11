'use strict';

const mongoose = require('mongoose');
const Schema = mongoose.Schema;

var UserSchema = new Schema({
  mail: {
    type: String,
    unique: true,
    required: true
  },
  password: {
    type: String,
    required: true
  },
  fname: {
    type: String,
    required: true
  },
  lname: {
    type: String,
    required: true
  },
  bio: {
    type: String
  },
  job: {
    type: String
  },
  past: {
    type: String
  },
  photo: {
    type: String
  },
  linkedin: {
    type: String
  },
  twitter: {
    type: String
  },
  facebook: {
    type: String
  },
  blog: {
    type: String
  },
  info: {
    type: String
  },
  roles: {
    type: Array
  },
  hash: {
    type: Array
  },
  talks: [{
    title: {
      type: String
    },
    description: {
      type: String
    },
    topic: {
      type: String
    },
    lang: {
      type: String
    },
    level: {
      type: String
    },
    addon: {
      type: String
    },
    status: {
      type: String
    },
    date: {
      type: Number
    },

  }]
});

module.exports = mongoose.model('User', UserSchema);