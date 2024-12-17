import 'package:elousta/client_requests/api_service.dart';
import 'package:elousta/client_requests/completed_requests.dart';
import 'package:elousta/client_requests/in_progress_requests.dart';
import 'package:elousta/client_requests/pending_requests.dart';
import 'package:elousta/client_requests/request_class.dart';
import 'package:elousta/client_requests/requests_appbar.dart';
import 'package:flutter/material.dart';

//TODO: will be taken from localStorage
const id = 3;

class RequestsPage extends StatefulWidget {
  @override
  _RequestsPageState createState() => _RequestsPageState();
}

class _RequestsPageState extends State<RequestsPage> {
  final ApiService apiService = ApiService();

  late Future<List<Request>> pendingRequests;
  late Future<List<Request>> inProgressRequests;
  late Future<List<Request>> completedRequests;

  @override
  void initState() {
    super.initState();
    pendingRequests = apiService.fetchPendingRequests(id);
    inProgressRequests = apiService.fetchInProgressRequests(id);
    completedRequests = apiService.fetchCompletedRequests(id);
  }

  // List<Request> pendingRequests = List.generate(
  //   5,
  //   (index) => Request(
  //     id: index + 1,
  //     techId: index,
  //     location: "location $index",
  //     description: "Pending Task ${index + 1}",
  //     startDate: "2024-12-01",
  //     endDate: "2024-12-${index + 5}",
  //   ),
  // );

  // List<Request> inProgressRequests = List.generate(
  //   8,
  //   (index) => Request(
  //     id: index + 1,
  //     techId: index,
  //     location: "location $index",
  //     description: "Pending Task ${index + 1}",
  //     startDate: "2024-12-01",
  //     endDate: "2024-12-${index + 5}",
  //   ),
  // );

  // List<Request> completedRequests = List.generate(
  //   10,
  //   (index) => Request(
  //     id: index + 1,
  //     techId: index,
  //     location: "location $index",
  //     description: "Pending Task ${index + 1}",
  //     startDate: "2024-12-01",
  //     endDate: "2024-12-${index + 5}",
  //   ),
  // );

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: const RequestsAppBar(),
        body: TabBarView(children: [
          _buildRequestTab(
            pendingRequests,
            (requests) => Pendingrequests(pendingRequests: requests),
          ),
          _buildRequestTab(
            inProgressRequests,
            (requests) => InProgressRequests(inProgressRequests: requests),
          ),
          _buildRequestTab(
            completedRequests,
            (requests) => CompletedRequests(completedRequests: requests),
          ),
        ]),
      ),
    );
  }

  Widget _buildRequestTab(Future<List<Request>> futureRequests,
      Widget Function(List<Request>) customWidget) {
    return FutureBuilder<List<Request>>(
      future: futureRequests,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text('Error: ${snapshot.error}'));
        } else if (snapshot.hasData) {
          final requests = snapshot.data!;
          return customWidget(requests);
        } else {
          return const Center(child: Text("No requests available"));
        }
      },
    );
  }
}
