define(['react', 'player', 'store'],
  function(React, Player, WuzeloStore) {

  function getAppState() {
    return {
      unassignedPlayers: WuzeloStore.getUnassignedPlayers(),
      redTeam: WuzeloStore.getRedTeam(),
      blueTeam: WuzeloStore.getBlueTeam(),
    };
  };

  var App = React.createClass({
    getInitialState: function() {
      return getAppState();
    },

    componentDidMount: function() {
      WuzeloStore.addChangeListener(this._onChange);
    },

    componentWillUnmount: function() {
      WuzeloStore.removeChangeListener(this._onChange);
    },

    render: function() {
      var playerNodes = this.state.unassignedPlayers.map(function(player) {
        return <Player player={player} key={player.id} />;
      });

      var redTeamNodes = this.state.redTeam.players.map(function(player) {
        return <Player player={player} key={player.id} />;
      });
      var redWinAction = '/addGame/' + this.state.redTeam.players.map(function(player) {
        return player.id;
      }).join(',') + ',' +
      this.state.blueTeam.players.map(function(player) {
        return player.id;
      }).join(',');

      var blueTeamNodes = this.state.blueTeam.players.map(function(player) {
        return <Player player={player} key={player.id} />;
      });
      var blueWinAction = '/addGame/' + this.state.blueTeam.players.map(function(player) {
        return player.id;
      }).join(',') + ',' +
      this.state.redTeam.players.map(function(player) {
        return player.id;
      }).join(',');

      return (
        <div className="app">
          <div className="header">header</div>
          <ul className="players">
            {playerNodes}
          </ul>
          <div className="red-team team">
            <h1>red team</h1>
            {redTeamNodes}
            <form action={redWinAction} method="post">
              <input type="submit" value="win" />
            </form>
          </div>
          <div className="blue-team team">
            <h1>blue team</h1>
            {blueTeamNodes}
            <form action={blueWinAction} method="post">
              <input type="submit" value="win" />
            </form>
          </div>
          <div className="footer">footer</div>
        </div>);
    },

    _onChange: function() {
      this.setState(getAppState());
    },
  });
  return App;
});
