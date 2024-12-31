class ComplaintDTO {
  final int clientId;
  final int techId;
  final String complaintBody;

  ComplaintDTO({
    required this.clientId,
    required this.techId,
    required this.complaintBody,
  });

  Map<String, dynamic> toJson() {
    return {
      'clientId': clientId,
      'techId': techId,
      'complaintBody': complaintBody
    };
  }

  @override
  String toString() {
    return 'ComplaintDTO(clientId: $clientId, techId: $techId, complaintBody: $complaintBody)';
  }
}
