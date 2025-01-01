import 'package:elousta/client_requests/requests_controller.dart';
import 'package:flutter/material.dart';

class RequestsSearchBar extends StatefulWidget {
  final RequestsController controller;
  const RequestsSearchBar({super.key, required this.controller});

  @override
  State<RequestsSearchBar> createState() => _RequestsSearchBarState();
}

class _RequestsSearchBarState extends State<RequestsSearchBar> {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: TextField(
        decoration: InputDecoration(
          hintText: "Search requests...",
          prefixIcon: const Icon(Icons.search),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8),
          ),
        ),
        onChanged: (value) {
          if (value.isEmpty) {
            widget.controller
                .resetSearch(); // Reset search when query is cleared
          } else {
            widget.controller.searchRequests(value); // Perform search
          }
        },
      ),
    );
  }
}
