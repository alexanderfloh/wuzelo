define(['react', 'player'],
  function(React, Player) {

  var App = React.createClass({

    players: [
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
    ],

    render: function() {
      var playerNodes = this.players.map(function(player) {
        return <Player firstName={player.firstName} lastName={player.lastName} key={player.id} />;
      });
      return (
        <div className="app">
          <div className="header">header</div>
          <ul className="players">
            {playerNodes}
          </ul>
          <div className="red-team team">red team</div>
          <div className="blue-team team">blue team</div>
          <div className="footer">footer</div>
        </div>);
    }
  });
  return App;
});
