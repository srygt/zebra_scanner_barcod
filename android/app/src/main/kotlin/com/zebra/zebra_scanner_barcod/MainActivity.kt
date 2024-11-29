package com.zebra.zebra_scanner_barcod

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "zebra_scanner_channel"
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // MethodChannel tanımlaması
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "configureScanner" -> {
                    configureScanner()
                    result.success("Scanner configured")
                }
                else -> result.notImplemented()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // BroadcastReceiver tanımlaması
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == "com.symbol.datawedge.api.ACTION") {
                    val scannedData = intent.getStringExtra("com.symbol.datawedge.data_string") ?: ""
                    android.util.Log.d("ZebraScanner", "Scanned Data: $scannedData")
                    flutterEngine?.dartExecutor?.binaryMessenger?.let { messenger ->
                        MethodChannel(messenger, "zebra_scanner_channel")
                            .invokeMethod("onScannedData", scannedData)
                    }
                }
            }
        }

        // BroadcastReceiver'ı kayıt et
        val filter = IntentFilter().apply {
            addAction("com.symbol.datawedge.api.ACTION")
        }
        registerReceiver(broadcastReceiver, filter)
    }

    private fun configureScanner() {
        val intent = Intent("com.symbol.datawedge.api.ACTION").apply {
            putExtra("com.symbol.datawedge.api.SET_CONFIG", Bundle().apply {
                putString("PROFILE_NAME", "FlutterProfile")
                putString("PROFILE_ENABLED", "true")
                putString("CONFIG_MODE", "CREATE_IF_NOT_EXIST")
                putParcelableArray("APP_LIST", arrayOf(Bundle().apply {
                    putString("PACKAGE_NAME", packageName)
                    putStringArray("ACTIVITY_LIST", arrayOf("*"))
                }))
                putParcelableArray("PLUGIN_CONFIG", arrayOf(Bundle().apply {
                    putString("PLUGIN_NAME", "BARCODE")
                    putString("RESET_CONFIG", "true")
                    putParcelable("PARAM_LIST", Bundle().apply {
                        putString("scanner_input_enabled", "true")
                    })
                }))
            })
        }
        sendBroadcast(intent)
    }
}