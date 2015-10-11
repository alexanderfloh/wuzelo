define(['react'],
  function(React) {

  var Player = React.createClass({
    render: function() {
      return (<li>{this.props.firstName} {this.props.lastName}</li>);
    }
  });
  return Player;
});
