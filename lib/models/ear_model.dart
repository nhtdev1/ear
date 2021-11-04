/// The class contains the information from coming notification
class EarModel {
  const EarModel({
    required this.packageName,
    required this.message,
    required this.text,
    required this.extra,
    required this.timestamp,
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

  bool get isFromFacebook => this.packageName == "com.facebook.katana";

  bool get isFromMessenger => this.packageName == "com.facebook.orca";

  bool get isFromGmail => this.packageName == "com.google.android.gm";

  bool get isFromSkype => this.packageName == "com.skype.raider";

  bool get isFromWhatsApp => this.packageName == "com.whatsapp";

  bool get isFromTwitter => this.packageName == "com.twitter.android";

  bool get isFromLinkedIn => this.packageName == "com.linkedin";

  bool get isFromWInstagram => this.packageName == "com.linkedin.android";

  bool get isFromLine => this.packageName == "jp.naver.line.android";

  bool get isFromSnapChat => this.packageName == "com.snapchat.android";

  bool get isFromQq => this.packageName == "com.tencent.mobileqq";

  bool get isFromWeChat => this.packageName == "com.tencent.mm";

  bool get isFromPhoneCall => this.packageName == "com.google.android.dialer";

  bool get isFromSms => this.packageName == "com.google.android.apps.messaging";

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
