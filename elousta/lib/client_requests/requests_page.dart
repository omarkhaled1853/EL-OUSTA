import 'package:elousta/client_requests/pending_requests.dart';
import 'package:elousta/client_requests/request_class.dart';
import 'package:elousta/client_requests/request_list.dart';
import 'package:elousta/client_requests/requests_appbar.dart';
import 'package:flutter/material.dart';

//TODO: will be taken from localStorage
const id = 3;

class RequestsPage extends StatefulWidget {
  @override
  _RequestsPageState createState() => _RequestsPageState();
}

class _RequestsPageState extends State<RequestsPage> {
  List<Request> pendingRequests = List.generate(
    5,
    (index) => Request(
      id: index + 1,
      techId: index,
      location: "location $index",
      description: "Pending Task ${index + 1}",
      startDate: "2024-12-01",
      endDate: "2024-12-${index + 5}",
    ),
  );

  List<Request> inProgressRequests = List.generate(
    8,
    (index) => Request(
      id: index + 1,
      techId: index,
      location: "location $index",
      description: "Pending Task ${index + 1}",
      startDate: "2024-12-01",
      endDate: "2024-12-${index + 5}",
    ),
  );

  List<Request> completedRequests = List.generate(
    10,
    (index) => Request(
      id: index + 1,
      techId: index,
      location: "location $index",
      description: "Pending Task ${index + 1}",
      startDate: "2024-12-01",
      endDate: "2024-12-${index + 5}",
    ),
  );

  @override
  Widget build(BuildContext context) {
    return const DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: RequestsAppBar(),
        body: TabBarView(children: [
          RequestList(state: "PENDING", endpoint: "pending"),
          RequestList(state: "IN-PROGRESS", endpoint: "inProgress"),
          RequestList(state: "COMPLETED", endpoint: "completed"),
        ]),
      ),
    );
  }
}
