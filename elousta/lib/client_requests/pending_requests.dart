import 'package:elousta/client_requests/request_button.dart';
import 'package:elousta/client_requests/request_card.dart';
import 'package:elousta/client_requests/request_class.dart';
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
              onPressed: () {},
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
