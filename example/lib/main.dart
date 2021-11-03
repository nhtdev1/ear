import 'package:ear/models/models.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:convert';

import 'package:ear/ear.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  StreamSubscription<EarModel> _earSubscription;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    startListening();
  }

  void onData(EarModel event) {
    print(event);
    print('converting package extra to json');
    print(event.packageName);
  }

  void startListening() {
    try {
      _earSubscription = Ear.instance.earStream.listen(onData);
    } on EarModel catch (exception) {
      print(exception);
    }
  }

  void stopListening() => _earSubscription?.cancel();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () async {
            bool result = await Ear.register();
            print(result);
          },
        ),
      ),
    );
  }
}
