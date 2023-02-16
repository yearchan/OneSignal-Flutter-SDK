package com.onesignal.flutter;

import com.onesignal.OneSignal;
import com.onesignal.Continue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class OneSignalLocation extends FlutterRegistrarResponder implements MethodCallHandler {
    private MethodChannel channel;

    static void registerWith(BinaryMessenger messenger) {
        OneSignalLocation controller = new OneSignalLocation();
        controller.messenger = messenger;
        controller.channel = new MethodChannel(messenger, "OneSignal#location");
        controller.channel.setMethodCallHandler(controller);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.contentEquals("OneSignal#requestPermission"))
            this.requestPermission(result);
        else if (call.method.contentEquals("OneSignal#setShared"))
            this.setShared(call, result);
        else if (call.method.contentEquals("OneSignal#isShared"))
            replySuccess(result, OneSignal.getLocation().isShared());
        else
            replyNotImplemented(result);
    }

    private void requestPermission(Result reply) {
        OneSignal.getLocation().requestPermission(Continue.none());
        replySuccess(reply, null);
    }
    
    private void setShared(MethodCall call, Result result) {
        OneSignal.getLocation().setShared((boolean) call.arguments);
        replySuccess(result, null);
    }
}
