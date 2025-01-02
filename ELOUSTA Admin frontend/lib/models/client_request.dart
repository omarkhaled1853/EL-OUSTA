class ClientRequest {
  final int id;
  final String emailAddress;
  final String username;
  final String firstName;
  final String lastName;
  final String phoneNumber;
  final int cnt;

  ClientRequest({
    required this.id,
    required this.emailAddress,
    required this.username,
    required this.firstName,
    required this.lastName,
    required this.phoneNumber,
    required this.cnt,
  });

  factory ClientRequest.fromJson(Map<String, dynamic> json) {
    return ClientRequest(
      id: json['id'],
      emailAddress: json['emailAddress'],
      username: json['username'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      phoneNumber: json['phoneNumber'],
      cnt: json['cnt'],
    );
  }
}