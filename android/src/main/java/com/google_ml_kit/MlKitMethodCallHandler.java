package com.google_ml_kit;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google_ml_kit.vision.BarcodeDetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MlKitMethodCallHandler implements MethodChannel.MethodCallHandler {

    private final Map<String, ApiDetectorInterface> handlers;

    public MlKitMethodCallHandler(Context context) {
        List<ApiDetectorInterface> detectors = new ArrayList<ApiDetectorInterface>(
                Arrays.asList(
                        new BarcodeDetector(context)
//                        new DigitalInkRecogniser(),
//                        new FaceDetector(context),
//                        new ImageLabelDetector(context),
//                        new PoseDetector(context),
//                        new TextDetector(context),
//                        new ObjectDetector(context),
//                        new CustomRemoteModelManager()
//                        new EntityExtractor(),
//                        new EntityModelManager(),
//                        new LanguageDetector(),
//                        new OnDeviceTranslator(),
//                        new TranslatorModelManager(),
//                        new SmartReply(),
//                        new TextDetectorV2(context)
                ));

        handlers = new HashMap<>();
        for (ApiDetectorInterface detector : detectors) {
            for (String method : detector.getMethodsKeys()) {
                handlers.put(method, detector);
            }
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        ApiDetectorInterface handler = handlers.get(call.method);
        if (handler != null) {
            handler.onMethodCall(call, result);
        } else {
            result.notImplemented();
        }
    }
}
