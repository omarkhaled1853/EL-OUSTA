import 'dart:convert';

Request requestFromJson(String str) => Request.fromJson(json.decode(str));

String requestToJson(Request data) => json.encode(data.toJson());

class Request {
    int techId;
    String techName;
    String details;
    String location;
    String startTime;
    String endTime;

    Request({
        required this.techId,
        required this.techName,
        required this.details,
        required this.location,
        required this.startTime,
        required this.endTime,
    });

    factory Request.fromJson(Map<String, dynamic> json) => Request(
        techId: json["Tech_id"],
        techName: json["Tech_name"],
        details: json["details"],
        location: json["Location"],
        startTime: json["Start_time"],
        endTime: json["End_time"],
    );

    Map<String, dynamic> toJson() => {
        "Tech_id": techId,
        "Tech_name": techName,
        "details": details,
        "Location": location,
        "Start_time": startTime,
        "End_time": endTime,
    };
}
