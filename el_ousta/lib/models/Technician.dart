class Technician {
  final String username;
  final String password;
  final String emailAddress;
  final String firstName;
  final String lastName;
  final DateTime dob;
  final String phoneNumber;
  final String roles;
  final String city;
  final String domain;
  final DateTime startDate;

  Technician(
      {
        required this.username,
        required this.password,
        required this.emailAddress,
        required this.firstName,
        required this.lastName,
        required this.dob,
        required this.phoneNumber,
        required this.city,
        required this.roles,
        required this.domain,
        required this.startDate
      }
    );
  Map<String, dynamic> toJson() {
    return {
      'username': username,
      'password': password,
      'emailAddress': emailAddress,
      'firstName': firstName,
      'lastName': lastName,
      'dob': dob.toIso8601String(), // Convert DateTime to ISO format
      'phoneNumber': phoneNumber,
      'city': city,
      'roles': roles,
      'domain': domain,
      'startDate': startDate.toIso8601String(), // Convert DateTime to ISO format
    };
  }
}