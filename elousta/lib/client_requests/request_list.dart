import 'package:elousta/client_requests/requests_search_bar.dart';
import 'package:flutter/material.dart';

class RequestList extends StatefulWidget {
  final Widget requests;

  const RequestList({
    super.key,
    required this.requests,
  });

  @override
  State<RequestList> createState() => _RequestListState();
}

class _RequestListState extends State<RequestList> {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [const RequestsSearchBar(), Expanded(child: widget.requests)],
    );
  }
}
