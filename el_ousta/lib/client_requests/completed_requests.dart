import 'dart:convert';

import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/client_requests/request_button.dart';
import 'package:el_ousta/client_requests/request_card.dart';
import 'package:el_ousta/client_requests/request_class.dart';
import 'package:el_ousta/main.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'complaint_dialog.dart';

class CompletedRequests extends StatefulWidget {
  final List<Request> completedRequests;
  const CompletedRequests({super.key, required this.completedRequests});

  @override
  State<CompletedRequests> createState() => _CompletedRequestsState();
}

class _CompletedRequestsState extends State<CompletedRequests> {
  Future<void> _showRatingDialog(Request request) async {
    double rating = 3.0; // Default rating
    TextEditingController commentsController = TextEditingController();

    await showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Rate the Request'),
          content: StatefulBuilder(
            builder: (context, setState) {
              return Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text('Rate the request from 1 to 5'),
                  Slider(
                    value: rating,
                    min: 1,
                    max: 5,
                    divisions: 4,
                    label: rating.toStringAsFixed(0),
                    onChanged: (value) {
                      setState(() {
                        rating = value;
                      });
                    },
                  ),
                  const SizedBox(height: 10),
                  TextField(
                    controller: commentsController,
                    decoration: const InputDecoration(
                      labelText: 'Comments (optional)',
                      border: OutlineInputBorder(),
                    ),
                    maxLines: 3,
                  ),
                ],
              );
            },
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () async {
                await _submitRating(
                  request.id,
                  request.techId,
                  rating.toInt(),
                  commentsController.text,
                );
                Navigator.pop(context);
              },
              child: const Text('Submit'),
            ),
          ],
        );
      },
    );
  }

  Future<void> _submitRating(
      int requestId, int techId, int rating, String comments) async {
    final url = Uri.parse(ServerAPI.baseURL + '/client/feedback');
    String token = (await secureStorage.read(key: 'auth_token')) as String;
    String idString = (await secureStorage.read(key: 'id')) as String;
    int id = int.parse(idString);
    try {
      final response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token'
        },
        body: jsonEncode({
          'clientId': id,
          'techId': techId,
          'rate': rating,
          'commentBody': comments,
        }),
      );
      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Rating submitted successfully!')),
        );
      } else {
        print(response.statusCode);
        print(response.body);
        throw Exception('Failed to submit rating');
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return widget.completedRequests.isEmpty ? const Center(child: Text("There isn't exist completed requests")) : ListView.builder(
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
              onPressed: () => _showRatingDialog(request),
            ),
            RequestButton(
              text: "Complaint",
              color: Colors.redAccent,
              icon: Icons.report,
              onPressed: () {
                showDialog(
                  context: context,
                  barrierDismissible: true,
                  barrierColor:
                      Colors.black.withOpacity(0.5), // Set background opacity
                  builder: (BuildContext context) {
                    return ComplaintDialog(
                      clientId: request.clientId,
                      techId: request.techId,
                    );
                  },
                );
              },
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
