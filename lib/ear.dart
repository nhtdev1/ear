import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:ear/models/models.dart';
import 'package:flutter/services.dart';

class Ear {
  Ear._internal();

  static final Ear _instance = Ear._internal();

  static Ear get instance => _instance;

  Stream<EarModel> _earStream;

  static const MethodChannel _channel = const MethodChannel('ear/command');
  static const EventChannel _event_channel = const EventChannel('ear/event');

  static EventChannel eventChannel() => _event_channel;

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  Stream<EarModel> get earStream {
    if (Platform.isAndroid) {
      if (_earStream == null) {
        _earStream = Ear.eventChannel().receiveBroadcastStream().map(_earEvent);
        return _earStream;
      }
    }
    return null;
  }

  EarModel _earEvent(dynamic event) => EarModel.fromJson(json.decode(event));

  static Future<bool> register() async =>
      await _channel.invokeMethod('register');
}
