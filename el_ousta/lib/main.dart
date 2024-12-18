
import 'package:el_ousta/old%20files/homeclient.dart';
import 'package:el_ousta/screens/userTechScreen.dart';
import 'package:el_ousta/widgets/CheckoutButton.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:flutter_stripe/flutter_stripe.dart';
const secureStorage = FlutterSecureStorage();

// Function to check if the token exists
Future<bool> isUserLoggedIn() async {
  String? token = await secureStorage.read(key: 'auth_token');
  print(token);
  String? id = await secureStorage.read(key: 'id');
  print(id);
  return token != null;
}

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized(); // Ensures async operations before `runApp`
  dynamic isLoggedIn = await isUserLoggedIn();
  Stripe.publishableKey = 'pk_test_51QW6k4HqAbjkHma4anDeYAXeeQuB2HFerQTm6VNyWDJvsoL9bgXCUnlCM50Bvbgb62XK82dkObVYMvPl0bSeavOw00Ufngx3aT';
  runApp(MyApp(isLoggedIn: isLoggedIn,));
  // runApp(MaterialApp(
  //   home: Scaffold(appBar: AppBar(), body: const Padding(
  //     padding: EdgeInsets.all(8.0),
  //     child: CheckoutButton(),
  //   )),
  // ));
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


