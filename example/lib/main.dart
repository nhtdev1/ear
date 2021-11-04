import 'package:ear/models/models.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:ear/ear.dart';

void main() => runApp(HomePage());

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late StreamSubscription<EarModel> _earSubscription;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  /// Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async => startListening();

  void onData(EarModel event) => print(event.packageName);

  /// To get the infor of notification, you just subscribe to
  /// [ Stream<EarModel>]
  void startListening() {
    try {
      _earSubscription = Ear.instance.earStream!.listen(onData);
    } on EarModel catch (exception) {
      print(exception);
    }
  }

  /// IMPORTANT: before you is killed, you must call cancel stream to
  /// avoid memory leak
  void stopListening() => _earSubscription?.cancel();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Ear example app')),
        floatingActionButton: FloatingActionButton(
          onPressed: () async {
            bool result = await Ear.register();
            print(result ? "Start listenning successfully" :"Start listenning failure");
          },
        ),
      ),
    );
  }

  @override
  void dispose() {
    stopListening();
    super.dispose();
  }
}
