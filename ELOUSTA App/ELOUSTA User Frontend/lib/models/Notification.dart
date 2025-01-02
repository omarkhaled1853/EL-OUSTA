class NotificationObject {
  final String message;
  final DateTime date;

  NotificationObject({
    required this.message,
    required this.date,
  });

  factory NotificationObject.fromJson(Map<String, dynamic> json) {
    return NotificationObject(
      message: json['message'],
      date: DateTime.parse(json['date']),
    );
  }
}