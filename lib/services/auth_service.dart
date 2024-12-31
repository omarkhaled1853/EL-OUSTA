import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/auth_request.dart';
import '../models/credentials.dart';

class AuthService {
  final String baseUrl = 'http://10.0.2.2:8080'; // Replace with your actual backend URL

  Future<Credentials> login(String username, String password) async {
    if (username.length > 100 || password.length > 100) {
      return Credentials(status: 'FAIL');
    }

    try {
      final response = await http.post(
        Uri.parse('$baseUrl/admin/signIn'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(AuthRequest(
          username: username,
          password: password,
        ).toJson()),
      );

      if (response.statusCode != 200) {
        return Credentials(status: 'FAIL');
      }

      return Credentials.fromJson(json.decode(response.body));
    } catch (e) {
      throw Exception('Failed to connect to server');
    }
  }
}