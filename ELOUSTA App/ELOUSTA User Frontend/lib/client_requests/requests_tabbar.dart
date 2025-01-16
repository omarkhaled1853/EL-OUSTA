import 'package:flutter/material.dart';

class RequestsTabBar extends StatelessWidget implements PreferredSizeWidget {
  final TabController controller;

  const RequestsTabBar({super.key, required this.controller});

  @override
  Widget build(BuildContext context) {
    return TabBar(
      controller: controller,
      indicatorColor: Colors.white,
      tabs: const [
        Tab(icon: Icon(Icons.pending), text: "Pending"),
        Tab(icon: Icon(Icons.work), text: "In Progress"),
        Tab(icon: Icon(Icons.done), text: "Completed"),
      ],
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight + 48.0);
}
