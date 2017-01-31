const Menus = function Menus() {
  const menu = {
    s: [
      {
        link: 'tabs.myInfo',
        name: 'My Info'
      },
      {
        link: 'tabs.myTalks',
        name: 'My Talks'
      },
      {
        link: 'account',
        name: 'Settings'
      }
    ],
    o: [
      {
        link: 'talks',
        name: 'Talks'
      },
      {
        link: 'account',
        name: 'Settings'
      }
    ],
    a: [
      {
<<<<<<< HEAD
        link: 'account',
        name: 'Settings'
=======
        link: 'manageUsers',
        name: 'Manage Users'
>>>>>>> 7645df6feb0fa4d43feaae43dcf1331faa2b2c39
      }
    ]
  };

  return {
    getMenu: function getMenu(role) {
<<<<<<< HEAD
      const menuArr = [];
=======
      let menuArr = [];
>>>>>>> 7645df6feb0fa4d43feaae43dcf1331faa2b2c39


      if (!role || role.length === 0) {
        return false;
      }

      if (role.indexOf('s') !== -1) {
<<<<<<< HEAD
        menuArr.concat(menu.s);
      }

      if (role.indexOf('o') !== -1) {
        menuArr.concat(menu.o);
      }

      if (role.indexOf('a') !== -1) {
        menuArr.concat(menu.a);
=======
        menuArr = menuArr.concat(menu.s);
      }

      if (role.indexOf('o') !== -1) {
        menuArr = menuArr.concat(menu.o);
      }

      if (role.indexOf('a') !== -1) {
        menuArr = menuArr.concat(menu.a);
>>>>>>> 7645df6feb0fa4d43feaae43dcf1331faa2b2c39
      }

      return menuArr;
    },
    getTopics: () => ['JVM Languages and new programming paradigms',
      'Web development and Java Enterprise technologies',
      'Software engineering practices',
      'Architecture & Cloud',
      'BigData & NoSQL'
    ],
    getTypes: () => ['Regular Talk',
      'Lighting Talk',
      'Online Talk',
      'Hands-On-Lab'
    ],
    getLang: () => ['English',
      'Ukrainian',
      'Russian'
    ],
    getTalksLevels: () => ['Beginner',
      'Intermediate',
      'Advanced',
      'Expert'
    ],
    getStatus: () => ['New',
      'In Progress',
      'Approved',
      'Rejected'
    ]
  };
};

export default Menus;
