import 'package:elousta/client_requests/request_list.dart';
import 'package:flutter/material.dart';

class RequestsAppBar extends StatelessWidget implements PreferredSizeWidget {
  const RequestsAppBar({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: const Text('Requests'),
      bottom: TabBar(
        indicatorColor: Colors.white,
        tabs: const [
          Tab(icon: Icon(Icons.pending), text: "Pending"),
          Tab(icon: Icon(Icons.work), text: "In Progress"),
          Tab(icon: Icon(Icons.done), text: "Completed"),
        ],
        // onTap: (index) {
        //   // Call fetchRequests() when a tab is tapped
        //   final requestList =
        //       context.findAncestorStateOfType<RequestListState>();
        //   if (requestList != null) {
        //     requestList.fetchRequests();
        //   }
        // },
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight + 48.0);
}
