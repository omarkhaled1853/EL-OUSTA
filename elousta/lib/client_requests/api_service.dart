import 'dart:convert';
import 'package:http/http.dart' as http;
import 'request_class.dart'; // Import your Request model

class ApiService {
  final String baseUrl =
      "http://10.0.2.2:8080"; // Replace with your backend URL

  // Fetch Pending Requests
  Future<List<Request>> fetchPendingRequests(int id) async {
    return _fetchRequests('$baseUrl/client/requests/pending/$id');
  }

  // Fetch In Progress Requests
  Future<List<Request>> fetchInProgressRequests(int id) async {
    return _fetchRequests('$baseUrl/client/requests/inprogress/$id');
  }

  // Fetch Completed Requests
  Future<List<Request>> fetchCompletedRequests(int id) async {
    return _fetchRequests('$baseUrl/client/requests/completed/$id');
  }

  // Generic function to fetch data
  Future<List<Request>> _fetchRequests(String url) async {
    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        List<dynamic> jsonResponse = json.decode(response.body);
        return jsonResponse.map((data) => Request.fromJson(data)).toList();
      } else {
        throw Exception('Failed to fetch requests: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error fetching data: $e');
    }
  }
}
