
define(['dispatcher', 'constants'],
  function(AppDispatcher, WuzeloConstants) {
    var WuzeloActions = {
      joinTeam: function(player, team) {
        AppDispatcher.dispatch({
          actionType: WuzeloConstants.JOIN_TEAM,
          player: player,
          team: team
        });
      },


    };
    return WuzeloActions;
  }
);
