requirejs.config({
  paths: {
    'jquery': '../lib/jquery/jquery',
    'react': '../lib/react/react-with-addons',
    'hammer': '../lib/hammerjs/hammer',
    'app': '../jsx/app',
    'player': '../jsx/player',
  }
});

require([
  'react',
  'jquery',
  'app'],
  function(
    React,
    $,
    App
  ) {
  React.render(
    React.createElement(
      App,
      {

      }
    ),
    document.getElementById('content')
  );
});
