package com.oppo.plugins;

import com.coloros.mcssdk.PushManager;
import com.coloros.mcssdk.callback.PushAdapter;
import com.coloros.mcssdk.callback.PushCallback;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;
/**
 * This class echoes a string called from JavaScript.
 */
public class CordovaPluginOppo extends CordovaPlugin {

		private String appKey;
		private String appSecret;
		private static CordovaPluginOppo instance;
        private PushCallback mPushCallback = new PushAdapter() {
           @Override
           public void onRegister(int code, String s) {
               instance.onRegister(code, s);
           }

           @Override
           public void onUnRegister(int code) {
               instance.onUnRegister(code);
           }
       };

       public CordovaPluginOppo(){
           instance = this;
       }
	   
	   @Override
		public void initialize(CordovaInterface cordova, CordovaWebView webView) {
			PackageManager packageManager = cordova.getActivity().getPackageManager();
			ApplicationInfo applicationInfo;
			try {
					applicationInfo = packageManager.getApplicationInfo(cordova.getActivity().getPackageName(), PackageManager.GET_META_DATA);
					appKey = applicationInfo.metaData.getString("APP_KEY");
					appSecret = applicationInfo.metaData.getString("APP_SECRET");
			} catch (PackageManager.NameNotFoundException e) {
				Toast.makeText(cordova.getActivity().getApplicationContext(), "推送服务初始化错误", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}


       public void onRegister(int code, String result){
        try{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("code", code);
				jsonObject.put("content", result);
				String format = "window.plugins.cordovaPluginOppo.onRegister(%s);";
				final String js = String.format(format, jsonObject.toString());
				instance.cordova.getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						instance.webView.loadUrl("javascript:" + js);
					}
				});
           }catch(Exception ex){}
       }

       public void onUnRegister(int code){
          try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", code);
                String format = "window.plugins.cordovaPluginOppo.onUnRegister(%s);";
                final String js = String.format(format, jsonObject.toString());
                instance.cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        instance.webView.loadUrl("javascript:" + js);
                    }
                });
           }catch(Exception ex){}
       }


       @Override
       public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
           if("init".equals(action)){
             try{
               PushManager.getInstance().register(this.cordova.getActivity(), appKey, appSecret, instance.mPushCallback);
             }catch (Exception e){
               instance.onRegister(-1, e.getMessage());
             }
             return true;
           }else if("getRegister".equals(action)){
               PushManager.getInstance().getRegister();
               return true;
           }else if("unRegister".equals(action)){
               PushManager.getInstance().unRegister();
               return true;
           }else if("pausePush".equals(action)){
               try{
                   PushManager.getInstance().pausePush();
                   callbackContext.success();
               }catch (Exception e){
                   callbackContext.error(e.getMessage());
               }
               return true;
           }else if("resumePush".equals(action)){
               try{
                   PushManager.getInstance().resumePush();
                   callbackContext.success();
               }catch (Exception e){
                   callbackContext.error(e.getMessage());
               }
               return true;
           }else if("getPushVersion".equals(action)){
               try{
                   int code = PushManager.getInstance().getPushVersionCode();
                   callbackContext.success(code);
               }catch (Exception e){
                   callbackContext.error(e.getMessage());
               }
               return true;
           }
           return false;

       }

       /**
        * 接受到消息
        */
       public static void onNotificationCallBack(JSONObject jsonObject,Object other) {
           if (instance == null) {
               return;
           }
           String format = "window.plugins.cordovaPluginOppo.onNotificationArrived(%s);";
           final String js = String.format(format, jsonObject.toString());
           instance.cordova.getActivity().runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   instance.webView.loadUrl("javascript:" + js);
               }
           });
       }
}
