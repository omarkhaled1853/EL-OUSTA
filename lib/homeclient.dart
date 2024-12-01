import 'package:flutter/material.dart';
import 'user_profile.dart'; // Import the User Profile page
import 'dart:convert';
import 'package:http/http.dart' as http;

class ClientPage extends StatefulWidget {
  const ClientPage({Key? key}) : super(key: key);

  @override
  State<ClientPage> createState() => _ClientPageState();
}

class _ClientPageState extends State<ClientPage> {
  List<Map<String, dynamic>> services = [
    {
      'serviceName': 'Plumbing',
      'techName': 'John Doe',
      'location': 'Downtown',
      'rate': 20, // Rate as integer
    },
    {
      'serviceName': 'Electrical',
      'techName': 'Jane Smith',
      'location': 'City Center',
      'rate': 25,
    },
    {
      'serviceName': 'Cleaning',
      'techName': 'Emily Davis',
      'location': 'Uptown',
      'rate': 15,
    },
    {
      'serviceName': 'Gardening',
      'techName': 'Mike Johnson',
      'location': 'West End',
      'rate': 30,
    },
    {
      'serviceName': 'Painting',
      'techName': 'Sarah Brown',
      'location': 'North Area',
      'rate': 40,
    },
    {
      'serviceName': 'Carpentry',
      'techName': 'Paul Wilson',
      'location': 'South Area',
      'rate': 35,
    },
  ];
  bool isLoading = true;
  String searchQuery = '';
  late List<Map<String, dynamic>> filteredServices;

  @override
  void initState() {
    super.initState();
    fetchClientData(); // Fetch data when page loads
    filteredServices = services; // Initially show all services
  }
  Future<void> fetchClientData() async {
    try {
      final response = await http.get(Uri.parse('https://example.com/api/client'));
      if (response.statusCode == 200) {
        setState(() {
          services = json.decode(response.body); // Parse the fetched data
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
      filteredServices = services.where((service) {
        // Check all fields for matches, including `rate` as integer
        final rateString = service['rate'].toString(); // Convert rate to string for searching
        return service['serviceName']!.toLowerCase().contains(searchQuery) ||
            service['techName']!.toLowerCase().contains(searchQuery) ||
            service['location']!.toLowerCase().contains(searchQuery) ||
            rateString.contains(searchQuery);
      }).toList();
    });
  }

  int _currentIndex = 0;

  void onTabTapped(int index) {
    if (index == 3) {
      // Navigate to ProfilePage if "Profile" tab is selected
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const ProfilePage()),
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
        title: const Text('El Osta'),
        backgroundColor: Colors.purple,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          :Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            // Search bar
            TextField(
              onChanged: updateSearch,
              decoration: InputDecoration(
                hintText: 'Search services...',
                prefixIcon: const Icon(Icons.search),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8.0),
                ),
              ),
            ),
            const SizedBox(height: 16),
            Expanded(
              child: filteredServices.isNotEmpty
                  ? GridView.builder(
                itemCount: filteredServices.length,
                gridDelegate:
                const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  crossAxisSpacing: 10,
                  mainAxisSpacing: 10,
                  childAspectRatio: 1,
                ),
                itemBuilder: (context, index) {
                  final service = filteredServices[index];
                  return Container(
                    decoration: BoxDecoration(
                      color: Colors.grey.shade200,
                      borderRadius: BorderRadius.circular(10),
                      border:
                      Border.all(color: Colors.purple, width: 1),
                    ),
                    padding: const EdgeInsets.all(10),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        buildHighlightedText(
                            service['serviceName']!, searchQuery),
                        const SizedBox(height: 5),
                        buildHighlightedText(
                            'Tech: ${service['techName']}', searchQuery),
                        buildHighlightedText(
                            'Location: ${service['location']}',
                            searchQuery),
                        buildHighlightedText(
                            'Rate: ${service['rate']} ⭐', searchQuery),
                      ],
                    ),
                  );
                },
              )
                  : const Center(
                child: Text(
                  'No results found',
                  style: TextStyle(fontSize: 18, color: Colors.grey),
                ),
              ),
            ),
          ],
        ),
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

  Widget buildHighlightedText(String text, String query) {
    if (query.isEmpty) {
      return Text(
        text,
        style: const TextStyle(fontSize: 14),
      );
    }

    final RegExp regex = RegExp(RegExp.escape(query), caseSensitive: false);
    final matches = regex.allMatches(text);
    if (matches.isEmpty) {
      return Text(
        text,
        style: const TextStyle(fontSize: 14),
      );
    }

    List<TextSpan> spans = [];
    int lastMatchEnd = 0;

    for (final match in matches) {
      if (match.start > lastMatchEnd) {
        spans.add(TextSpan(
          text: text.substring(lastMatchEnd, match.start),
        ));
      }
      spans.add(TextSpan(
        text: text.substring(match.start, match.end),
        style: const TextStyle(
          fontWeight: FontWeight.bold,
          color: Colors.purple,
        ),
      ));
      lastMatchEnd = match.end;
    }

    if (lastMatchEnd < text.length) {
      spans.add(TextSpan(
        text: text.substring(lastMatchEnd),
      ));
    }

    return RichText(
      text: TextSpan(
        style: const TextStyle(color: Colors.black, fontSize: 14),
        children: spans,
      ),
    );
  }
}
