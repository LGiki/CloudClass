module.exports = {
  chainWebpack: (config) => {
    config.plugin("html").tap((args) => {
      args[0].title = "到云";
      return args;
    });
  },

  transpileDependencies: ["vuetify"],
  publicPath: "",

  pluginOptions: {
    cordovaPath: "src-cordova",
  },
};
