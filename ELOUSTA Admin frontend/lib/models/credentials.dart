class Credentials {
  final String status;
  final String? token;
  final String? id;

  Credentials({
    required this.status,
    this.token,
    this.id,
  });

  factory Credentials.fromJson(Map<String, dynamic> json) {
    return Credentials(
      status: json['status'],
      token: json['token'],
      id: json['id'],
    );
  }
}