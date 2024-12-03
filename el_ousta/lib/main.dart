import 'package:flutter/material.dart';
import 'user_profile.dart'; // Import the User Profile page
import 'technician_page.dart'; // Import the Technician Profile page
import 'homeclient.dart';
import 'techinican_home.dart';
import 'catchback.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Profile Navigation',
      theme: ThemeData(
        primarySwatch: Colors.purple,
      ),
      home: const HomePage(),
    );
  }
}

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Page'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // Button to navigate to User Profile Page
            // ElevatedButton(
            //   onPressed: () {
            //     Navigator.push(
            //       context,
            //       MaterialPageRoute(builder: (context) => const ProfilePage()),
            //     );
            //   },
            //   child: const Text('Go to User Profile'),
            // ),
            const SizedBox(height: 20),
            // Button to navigate to Technician Profile Page
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const TechnicianHome()),
                );
              },
              child: const Text('Go to Technician Home'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const ClientPage()),
                );
              },
              child: const Text('Go to client Home '),
            ),

          ],
        ),
      ),
    );
  }
}
