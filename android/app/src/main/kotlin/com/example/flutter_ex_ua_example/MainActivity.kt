package com.example.flutter_ex_ua_example

import io.flutter.embedding.android.FlutterActivity

class MainActivity: FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            "Channel"
        ).setMethodCallHandler { call, result ->
            when (call.method) {
                "hello" ->
                    result.success("Hello from Kotlin!")

                else ->
                    result.notImplemented()
            }
        }

    }
}
