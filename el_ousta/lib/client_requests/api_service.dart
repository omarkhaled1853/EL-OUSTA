import 'dart:convert';
import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/client_requests/requests_status_payload.dart';
import 'package:el_ousta/main.dart';
import 'package:http/http.dart' as http;
import 'complain_dto.dart';
import 'request_class.dart';

void getTokenAndId() async {
  ApiService.token = (await secureStorage.read(key: 'auth_token'))!;
}

class ApiService {
  //TODO: will be taken from localStorage
  static String token = "";
  ApiService() {
    getTokenAndId();
  }
  // Fetch Pending Requests
  Future<List<Request>> fetchPendingRequests(int id) async {
    return _fetchRequests(ServerAPI.baseURL + '/client/request/pending/$id');
  }

  // Fetch In Progress Requests
  Future<List<Request>> fetchInProgressRequests(int id) async {
    return _fetchRequests(ServerAPI.baseURL + '/client/request/inProgress/$id');
  }

  // Fetch Completed Requests
  Future<List<Request>> fetchCompletedRequests(int id) async {
    return _fetchRequests(ServerAPI.baseURL + '/client/request/completed/$id');
  }

  Future<void> doneRequest(Request request) async {
    final payload = RequestStatusPayload(
      id: request.id,
      clientId: 1,
      techId: request.techId,
    );

    final url = Uri.parse(ServerAPI.baseURL + '/client/request/done');
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
      clientId: 1,
      techId: request.techId,
    );

    final url = Uri.parse(ServerAPI.baseURL + '/client/request/refuse');
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
    final url = Uri.parse(ServerAPI.baseURL + '/client/request/complain');
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

  Future<List<Request>> searchRequests(
      int userId, String state, String query) async {
    final uri = Uri.parse(ServerAPI.baseURL + '/client/request/search').replace(
      queryParameters: {
        'id': userId.toString(),
        'state': state,
        'query': query,
      },
    );
    final response =
        await http.get(uri, headers: {'Authorization': 'Bearer $token'});

    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((json) => Request.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load search results ${response.statusCode}');
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
