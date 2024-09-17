import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BrowserPage extends StatelessWidget {
  const BrowserPage({super.key});

  @override
  Widget build(BuildContext context) {
    Future<void> launch() async {
      const channel = MethodChannel('Channel');
      final resultText = await channel.invokeMethod('launch');
      debugPrint("call launch");
      debugPrint(resultText);
    }

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text("Browser"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
              child: const Text("browser"),
              onPressed: () async => launch(),
            ),
          ],
        ),
      ),
    );
  }
}
