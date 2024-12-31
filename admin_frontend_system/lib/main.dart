import 'package:flutter/material.dart';
import 'package:admin_frontend_system/Service/services_page.dart';
import 'package:admin_frontend_system/Service/professions_page.dart'; // Import the professions page

void main() {
  runApp(const ElOstaApp());
}

class ElOstaApp extends StatelessWidget {
  const ElOstaApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: const MainNavigation(), // Use MainNavigation as the home
    );
  }
}

class MainNavigation extends StatefulWidget {
  const MainNavigation({Key? key}) : super(key: key);

  @override
  State<MainNavigation> createState() => _MainNavigationState();
}

class _MainNavigationState extends State<MainNavigation> {
  int _selectedIndex = 0;

  // Define the pages
  final List<Widget> _pages = [
    const ServicesPage(),
    const ProfessionsPage(),
    const Center(child: Text('Requests Page Placeholder')),
    const Center(child: Text('Complains Page Placeholder')),
    const Center(child: Text('Users page Placeholder')),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pages[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        selectedItemColor: const Color.fromARGB(255, 1, 125, 187),
        unselectedItemColor: Colors.grey,
        onTap: _onItemTapped,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.work), label: 'Professions'),
          BottomNavigationBarItem(icon: Icon(Icons.request_page), label: 'Requests'),
          BottomNavigationBarItem(icon: Icon(Icons.error), label: 'Complains'),
          BottomNavigationBarItem(icon: Icon(Icons.supervised_user_circle_sharp), label: 'Users'),
        ],
      ),
    );
  }
}
