import 'package:elousta/client_requests/request_button.dart';
import 'package:elousta/client_requests/request_card.dart';
import 'package:elousta/client_requests/request_class.dart';
import 'package:flutter/material.dart';

class CompletedRequests extends StatefulWidget {
  final List<Request> completedRequests;
  const CompletedRequests({super.key, required this.completedRequests});

  @override
  State<CompletedRequests> createState() => _CompletedRequestsState();
}

class _CompletedRequestsState extends State<CompletedRequests> {
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: widget.completedRequests.length,
      itemBuilder: (context, index) {
        final request = widget.completedRequests[index];
        return RequestCard(
          request: request,
          requestButtons: [
            RequestButton(
              text: "Rating",
              color: Colors.amber,
              icon: Icons.star,
              onPressed: () {},
            ),
            RequestButton(
              text: "Complaint",
              color: Colors.redAccent,
              icon: Icons.report,
              onPressed: () {},
            ),
          ],
          icon: const Icon(
            Icons.done,
            color: Colors.green,
          ),
        );
      },
    );
  }
}
