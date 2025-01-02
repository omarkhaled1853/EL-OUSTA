class Request {
  final int id;
  final int techId;
  final int clientId;
  final String location;
  final String description;
  final String startDate;
  final String endDate;

  Request({
    required this.id,
    required this.techId,
    required this.clientId,
    required this.location,
    required this.description,
    required this.startDate,
    required this.endDate,
  });

  factory Request.fromJson(Map<String, dynamic> json) {
    return Request(
      id: json['id'],
      description: json['description'],
      startDate: json['startDate'],
      endDate: json['endDate'],
      techId: json['techId'],
      clientId: json['clientId'],
      location: json['location'],
    );
  }
}
