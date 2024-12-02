import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class TestScreen extends StatelessWidget {
  final dynamic id;

  const TestScreen({super.key, required this.id});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(60),
        child: Text(id, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 18.0),),
      ),
    );
  }
}
