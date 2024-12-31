import 'package:flutter/material.dart';

class RequestsSearchBar extends StatefulWidget {
  const RequestsSearchBar({super.key});

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
          // searchQuery = value;
          // applySearch();
        },
      ),
    );
  }
}
