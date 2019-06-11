/*var exec = require('cordova/exec');

var myMathFunc1 = function(){};

myMathFunc1.prototype.plus = function(success, error, arg0) {
  exec(success, error, "CordovaPluginOppo", "plus", arg0);
};

myMathFunc1.prototype.minus = function(success, error, arg0) {
  exec(success, error, "CordovaPluginOppo", "minus", arg0);
};

var MYMATHFUNC1 = new myMathFunc1();
module.exports = MYMATHFUNC1;*/

var exec = require('cordova/exec');
var CordovaPluginOppo = function() {}

CordovaPluginOppo.prototype.init = function(successCallback, errorCallback) {
  exec(successCallback, errorCallback, 'CordovaPluginOppo', "init", []);
}
CordovaPluginOppo.prototype.getRegister = function(args,successCallback,errorCallback) {
  exec(successCallback, errorCallback, 'CordovaPluginOppo', "getRegister", args);
}
CordovaPluginOppo.prototype.unRegister = function(args,successCallback,errorCallback) {
  exec(successCallback, errorCallback, 'CordovaPluginOppo', "unRegister", args);
}
CordovaPluginOppo.prototype.pausePush = function(args,successCallback, errorCallback) {
  exec(successCallback, errorCallback, 'CordovaPluginOppo', "pausePush", args);
}
CordovaPluginOppo.prototype.resumePush = function(args,successCallback, errorCallback) {
  exec(successCallback, errorCallback, 'CordovaPluginOppo', "resumePush", args);
}
CordovaPluginOppo.prototype.getPushVersionCode = function(args,successCallback, errorCallback) {
  exec(successCallback, errorCallback, 'CordovaPluginOppo', "getPushVersionCode", args);
}
CordovaPluginOppo.prototype.onRegister = function (data) {
  data = JSON.parse(JSON.stringify(data));
  cordova.fireDocumentEvent('cordovaPluginOppo.onRegister', data);
}
CordovaPluginOppo.prototype.onUnRegister = function (data) {
  data = JSON.parse(JSON.stringify(data));
  cordova.fireDocumentEvent('cordovaPluginOppo.onUnRegister', data);
}
/**
 * 监听事件
 * document.addEventListener("cordovaPluginOppo.onNotificationArrived", (data) => {
      console.log(JSON.stringify(data));
    }, false);
 */
CordovaPluginOppo.prototype.onNotificationArrived = function (data) {
  data = JSON.parse(JSON.stringify(data));
  cordova.fireDocumentEvent('cordovaPluginOppo.onNotificationArrived', data);
}

CordovaPluginOppo.prototype.onNotificationClicked = function (data) {
  data = JSON.parse(JSON.stringify(data));
  cordova.fireDocumentEvent('cordovaPluginOppo.onNotificationClicked', data);
}
if (!window.plugins) {
  window.plugins = {}
}

if (!window.plugins.cordovaPluginOppo) {
  window.plugins.cordovaPluginOppo = new CordovaPluginOppo()
}
module.exports = new CordovaPluginOppo();
