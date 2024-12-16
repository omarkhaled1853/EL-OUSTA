import 'package:elousta/client_requests/request_button.dart';
import 'package:elousta/client_requests/request_card.dart';
import 'package:elousta/client_requests/request_class.dart';
import 'package:elousta/client_requests/requests_appbar.dart';
import 'package:flutter/material.dart';

class RequestsPage extends StatefulWidget {
  @override
  _RequestsPageState createState() => _RequestsPageState();
}

class _RequestsPageState extends State<RequestsPage> {
  List<Request> pendingRequests = List.generate(
    5,
    (index) => Request(
      id: index + 1,
      description: "Pending Task ${index + 1}",
      startDate: "2024-12-01",
      endDate: "2024-12-${index + 5}",
    ),
  );

  List<Request> inProgressRequests = List.generate(
    8,
    (index) => Request(
      id: index + 1,
      description: "In progress Task ${index + 1}",
      startDate: "2024-12-01",
      endDate: "2024-12-${index + 5}",
    ),
  );

  List<Request> completedRequests = List.generate(
    10,
    (index) => Request(
      id: index + 1,
      description: "Completed Task ${index + 1}",
      startDate: "2024-12-01",
      endDate: "2024-12-${index + 5}",
    ),
  );

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: const RequestsAppBar(),
        body: TabBarView(children: [
          ListView.builder(
            itemCount: pendingRequests.length,
            itemBuilder: (context, index) {
              final request = pendingRequests[index];
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
          ),
          ListView.builder(
            itemCount: pendingRequests.length,
            itemBuilder: (context, index) {
              final request = pendingRequests[index];
              return RequestCard(
                request: request,
                requestButtons: [
                  RequestButton(
                    text: "Cancel",
                    color: Colors.red,
                    icon: Icons.cancel,
                    onPressed: () {},
                  ),
                  RequestButton(
                    text: "Done",
                    color: Colors.green,
                    icon: Icons.check,
                    onPressed: () {},
                  ),
                ],
                icon: const Icon(
                  Icons.work,
                  color: Colors.blue,
                ),
              );
            },
          ),
          ListView.builder(
            itemCount: pendingRequests.length,
            itemBuilder: (context, index) {
              final request = pendingRequests[index];
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
          ),
        ]),
      ),
    );
  }
}
