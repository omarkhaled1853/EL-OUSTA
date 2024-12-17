import 'package:elousta/client_requests/request_button.dart';
import 'package:elousta/client_requests/request_card.dart';
import 'package:elousta/client_requests/request_class.dart';
import 'package:flutter/material.dart';

class InProgressRequests extends StatefulWidget {
  final List<Request> inProgressRequests;
  const InProgressRequests({super.key, required this.inProgressRequests});

  @override
  State<InProgressRequests> createState() => _InProgressRequestsState();
}

class _InProgressRequestsState extends State<InProgressRequests> {
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
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
              onPressed: () {},
            ),
            RequestButton(
              text: "Done",
              color: Colors.green,
              icon: Icons.check,
              onPressed: () {},
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
