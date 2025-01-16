import 'package:el_ousta/API/serverAPI.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:el_ousta/models/TechCard.dart';

import '../models/TechCard.dart';

Future<List<TechCard>> service_search(
    String searchQuery, String Profession, String token) async {
  print(
      "Searching for technicians with query: $searchQuery in domain ID: $Profession");
  var client = http.Client();
  var uri = Uri.parse(
      "${ServerAPI.baseURL}/client/home/searchbyname/$Profession");

  try {
    print(token);
    print("كلمة جنبه");
    var response = await client.post(
      uri,
      headers: {'Content-Type': 'application/json',
        'Authorization': 'Bearer $token'
      },
      body: searchQuery, // Send `searchQuery` as plain text
    );

    if (response.statusCode == 200) {
      print("Search successful. Parsing response...");
      print(response.body);
      return techCardFromJson(response.body);
    } else {
      print(
          "Failed to search technicians. HTTP Status Code: ${response.statusCode}");
      print("Response body: ${response.body}");
      return [];
    }
  } catch (e) {
    print("Error during service search: $e");
    return [];
  } finally {
    client.close();
  }
}
