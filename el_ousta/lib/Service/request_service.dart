import 'dart:convert';
import 'package:el_ousta/API/serverAPI.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:intl/intl.dart'; // For date formatting

class RequestService {
  final String baseUrl ;

  RequestService({required this.baseUrl});

  Future<bool> sendRequest(
      int userId,
      int techId,
      String description,
      String location,
      DateTime startDate,
      DateTime endDate,
      BuildContext context,
      String token) async {
    // Format the dates to 'dd/MM/yyyy' format
    String formattedStartDate = DateFormat('dd/MM/yyyy').format(startDate);
    String formattedEndDate = DateFormat('dd/MM/yyyy').format(endDate);

    // Construct the DTO
    final requestDto = {
      'userid': userId,
      'techid': techId,
      'description': description,
      'location': location,
      'state': 'Pending', // You can set the state dynamically if needed
      'startdate': formattedStartDate,
      'enddate': formattedEndDate,
    };
    print(token);
    final response = await http.post(
      Uri.parse(ServerAPI.baseURL + '/client/request/addRequest'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token'
      },
      body: json.encode(requestDto),
    );

    if (response.statusCode == 200) {
      // Successfully sent the request
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Request successfully sent!')),
      );
      return true;
    } else {
      // Error occurred while sending the request
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to send the request.')),
      );
      return false;
    }
  }
}
