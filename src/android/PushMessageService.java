package com.oppo.plugins;

import android.content.Context;
import com.coloros.mcssdk.PushService;
import com.coloros.mcssdk.mode.AppMessage;
import com.coloros.mcssdk.mode.CommandMessage;
import com.coloros.mcssdk.mode.SptDataMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class PushMessageService extends PushService {

    /**
     * 命令消息，主要是服务端对客户端调用的反馈，一般应用不需要重写此方法
     *
     * @param context
     * @param commandMessage
     */
    @Override
    public void processMessage(Context context, CommandMessage commandMessage) {
        super.processMessage(context, commandMessage);
    }

    /**
     * 普通应用消息，视情况看是否需要重写
     *
     * @param context
     * @param appMessage
     */
    @Override
    public void processMessage(Context context, AppMessage appMessage) {
        super.processMessage(context, appMessage);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", appMessage.getContent());
            jsonObject.put("title", appMessage.getTitle());
            jsonObject.put("type", appMessage.getType());
            CordovaPluginOppo.onNotificationCallBack(jsonObject, null);
        } catch (JSONException e) {
        }

    }


    /**
     * 透传消息处理，应用可以打开页面或者执行命令,如果应用不需要处理透传消息，则不需要重写此方法
     *
     * @param context
     * @param sptDataMessage
     */
    @Override
    public void processMessage(Context context, SptDataMessage sptDataMessage) {
        super.processMessage(context.getApplicationContext(), sptDataMessage);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", sptDataMessage.getContent());
            jsonObject.put("type", sptDataMessage.getType());
            CordovaPluginOppo.onNotificationCallBack(jsonObject, null);
        } catch (JSONException e) {
        }
    }
}
