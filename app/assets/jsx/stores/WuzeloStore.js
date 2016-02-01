define(['dispatcher', 'events', 'constants', 'underscore'],
  function(AppDispatcher, EventEmitter, WuzeloConstants, _) {

  var CHANGE_EVENT = 'change';

  var _unassignedPlayers = [
  {
    id: 1,
    firstName: 'Max',
    lastName: 'Mustermann',
  },
  {
    id: 2,
    firstName: 'Moritz',
    lastName: 'Mustermann',
  },
  {
    id: 3,
    firstName: 'Manuel',
    lastName: 'Mustermann',
  },
  {
    id: 4,
    firstName: 'Maria',
    lastName: 'Mustermann',
  }
  ];

  var _teams = [{players: []}, {players:[]}];

  function joinTeam(player, team) {
    console.log('adding player ' + player + ' to team ' + team);
    if(!_teams[team]) {
      _teams[team] = {
        players: [player]
      };
    } else {
      _teams[team].players.push(player);
    }
    _unassignedPlayers.splice(_unassignedPlayers.findIndex(function(p, index, array) {
      return p.id === player.id;
    }), 1);
  }

  var WuzeloStore = _.extend({}, EventEmitter.prototype, {
    getUnassignedPlayers: function() {
      return _unassignedPlayers;
    },

    getRedTeam: function() {
      return _teams[0];
    },

    getBlueTeam: function() {
      return _teams[1];
    },

    emitChange: function() {
      this.emit(CHANGE_EVENT);
    },

    addChangeListener: function(callback) {
      this.on(CHANGE_EVENT, callback);
    },

    removeChangeListener: function(callback) {
      this.removeListener(CHANGE_EVENT, callback);
    }
  });

  AppDispatcher.register(function(action) {
    var text;

    switch(action.actionType) {
      case WuzeloConstants.JOIN_TEAM:
        joinTeam(action.player, action.team);
        console.log('joining team!');
        WuzeloStore.emitChange();
        break;

      default:
        //no op
    }
  });

  return WuzeloStore;
});
