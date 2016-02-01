
requirejs.config({
  paths: {
    'jquery': '../lib/jquery/jquery',
    'react': '../lib/react/react-with-addons',
    'flux': '../lib/flux/Flux',
    'keymirror': '../js/3rd-party/keyMirror',
    'hammer': '../lib/hammerjs/hammer',
    'underscore': '../lib/underscorejs/underscore',
    'events': '../lib/EventEmitter/EventEmitter',

    'actions': './actions/WuzeloActions',

    'player': './components/player.react',
    'team': './components/team.react',

    'constants': './constants/WuzeloConstants',

    'dispatcher': './dispatcher/AppDispatcher',

    'store': './stores/WuzeloStore',
  }
});


require(['react', './components/app.react'], function(React, App) {
  React.render(
    <App />,
    document.getElementById('wuzeloapp')
  );
});
