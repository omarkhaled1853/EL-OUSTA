import 'package:el_ousta/client_requests/api_service.dart';
import 'package:el_ousta/client_requests/request_button.dart';
import 'package:el_ousta/client_requests/request_card.dart';
import 'package:el_ousta/client_requests/request_class.dart';
import 'package:flutter/material.dart';

class Pendingrequests extends StatefulWidget {
  final List<Request> pendingRequests;

  const Pendingrequests({
    super.key,
    required this.pendingRequests,
  });

  @override
  State<Pendingrequests> createState() => _PendingrequestsState();
}

class _PendingrequestsState extends State<Pendingrequests> {
  // Function to handle cancel request and update the UI
  Future<void> cancelRequest(Request request) async {
    try {
      final ApiService apiService = ApiService();
      // Call API to cancel the request
      await apiService.cancelRequest(request);

      // On successful cancellation, remove the request from the list and update UI
      setState(() {
        widget.pendingRequests.remove(request);
      });

      // Optionally, show success message
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
            content: Text('Request canceled successfully!'),
            backgroundColor: Colors.red),
      );
    } catch (e) {
      // Handle cancellation error
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: widget.pendingRequests.length,
      itemBuilder: (context, index) {
        final request = widget.pendingRequests[index];
        return RequestCard(
          request: request,
          requestButtons: [
            RequestButton(
              text: "Cancel",
              color: Colors.red,
              icon: Icons.cancel,
              onPressed: () => cancelRequest(request),
            ),
          ],
          icon: const Icon(
            Icons.pending,
            color: Colors.orange,
          ),
        );
      },
    );
  }
}
