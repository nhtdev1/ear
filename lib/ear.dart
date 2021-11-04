import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:ear/models/models.dart';
import 'package:flutter/services.dart';

/// A class to communicate with native code, register to lisnenning all
/// coming notificaiton and provider a stream container specific notification
class Ear {
  Ear._internal();

  static final Ear _instance = Ear._internal();

  static Ear get instance => _instance;

  Stream<EarModel> _earStream;

  static const MethodChannel _channel = const MethodChannel('ear/command');

  static const EventChannel _eventChannel = const EventChannel('ear/event');

  static EventChannel eventChannel() => _eventChannel;

  Stream<EarModel> get earStream {
    if (Platform.isAndroid) {
      if (_earStream == null) {
        _earStream = Ear.eventChannel().receiveBroadcastStream().map(_earEvent);
        return _earStream;
      }
    }
    return null;
  }

  /// Handle raw json return from notification and convert it to EarModel
  EarModel _earEvent(dynamic event) => EarModel.fromJson(json.decode(event));

  /// Register to start listenning coming notification
  static Future<bool> register() async =>
      await _channel.invokeMethod('register');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
