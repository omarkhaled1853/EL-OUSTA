import 'dart:convert';

import 'package:elousta/client_requests/completed_requests.dart';
import 'package:elousta/client_requests/in_progress_requests.dart';
import 'package:elousta/client_requests/pending_requests.dart';
import 'package:elousta/client_requests/request_class.dart';
import 'package:elousta/client_requests/requests_filter.dart';
import 'package:elousta/client_requests/requests_search_bar.dart';
import 'package:elousta/client_requests/requests_sort.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

//TODO: will be taken from localStorage
const id = 3;

class RequestList extends StatefulWidget {
  final String state;
  final String endpoint;

  const RequestList({super.key, required this.state, required this.endpoint});

  @override
  State<RequestList> createState() => RequestListState();
}

class RequestListState extends State<RequestList> {
  List<Request> displayedRequests = [];
  String filterQuery = "";
  String searchQuery = "";
  String sortType = "none";
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchRequests();
  }

  Future<void> fetchRequests() async {
    setState(() {
      isLoading = true;
    });

    final url = Uri.parse(
        'http://10.3.75.138:8080/client/requests/${widget.endpoint}/$id');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final List<dynamic> jsonResponse = json.decode(response.body);
      setState(() {
        displayedRequests =
            jsonResponse.map((data) => Request.fromJson(data)).toList();
        isLoading = false;
      });
    } else {
      setState(() {
        displayedRequests = [];
        isLoading = false;
      });
    }
  }

  Future<void> applySearch() async {
    final url = Uri.parse('http://10.3.75.138:8083/client/requests/search');
    final body =
        json.encode({"id": id, "state": widget.state, "query": searchQuery});

    final response = await http
        .post(url, body: body, headers: {'Content-Type': 'application/json'});

    if (response.statusCode == 200) {
      final List<dynamic> jsonResponse = json.decode(response.body);
      setState(() {
        displayedRequests =
            jsonResponse.map((data) => Request.fromJson(data)).toList();
      });
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('Failed to search ${widget.state} requests'),
        backgroundColor: Colors.red,
      ));
    }
  }

  Future<void> applyFilter() async {
    final url = Uri.parse('http://10.3.75.138:8080/client/requests/filter');
    final body =
        json.encode({"id": id, "state": widget.state, "query": filterQuery});

    final response = await http
        .post(url, body: body, headers: {'Content-Type': 'application/json'});

    if (response.statusCode == 200) {
      final List<dynamic> jsonResponse = json.decode(response.body);
      setState(() {
        displayedRequests =
            jsonResponse.map((data) => Request.fromJson(data)).toList();
      });
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('Failed to apply filter for ${widget.state}'),
        backgroundColor: Colors.red,
      ));
    }
  }

  Future<void> applySort() async {
    final url = Uri.parse('http://10.3.75.138:8080/client/requests/sort');
    final body =
        json.encode({"id": id, "type": sortType, "state": widget.state});

    final response = await http
        .post(url, body: body, headers: {'Content-Type': 'application/json'});

    if (response.statusCode == 200) {
      final List<dynamic> jsonResponse = json.decode(response.body);
      setState(() {
        displayedRequests =
            jsonResponse.map((data) => Request.fromJson(data)).toList();
      });
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('Failed to sort ${widget.state} requests'),
        backgroundColor: Colors.red,
      ));
    }
  }

  @override
  Widget build(BuildContext context) {
    return isLoading
        ? const Center(child: CircularProgressIndicator())
        : displayedRequests.isEmpty
            ? const Center(
                child: Text("No Data", style: TextStyle(fontSize: 18)))
            : Column(
                children: [
                  RequestsSearchBar(onSearch: (value) {
                    searchQuery = value;
                    applySearch();
                  }),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      RequestsFilter(
                          filterQuery: filterQuery, applyFilter: applyFilter),
                      RequestsSort(sortType: sortType, applySort: applySort)
                    ],
                  ),
                  Expanded(
                      child:
                          _getRequestsWidget(widget.state, displayedRequests))
                ],
              );
  }
}

// Method to return the appropriate request widget based on the state
Widget _getRequestsWidget(String state, List<Request> requests) {
  switch (state) {
    case "pending":
      return Pendingrequests(pendingRequests: requests);
    case "inProgress":
      return InProgressRequests(inProgressRequests: requests);
    case "completed":
      return CompletedRequests(completedRequests: requests);
    default:
      return const Center(child: Text("No requests available"));
  }
}
