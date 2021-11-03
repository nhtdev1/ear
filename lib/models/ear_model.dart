/// The class contains the information from coming notification
class EarModel {
  const EarModel({
    this.packageName,
    this.message,
    this.text,
    this.extra,
    this.timestamp,
  });

  final String packageName;
  final String message;
  final String text;
  final String extra;
  final int timestamp;

  static const empty =
      EarModel(packageName: "", message: "", text: "", extra: "", timestamp: 0);

  bool get isEmpty => this.packageName.isEmpty && this.timestamp == 0;

  bool get isNotEmpty => this.packageName.isNotEmpty && this.timestamp != 0;

  EarModel.fromJson(Map<String, dynamic> json)
      : packageName = json['packageName'],
        message = json['message'],
        text = json['text'],
        extra = json['extra'],
        timestamp = json['timestamp'];
}
