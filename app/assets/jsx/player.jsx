define(['react', 'hammer'],
  function(React, Hammer) {

  var Player = React.createClass({
    onLeft: function(event) {
      console.log(event.type);
      var elementToDrag = event.target;
      // deltaX tracks the distance dragged along the x-axis since the initial touch.
      elementToDrag.style.left = event.deltaX + 'px';
    },

    onRight: function(event) {
      console.log(event.type);
      var elementToDrag = event.target;
      // deltaX tracks the distance dragged along the x-axis since the initial touch.
      elementToDrag.style.left = event.deltaX + 'px';
    },

    resetElement: function(event) {
      var elementToReset = event.target;
      elementToReset.style.left = 0;
    },

    componentDidMount: function() {
      var options = {
        dragLockToAxis: true,
        dragBlockHorizontal: true
      };

      var hammertime = new Hammer(this.refs.playerNode.getDOMNode(), options);
      hammertime.on("panleft swipeleft", this.onLeft);
      hammertime.on("panright swiperight", this.onRight);
      hammertime.on("release", this.resetElement);
    },

    render: function() {
      return (<li ref="playerNode">{this.props.firstName} {this.props.lastName}</li>);
    },
  });
  return Player;
});
