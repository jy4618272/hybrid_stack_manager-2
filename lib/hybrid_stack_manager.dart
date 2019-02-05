import 'dart:async';

import 'package:flutter/services.dart';

class HybridStackManager {
  static const MethodChannel _channel =
      const MethodChannel('hybrid_stack_manager');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
