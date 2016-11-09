const Menus = function Menus() {
  const menu = {
    s: [
      {
        link: 'account',
        name: 'My Account'
      },
      {
        link: 'tabs.myInfo',
        name: 'My Info'
      },
      {
        link: 'tabs.myTalks',
        name: 'My Talks'
      }
    ],
    o: [
      {
        link: 'oaccount',
        name: 'My Account'
      },
      {
        link: 'otalks',
        name: 'Talks'
      }
    ]
  };

  return {
    getMenu: function getMenu(role) {
      let menuRole;


      if (!role || role.length === 0) {
        return false;
      }

      if (role.indexOf('s') !== -1) {
        menuRole = 's';
      }

      if (role.indexOf('o') !== -1) {
        menuRole = 'o';
      }

      return menu[menuRole];
    },
    getTopics: ()=> {
      return ['JVM Languages and new programming paradigms',
        'Web development and Java Enterprise technologies',
        'Software engineering practices',
        'Architecture & Cloud',
        'BigData & NoSQL'
      ]
    },
    getTypes: ()=> {
      return ['Regular Talk',
        'Lighting Talk',
        'Online Talk',
        'Hands-On-Lab',
        'BigData & NoSQL'
      ]
    },
    getLang: ()=> {
      return ['English',
        'Ukrainian',
        'Russian'
      ]
    },
    getTalksLevels: ()=> {
      return ['Beginner',
        'Intermediate',
        'Advanced',
        'Expert'
      ]
    }
  };
};

export default Menus;
