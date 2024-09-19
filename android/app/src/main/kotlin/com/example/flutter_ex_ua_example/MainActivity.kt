package com.example.flutter_ex_ua_example

import android.content.Intent
import android.os.Bundle
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private var completion: MethodChannel.Result? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        println("LOGGING:configureFlutterEngine")

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        println("LOGGING:onActivityResult")
        println("LOGGING:request = %d, result = %d".format(requestCode, resultCode))
        println(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("LOGGING:savedInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()

        println("LOGGING:onDestroy")
    }

    // Custom url Scheme
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val action: String? = intent?.action
        val data: Uri? = intent?.data

        println("LOGGING:onNewIntent")
        println(data)
    }

    override fun onPause() {
        super.onPause()

        println("LOGGING:onPause")
    }

    override fun onResume() {
        super.onResume()

        println("LOGGING:onResume")

        val result = completion;
        if (result != null) {
            result.success("finish")
            completion = null
        }
    }

    override fun onStart() {
        super.onStart()

        println("LOGGING:onStart")
    }

    override fun onStop() {
        super.onStop()

        println("LOGGING:onStop")
    }  
}
