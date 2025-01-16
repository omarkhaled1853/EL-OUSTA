import 'package:el_ousta/client_requests/request_button.dart';
import 'package:el_ousta/client_requests/request_card.dart';
import 'package:el_ousta/client_requests/request_class.dart';
import 'package:flutter/material.dart';

import 'api_service.dart';

class InProgressRequests extends StatefulWidget {
  final List<Request> inProgressRequests;
  const InProgressRequests({super.key, required this.inProgressRequests});

  @override
  State<InProgressRequests> createState() => _InProgressRequestsState();
}

class _InProgressRequestsState extends State<InProgressRequests> {
  Future<void> cancelRequest(Request request) async {
    try {
      final ApiService apiService = ApiService();
      // Call API to cancel the request
      await apiService.cancelRequest(request);

      // On successful cancellation, remove the request from the list and update UI
      setState(() {
        widget.inProgressRequests.remove(request);
      });

      // Optionally, show success message
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Request canceled successfully!'),
          backgroundColor: Colors.red,
        ),
      );
    } catch (e) {
      // Handle cancellation error
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  Future<void> doneRequest(Request request) async {
    try {
      final ApiService apiService = ApiService();
      // Call API to cancel the request
      await apiService.doneRequest(request);

      // On successful cancellation, remove the request from the list and update UI
      setState(() {
        widget.inProgressRequests.remove(request);
      });

      // Optionally, show success message
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Request done successfully!'),
          backgroundColor: Colors.green,
        ),
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
    return widget.inProgressRequests.isEmpty
        ? const Center(child: Text("There isn't exist inProgress requests"))
        : ListView.builder(
            itemCount: widget.inProgressRequests.length,
            itemBuilder: (context, index) {
              final request = widget.inProgressRequests[index];
              return RequestCard(
                request: request,
                requestButtons: [
                  RequestButton(
                    text: "Cancel",
                    color: Colors.red,
                    icon: Icons.cancel,
                    onPressed: () => cancelRequest(request),
                  ),
                  RequestButton(
                    text: "Done",
                    color: Colors.green,
                    icon: Icons.check,
                    onPressed: () => doneRequest(request),
                  ),
                ],
                icon: const Icon(
                  Icons.work,
                  color: Colors.blue,
                ),
              );
            },
          );
  }
}
