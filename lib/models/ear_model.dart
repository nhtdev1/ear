/// The class contains the information from coming notification
class EarModel {
  const EarModel({
    this.packageName,
    this.message,
    this.text,
    this.extra,
    this.timestamp,
  });

  /// Contains notification type like facebook, instagram,...
  final String packageName;
  /// Contains content of notification
  final String message;
  /// Contains title of notification
  final String text;
  /// Contains more info about notification
  final String extra;
  /// Contains timestamp of notification
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

  bool get isFromFacebook => this.packageName == "com.facebook.katana";

  bool get isFromMessenger => this.packageName == "com.facebook.orca";

  bool get isFromGmail => this.packageName == "com.google.android.gm";

  bool get isFromSkpe => this.packageName == "com.skype.raider";

  bool get isFromWhatsApp => this.packageName == "com.whatsapp";

  bool get isFromTwitter => this.packageName == "com.twitter.android";

  bool get isFromLinkedIn => this.packageName == "com.linkedin";

  bool get isFromWInstagram => this.packageName == "com.linkedin.android";

  bool get isFromLine => this.packageName == "jp.naver.line.android";

  bool get isFromSnapChat => this.packageName == "com.snapchat.android";

  bool get isFromQq => this.packageName == "com.tencent.mobileqq";

  bool get isFromWeChat => this.packageName == "com.tencent.mm";

  bool get isFromPhoneCall => this.packageName == "com.snapchat";

  bool get isFromSms => this.packageName == "com.snapchat";

  bool get isFromTiktok => this.packageName == "com.ss.android.ugc.trill";

  bool get isFromZalo => this.packageName == "com.zing.zalo";

  bool get isFromTelegram => this.packageName == "org.telegram.messenger";

  bool get isFromDiscord => this.packageName == "com.discord";

  bool get isFromViber => this.packageName == "com.viber.voip";

  bool get isFromKakaoTalk => this.packageName == "com.kakao.talk";

  bool get isFromHagoChat => this.packageName == "com.yy.hiyo";

  bool get isFromBigoLive => this.packageName == "sg.bigo.live";

  bool get isFromReddit => this.packageName == "com.reddit.frontpage";
}
