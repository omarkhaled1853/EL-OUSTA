class RequestStatusPayload {
  final int id;
  final int clientId;
  final int techId;

  RequestStatusPayload({
    required this.id,
    required this.clientId,
    required this.techId,
  });

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'clientId': clientId,
      'techId': techId,
    };
  }
}
