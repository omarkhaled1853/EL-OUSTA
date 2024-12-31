import 'package:elousta/client_requests/api_service.dart';
import 'package:elousta/client_requests/completed_requests.dart';
import 'package:elousta/client_requests/in_progress_requests.dart';
import 'package:elousta/client_requests/pending_requests.dart';
import 'package:elousta/client_requests/request_class.dart';
import 'package:elousta/client_requests/requests_controller.dart';
import 'package:elousta/client_requests/requests_tabbar.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

//TODO: will be taken from localStorage
const id = 3;

class RequestsPage extends StatefulWidget {
  @override
  _RequestsPageState createState() => _RequestsPageState();
}

class _RequestsPageState extends State<RequestsPage>
    with SingleTickerProviderStateMixin {
  late RequestsController requestsController;

  @override
  void initState() {
    super.initState();
    requestsController = RequestsController(
      apiService: ApiService(),
      userId: id,
    );
    requestsController.initializeController(this);
  }

  @override
  void dispose() {
    requestsController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<RequestsController>(
      create: (_) => requestsController,
      child: Consumer<RequestsController>(
        builder: (context, controller, _) {
          return Scaffold(
            appBar: RequestsTabBar(controller: controller.tabController),
            body: TabBarView(
              controller: controller.tabController,
              children: [
                _buildRequestTab(
                  controller.currentRequests,
                  (requests) => Pendingrequests(pendingRequests: requests),
                ),
                _buildRequestTab(
                  controller.currentRequests,
                  (requests) =>
                      InProgressRequests(inProgressRequests: requests),
                ),
                _buildRequestTab(
                  controller.currentRequests,
                  (requests) => CompletedRequests(completedRequests: requests),
                )
              ],
            ),
          );
        },
      ),
    );
  }

  Widget _buildRequestTab(Future<List<Request>>? futureRequests,
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
