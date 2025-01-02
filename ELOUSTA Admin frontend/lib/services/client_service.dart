import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/client_request.dart';
import 'storage_service.dart';

class ClientService {
  final String baseUrl = 'http://10.0.2.2:8080';
  final StorageService _storageService = StorageService();

  Future<Map<String, String>> _getAuthHeaders() async {
    final token = await _storageService.readData('token');
    if (token == null) {
      throw Exception('Authentication token not found');
    }
    return {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };
  }

  Future<List<ClientRequest>> getClientRequests() async {
    try {
      final headers = await _getAuthHeaders();
      final response = await http.get(
        Uri.parse('$baseUrl/admin/client/requests'),
        headers: headers,
      );

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = json.decode(response.body);
        return jsonList.map((json) => ClientRequest.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load client requests');
      }
    } catch (e) {
      throw Exception('Failed to connect to server');
    }
  }

  Future<bool> deleteClient(int clientId) async {
    try {
      final headers = await _getAuthHeaders();
      final adminId = await _storageService.readData('userId');

      if (adminId == null) {
        throw Exception('Admin ID not found');
      }

      final response = await http.delete(
        Uri.parse('$baseUrl/admin/client/delete?clientId=$clientId&adminId=$adminId'),
        headers: headers,
      );

      if (response.statusCode == 200) {
        final bool success = json.decode(response.body);
        return success;
      } else {
        throw Exception('Failed to delete client');
      }
    } catch (e) {
      throw Exception('Failed to connect to server');
    }
  }
}