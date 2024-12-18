import 'package:flutter/rendering.dart';

class User {
  final String username;
  final String password;
  final String emailAddress;
  final String firstName;
  final String lastName;
  final DateTime dob;
  final String phoneNumber;
  final String roles;
  final String city;

  User(
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
      required List clientNotifications,
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
    };
  }

  factory User.fromJson(Map<String, dynamic> json) {
    print(json);
    return switch (json) {
      {
      'id': int id,
      'username': String username,
      'password': String password,
      'emailAddress': String emailAddress,
      'firstName': String firstName,
      'lastName': String lastName,
      'dob': String dob,
      'phoneNumber': String phoneNumber,
      'city': String city,
      'roles': String roles,
      'clientNotifications': []
      } =>
          User(
              username: username,
              password: password,
              emailAddress: emailAddress,
              firstName: firstName,
              lastName: lastName,
              dob: DateTime.parse(json['dob'] as String),
              phoneNumber: phoneNumber,
              city: city,
              roles: roles,
              clientNotifications: [],
          ),
      _ => throw const FormatException('Failed to load User.'),
    };
  }
}