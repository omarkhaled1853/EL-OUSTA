import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/screens/userTechScreen.dart';
import 'package:flutter/material.dart';
import 'package:el_ousta/old%20files/technician_page.dart'; // Import the User Profile page
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
const storage = FlutterSecureStorage();
class TechnicianHome extends StatefulWidget {
  const TechnicianHome({super.key});

  @override
  State<TechnicianHome> createState() => _TechnicianHomeState();
}

class _TechnicianHomeState extends State<TechnicianHome> {
List<Map<String, String>> pendingRequests = [
    // {"state": "Client A", "startDate": "2024-11-25", "endDate": "assets/hossam.jpg"},
    // {"state": "Client B", "startDate": "2024-11-26", "endDate": "assets/hossam.jpg"},
    // {"state": "Client C", "startDate": "2024-11-27", "endDate": "assets/hossam.jpg"},
    // {"state": "Client D", "startDate": "2024-11-28", "endDate": "assets/hossam.jpg"},
  ];
bool isLoading = true;
  String searchQuery = '';

  late List<Map<String, String>> filteredRequests;

  @override
  void initState() {
    super.initState();
    fetchClientData(); // Fetch data when page loads
    filteredRequests = pendingRequests; // Initially show all pending requests
  }
Future<void> fetchClientData() async {
  try {
    final response = await http.get(Uri.parse('${ServerAPI.baseURL}/technician/Home'));
    if (response.statusCode == 200) {
      setState(() {
        pendingRequests = json.decode(response.body); // Parse the fetched data
        isLoading = false; // Loading completed
      });
    } else {
      throw Exception('Failed to load client data');
    }
  } catch (error) {
    setState(() {
      isLoading = false;
    });
    _showSnackBar('Failed to load data. Please try again later.', Colors.red);
  }
}
void _showSnackBar(String message, Color color) {
  ScaffoldMessenger.of(context).showSnackBar(
    SnackBar(
      content: Text(message),
      backgroundColor: color,
    ),
  );
}
  void updateSearch(String query) {
    setState(() {
      searchQuery = query.toLowerCase();
      filteredRequests = pendingRequests.where((request) {
        return request['name']!.toLowerCase().contains(searchQuery) ||
            request['date']!.toLowerCase().contains(searchQuery);
      }).toList();
    });
  }

  int _currentIndex = 0;

  void onTabTapped(int index) {
    if (index == 3) {
      // Navigate to ProfilePage if "Profile" tab is selected
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const TechnicianPage()),
      );
    } else {
      setState(() {
        _currentIndex = index; // Update the current index
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'EL Osta',
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
          ),
        ),
        centerTitle: true,
        backgroundColor: Colors.purple,
        actions: <Widget>[
          IconButton(
            icon: const Icon(
              Icons.logout,
              color: Colors.black,
            ),
            onPressed: () async {
              await storage.delete(key: 'auth_token');
              Navigator.of(context).pushReplacement(
                  MaterialPageRoute(
                      builder: (ctx) => const UserTechScreen()
                  )
              );
            },
          )
        ],
      ),
      body:isLoading
          ? const Center(child: CircularProgressIndicator())
      :Column(
        children: [
          const SizedBox(height: 10),
          Container(
            padding: const EdgeInsets.all(8.0),
            child: const Text(
              'Home',
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          Container(
            height: 40,
            margin: const EdgeInsets.symmetric(horizontal: 20),
            decoration: BoxDecoration(
              color: Colors.grey.shade300,
              borderRadius: BorderRadius.circular(20),
            ),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                _buildTabButton("Pending", isSelected: true),
              ],
            ),
          ),
          const SizedBox(height: 10),
          Expanded(
            child: filteredRequests.isNotEmpty
                ? ListView.builder(
              itemCount: filteredRequests.length,
              itemBuilder: (context, index) {
                final request = filteredRequests[index];
                return _buildRequestCard(
                  state: request["state"]!,
                  startDate: request["startDate"]!,
                  endDate: request["endDate"]!,
                );
              },
            )
                : const Center(
              child: Text(
                'No matching requests found',
                style: TextStyle(fontSize: 16, color: Colors.grey),
              ),
            ),
          ),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        selectedItemColor: Colors.purple,
        unselectedItemColor: Colors.grey,
        onTap: onTabTapped,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.work), label: 'Professions'),
          BottomNavigationBarItem(icon: Icon(Icons.list), label: 'Requests'),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Profile'),
        ],
      ),
    );
  }

  // Tab button widget
  Widget _buildTabButton(String title, {bool isSelected = false}) {
    return TextButton(
      onPressed: () {
        print('$title tab clicked');
      },
      style: TextButton.styleFrom(
        foregroundColor: isSelected ? Colors.white : Colors.black,
        backgroundColor: isSelected ? Colors.purple : Colors.transparent,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(20),
        ),
      ),
      child: Text(
        title,
        style: const TextStyle(fontSize: 14),
      ),
    );
  }

  // Request card widget
  Widget _buildRequestCard({
    required String state,
    required String startDate,
    required String endDate,
  }) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      elevation: 3,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Row(
          children: [
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "State: $state",
                    style: const TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 4),
                  Text(
                    "Start Date: $startDate",
                    style: const TextStyle(
                      fontSize: 14,
                      color: Colors.grey,
                    ),
                  ),
                  Text(
                    "End Date: $endDate",
                    style: const TextStyle(
                      fontSize: 14,
                      color: Colors.grey,
                    ),
                  ),
                ],
              ),
            ),
            const Icon(Icons.arrow_forward_ios, color: Colors.purple, size: 18),
          ],
        ),
      ),
    );
  }
}
