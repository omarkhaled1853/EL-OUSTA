import 'package:elousta/client_requests/requests_page.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const RequestsApp());
}

class RequestsApp extends StatelessWidget {
  const RequestsApp({super.key});
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: RequestsPage(),
    );
  }
}
