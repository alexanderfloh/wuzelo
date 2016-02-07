define(['react', 'hammer', 'actions'],
  function(React, Hammer, WuzeloActions) {

  var Player = React.createClass({
    hammertime: null,

    onLeft: function(event) {
      //console.log(event.type);
      var elementToDrag = this.refs.playerNode.getDOMNode();
      // deltaX tracks the distance dragged along the x-axis since the initial touch.
      elementToDrag.style.left = event.deltaX + 'px';
      if(Math.abs(event.deltaX) > 150) {
        WuzeloActions.joinTeam(this.props.player, 0);
        //resetElement(event);
      }
    },

    onRight: function(event) {
      //console.log(event.type);
      var elementToDrag = this.refs.playerNode.getDOMNode();
      // deltaX tracks the distance dragged along the x-axis since the initial touch.
      elementToDrag.style.left = event.deltaX + 'px';
      if(Math.abs(event.deltaX) > 150) {
        WuzeloActions.joinTeam(this.props.player, 1);
        //resetElement(event);
      }
    },

    resetElement: function(event) {
      var elementToReset = this.refs.playerNode.getDOMNode();
      elementToReset.style.left = 0;
    },

    componentDidMount: function() {
      var options = {
        dragLockToAxis: true,
        dragBlockHorizontal: true
      };

      this.hammertime = new Hammer(this.refs.playerNode.getDOMNode(), options);
      this.hammertime.on("panleft swipeleft", this.onLeft);
      this.hammertime.on("panright swiperight", this.onRight);
      this.hammertime.on("release", this.resetElement);
    },

    componentWillUnmount: function() {
      if(this.hammertime) {
        this.hammertime.off("panleft swipeleft", this.onLeft);
        this.hammertime.off("panright swiperight", this.onRight);
        this.hammertime.off("release", this.resetElement);
        this.hammertime = null;
      }
    },

    render: function() {
      return (<li className="player" ref="playerNode">
        {this.props.player.firstName} {this.props.player.lastName} {this.props.player.elo} {this.props.player.rd}
      </li>);
    },
  });
  return Player;
});
