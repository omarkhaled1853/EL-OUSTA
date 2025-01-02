import 'package:el_ousta/client_requests/request_button.dart';
import 'package:el_ousta/client_requests/request_class.dart';
import 'package:flutter/material.dart';

class RequestCard extends StatelessWidget {
  final Request request;
  final List<RequestButton> requestButtons;
  final Icon icon;

  const RequestCard(
      {super.key,
      required this.request,
      required this.requestButtons,
      required this.icon});

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 4,
      margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                icon,
                const SizedBox(width: 8),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      request.description,
                      style: const TextStyle(
                          fontWeight: FontWeight.bold, fontSize: 16),
                    ),
                    const SizedBox(height: 8),
                    Text('Location ${request.location}'),
                    const SizedBox(height: 8),
                    Text('Start: ${request.startDate}'),
                    Text('End: ${request.endDate}'),
                  ],
                )
              ],
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: requestButtons.length == 1
                  ? MainAxisAlignment.end
                  : MainAxisAlignment.spaceBetween,
              children: requestButtons,
            ),
          ],
        ),
      ),
    );
  }
}
