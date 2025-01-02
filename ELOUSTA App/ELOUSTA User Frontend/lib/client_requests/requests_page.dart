import 'package:el_ousta/client_requests/api_service.dart';
import 'package:el_ousta/client_requests/completed_requests.dart';
import 'package:el_ousta/client_requests/in_progress_requests.dart';
import 'package:el_ousta/client_requests/pending_requests.dart';
import 'package:el_ousta/client_requests/request_class.dart';
import 'package:el_ousta/client_requests/request_list.dart';
import 'package:el_ousta/client_requests/requests_controller.dart';
import 'package:el_ousta/client_requests/requests_tabbar.dart';
import 'package:el_ousta/main.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:el_ousta/common/userTech.dart';
import '../widgets/appBarWithNotification.dart';

//TODO: will be taken from localStorage

class RequestsPage extends StatefulWidget {
  @override
  _RequestsPageState createState() => _RequestsPageState();
}

class _RequestsPageState extends State<RequestsPage>
    with SingleTickerProviderStateMixin {
  late RequestsController requestsController;
  static int id = 1;

  void initId() async {
    String idString = (await secureStorage.read(key: 'id'))! as String;
    id = int.parse(idString);
  }

  @override
  void initState() {
    super.initState();
    initId();
    print(id);
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
            appBar: const NotificationScreen(
              type: Type.USER,
            ),
            body: Scaffold(
              appBar: RequestsTabBar(controller: controller.tabController),
              body: TabBarView(
                controller: controller.tabController,
                children: [
                  _buildRequestTab(
                    controller.currentRequests,
                    (requests) => RequestList(
                        controller: requestsController,
                        requests: Pendingrequests(
                            pendingRequests: controller.isSearching
                                ? controller.searchResults
                                : requests)),
                  ),
                  _buildRequestTab(
                    controller.currentRequests,
                    (requests) => RequestList(
                        controller: requestsController,
                        requests: InProgressRequests(
                            inProgressRequests: controller.isSearching
                                ? controller.searchResults
                                : requests)),
                  ),
                  _buildRequestTab(
                    controller.currentRequests,
                    (requests) => RequestList(
                        controller: requestsController,
                        requests: CompletedRequests(
                            completedRequests: controller.isSearching
                                ? controller.searchResults
                                : requests)),
                  )
                ],
              ),
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
          final requests = requestsController.isSearching
              ? requestsController.searchResults
              : snapshot.data!;
          return customWidget(requests);
        } else {
          return const Center(child: Text("No requests available"));
        }
      },
    );
  }
}
