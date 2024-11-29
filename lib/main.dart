import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const ZebraScannerApp());
}

class ZebraScannerApp extends StatelessWidget {
  const ZebraScannerApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Zebra Scanner',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const ZebraScannerScreen(),
    );
  }
}

class ZebraScannerScreen extends StatefulWidget {
  const ZebraScannerScreen({Key? key}) : super(key: key);

  @override
  State<ZebraScannerScreen> createState() => _ZebraScannerScreenState();
}

class _ZebraScannerScreenState extends State<ZebraScannerScreen> {
  static const platform = MethodChannel('zebra_scanner_channel');
  String _scannedData = 'Waiting for scan...';

  @override
  void initState() {
    super.initState();
    configureScanner();
    platform.setMethodCallHandler((call) async {
      if (call.method == "onScannedData") {
        setState(() {
          _scannedData = call.arguments ?? "No data received";
        });
      }
    });
  }

  Future<void> configureScanner() async {
    try {
      await platform.invokeMethod('configureScanner');
    } catch (e) {
      setState(() {
        _scannedData = 'Failed to configure scanner: $e';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Zebra Scanner')),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          // Display scanned data
          Text(
            _scannedData,
            style: const TextStyle(fontSize: 18),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 20),
          ElevatedButton(
            onPressed: () {
              setState(() {
                _scannedData = "Test Barcode Data";
              });
            },
            child: const Text('Test Barcode Scan'),
          ),
        ],
      ),
    );
  }
}