import 'dart:convert';

import 'package:demoapp/model/TechCard.dart';
import 'package:http/http.dart' as http;

class Gettechcards {
  Future<List<TechCard>?> getcardsinstarting() async {
    List<TechCard> testData = [];
    var client = http.Client();
    var uri = Uri.parse("http://192.168.1.12:8088/client/home/filtercard");

    try {
      // The body must contain the "field" and "query" parameters for filtering
      var requestBody = jsonEncode({"field": "Domain", "query": "Electrical"});

      var response = await client.post(
        uri,
        headers: {'Content-Type': 'application/json'},
        body: requestBody,
      );

      if (response.statusCode == 200) {
        print(response.body);
        print("Filter successful. Parsing response...");
        print(techCardFromJson(response.body));
        return techCardFromJson(response.body); // Parse JSON to List<TechCard>
      } else {
        print(
            "Failed to fetch technicians. Status code: ${response.statusCode}");
        print("Response body: ${response.body}");
        return [];
      }
    } catch (e) {
      print("Error during fetching technicians: $e");
      return testData;
    }
  }
}
