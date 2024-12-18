import 'dart:convert';

List<TechCard> techCardFromJson(String str) =>
    List<TechCard>.from(json.decode(str).map((x) => TechCard.fromJson(x)));

String techCardToJson(List<TechCard> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class TechCard {
  int techId;
  String techName;
  String location;
  int experienceYears; // This should be an integer, not a string
  String rating;

  TechCard({
    required this.techId,
    required this.techName,
    required this.location,
    required this.experienceYears,
    required this.rating,
  });

  factory TechCard.fromJson(Map<String, dynamic> json) => TechCard(
        techId: json["techId"], // Matches the JSON response
        techName: json["techName"],
        location: json["location"],
        experienceYears: json["experienceYears"], // Corrected key
        rating: json["rating"],
      );

  Map<String, dynamic> toJson() => {
        "techId": techId,
        "techName": techName,
        "location": location,
        "experienceYears": experienceYears,
        "rating": rating,
      };
}
