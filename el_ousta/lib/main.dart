

import 'package:el_ousta/screens/homeClientScreen.dart';
import 'package:el_ousta/screens/userTechScreen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
const secureStorage = FlutterSecureStorage();

// Function to check if the token exists
Future<bool> isUserLoggedIn() async {
  String? token = await secureStorage.read(key: 'auth_token');
  print(token);
  return token != null;
}

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized(); // Ensures async operations before `runApp`
  dynamic isLoggedIn = await isUserLoggedIn();
  runApp(MyApp(isLoggedIn: isLoggedIn,));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key, required this.isLoggedIn});
  final bool isLoggedIn;
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'elousta',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: (isLoggedIn)
          ? const ClientPage()
          : const UserTechScreen(),
    );
  }
}


