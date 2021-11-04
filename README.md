
# [ear](https://pub.dev/packages/ear)  

![iconoir_chat-bubble-check-1](https://user-images.githubusercontent.com/35698467/140308408-49e5d3b9-5afe-4f25-b6ad-c86fb0ea7a99.png)

A Library to handle all coming notification for android

> Supported Platforms
>
> - Android

## ⚙️ Android Setup

```yaml
# add this line to your dependencies
ear: ^0.0.5
```

```dart
import 'package:ear/ear.dart';
```

## 1. Subcribe to stream to listen all coming notification 

```dart
 void startListening() {
    try {
     StreamSubscription<EarModel> _earSubscription = Ear.instance.earStream.listen(onData);
    } on EarModel catch (exception) {
      print(exception);
    }
  }
```

### 2. Unsubcribe from stream

```dart
 _earSubscription?.cancel();
```

### 3. Call register to start listenning

```dart
 Ear.register();
```

### 4. To check type of specific notifcation

```dart
earModel.isFromFacebook();
earModel.isFromGmail();
earModel.isFromSkype();
...
```

## If you need any features suggest

...

## Help needed for iOS 

Looking for iOS Dev who can check out and fix the issues releated to iOS (I dont have a Mac and iOS)
