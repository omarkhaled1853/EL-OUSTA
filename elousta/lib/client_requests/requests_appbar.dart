import 'package:flutter/material.dart';

class RequestsAppBar extends StatelessWidget implements PreferredSizeWidget {
  const RequestsAppBar({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: const Text('Requests'),
      bottom: const TabBar(
        indicatorColor: Colors.white,
        tabs: [
          Tab(icon: Icon(Icons.pending), text: "Pending"),
          Tab(icon: Icon(Icons.work), text: "In Progress"),
          Tab(icon: Icon(Icons.done), text: "Completed"),
        ],
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight + 48.0);
}
