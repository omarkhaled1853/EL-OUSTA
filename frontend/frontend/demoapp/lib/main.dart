import 'package:demoapp/profesions.dart';
import 'package:flutter/material.dart';

//// appbar in main
void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'EL Osta',
      theme: ThemeData(
        scaffoldBackgroundColor: Colors.white,
      ),
      home: const ProfessionsScreen(professionType: "Electrical"),
    );
  }
}
