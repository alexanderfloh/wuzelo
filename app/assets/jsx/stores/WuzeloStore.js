define(['dispatcher', 'events', 'constants', 'underscore', 'jquery'],
  function(AppDispatcher, EventEmitter, WuzeloConstants, _, $) {

  var CHANGE_EVENT = 'change';

  var _allPlayers = [ ];

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
    _allPlayers.splice(_allPlayers.findIndex(function(p, index, array) {
      return p.id === player.id;
    }), 1);
  }

  var WuzeloStore = _.extend({}, EventEmitter.prototype, {
    getUnassignedPlayers: function() {
      return _allPlayers.filter(function(player) {
        return !(_teams[0].players.some(function(p) { return p.id === player.id;} ) ||
          _teams[1].players.some(function(p) { return p.id === player.id;} ));
      });
    },

    getRedTeam: function() {
      return _teams[0];
    },

    getBlueTeam: function() {
      return _teams[1];
    },

    loadPlayers: function() {
      $.ajax({
        url: '/players',
        dataType: 'json',
        success: function(data) {
          _allPlayers = data;
          WuzeloStore.emit(CHANGE_EVENT);
        }.bind(this),
        error: function(xhr, status, err) {
          console.error('/players', status, err.toString());
        }.bind(this)
      });
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

  WuzeloStore.loadPlayers();
  setInterval(WuzeloStore.loadPlayers, 10 * 1000);
  return WuzeloStore;
});
