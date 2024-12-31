import 'dart:convert';
import 'package:elousta/client_requests/complain_dto.dart';
import 'package:elousta/client_requests/requests_status_payload.dart';
import 'package:http/http.dart' as http;
import 'request_class.dart';

class ApiService {
  //TODO: will be taken from localStorage
  String token =
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzM1Njg3MTMxLCJleHAiOjE3MzU3NzM1MzF9.jQC70CRaPqXbfCGACJ4P1ROi9ou272WPp54ZjaJw22M";

  final String baseUrl =
      "http://10.0.2.2:8080"; // Replace with your backend URL

  // Fetch Pending Requests
  Future<List<Request>> fetchPendingRequests(int id) async {
    return _fetchRequests('$baseUrl/client/request/pending/$id');
  }

  // Fetch In Progress Requests
  Future<List<Request>> fetchInProgressRequests(int id) async {
    return _fetchRequests('$baseUrl/client/request/inProgress/$id');
  }

  // Fetch Completed Requests
  Future<List<Request>> fetchCompletedRequests(int id) async {
    return _fetchRequests('$baseUrl/client/request/completed/$id');
  }

  Future<void> doneRequest(Request request) async {
    final payload = RequestStatusPayload(
      id: request.id,
      clientId: request.clientId,
      techId: request.techId,
    );

    final url = Uri.parse('$baseUrl/client/request/done');
    final response = await http.post(
      url,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token'
      },
      body: jsonEncode(payload.toJson()),
    );

    if (response.statusCode == 200) {
      print("Done request successful.");
    } else {
      throw Exception(
          "Failed to complete request. Status: ${response.statusCode}");
    }
  }

  Future<void> cancelRequest(Request request) async {
    final payload = RequestStatusPayload(
      id: request.id,
      clientId: request.clientId,
      techId: request.techId,
    );

    final url = Uri.parse('$baseUrl/client/request/refuse');
    final response = await http.post(
      url,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token'
      },
      body: jsonEncode(payload.toJson()),
    );

    if (response.statusCode == 200) {
      print("Cancel request successful.");
    } else {
      throw Exception(
          "Failed to cancel request. Status: ${response.statusCode}");
    }
  }

  Future<void> addComplaint(ComplaintDTO complaintDTO) async {
    final url = Uri.parse('$baseUrl/client/request/complain');
    final response = await http.post(
      url,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token'
      },
      body: jsonEncode(complaintDTO.toJson()),
    );

    if (response.statusCode == 200) {
      print("Complain request successful.");
    } else {
      throw Exception(
          "Failed to Complain request. Status: ${response.statusCode}");
    }
  }

  // Generic function to fetch data
  Future<List<Request>> _fetchRequests(String url) async {
    try {
      final response = await http
          .get(Uri.parse(url), headers: {'Authorization': 'Bearer $token'});
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
