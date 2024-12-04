import 'package:flutter/material.dart';
import 'technician_page.dart'; // Import the User Profile page
import 'dart:convert';
import 'package:http/http.dart' as http;

class TechnicianHome extends StatefulWidget {
  const TechnicianHome({Key? key}) : super(key: key);

  @override
  State<TechnicianHome> createState() => _TechnicianHomeState();
}

class _TechnicianHomeState extends State<TechnicianHome> {
List<Map<String, String>> pendingRequests = [
    {"name": "Client A", "date": "2024-11-25", "image": "assets/hossam.jpg"},
    {"name": "Client B", "date": "2024-11-26", "image": "assets/hossam.jpg"},
    {"name": "Client C", "date": "2024-11-27", "image": "assets/hossam.jpg"},
    {"name": "Client D", "date": "2024-11-28", "image": "assets/hossam.jpg"},
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
    final response = await http.get(Uri.parse('https://example.com/api/client'));
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
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 20),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    onChanged: updateSearch,
                    decoration: InputDecoration(
                      hintText: 'Search by Date or Name',
                      filled: true,
                      fillColor: Colors.grey.shade200,
                      prefixIcon: const Icon(Icons.search),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20),
                        borderSide: BorderSide.none,
                      ),
                    ),
                  ),
                ),
                const SizedBox(width: 10),
                Container(
                  decoration: const BoxDecoration(
                    shape: BoxShape.circle,
                    color: Colors.purple,
                  ),
                  child: IconButton(
                    icon: const Icon(Icons.arrow_forward, color: Colors.white),
                    onPressed: () {
                      print('Search query: $searchQuery');
                    },
                  ),
                )
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
                  name: request["name"]!,
                  date: request["date"]!,
                  image: request["image"]!,
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
    required String name,
    required String date,
    required String image,
  }) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      elevation: 3,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Row(
          children: [
            CircleAvatar(
              radius: 30,
              backgroundImage: AssetImage(image),
            ),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    name,
                    style: const TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 4),
                  Text(
                    date,
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
