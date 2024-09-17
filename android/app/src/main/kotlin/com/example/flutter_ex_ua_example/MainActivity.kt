package com.example.flutter_ex_ua_example

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    public lateinit var completion: MethodChannel.Result

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            "Channel"
        ).setMethodCallHandler { call, result ->
            completion = result
            when (call.method) {
                "hello" ->
                    result.success("Hello from Kotlin!")

                "launch" -> {
                    println("LOGGING:call launch")
                    val customTabsIntent = CustomTabsIntent.Builder()
                        .build()
                        .intent

                    customTabsIntent.data = Uri.parse("https://tucur-prg.github.io/flutter_ex_ua_example/")
                    startActivityForResult(customTabsIntent, 0)
                }

                else ->
                    result.notImplemented()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val action: String? = intent?.action
        val data: Uri? = intent?.data

        println("LOGGING:custom scheme open")
        println(data)
    }

    override fun onResume() {
        super.onResume()

        println("LOGGING:onResume")

        if (::completion.isInitialized) {
            completion.success("finish")
        }
    }
}
