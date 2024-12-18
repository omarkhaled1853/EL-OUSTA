import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:demoapp/model/TechCard.dart';

Future<List<TechCard>> service_search(
    String searchQuery, String Profession) async {
  print(
      "Searching for technicians with query: $searchQuery in domain ID: $Profession");
  var client = http.Client();
  var uri = Uri.parse(
      "http://192.168.1.12:8088/client/home/searchbyname/$Profession");

  try {
    var response = await client.post(
      uri,
      headers: {'Content-Type': 'application/json'},
      body: searchQuery, // Send `searchQuery` as plain text
    );

    if (response.statusCode == 200) {
      print("Search successful. Parsing response...");
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
