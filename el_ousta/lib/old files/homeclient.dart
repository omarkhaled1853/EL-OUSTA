import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/screens/userTechScreen.dart';
import 'package:flutter/material.dart';
import 'package:el_ousta/screens/user_profile.dart'; // Import the User Profile page
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import '../widgets/appBarWithNotification.dart';
import 'package:el_ousta/common/userTech.dart';
const storage = FlutterSecureStorage();
class ClientPage extends StatefulWidget {
  const ClientPage({Key? key}) : super(key: key);

  @override
  State<ClientPage> createState() => _ClientPageState();
}

class _ClientPageState extends State<ClientPage> {
  List<Map<String, dynamic>> services = [];
  bool isLoading = false;
  String? sortBy;
  late List<Map<String, dynamic>> filteredServices;

  @override
  void initState() {
    super.initState();
    fetchAllServices(); // Fetch all services when the page loads
    filteredServices = services; // Initially show all services
  }

  Future<void> fetchAllServices() async {
    setState(() {
      isLoading = true;
    });

    try {
      final response = await http.get(
        Uri.parse(ServerAPI.baseURL + 'client/home/'), // Replace with your API endpoint
      );

      if (response.statusCode == 200) {
        final List<dynamic> responseData = json.decode(response.body);
        setState(() {
          services = responseData.cast<Map<String, dynamic>>();
          filteredServices = services; // Reset filtered services to all data
        });
      } else {
        throw Exception('Failed to fetch all services');
      }
    } catch (error) {
      _showSnackBar('Error fetching all services: $error', Colors.red);
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> _applySearch(String query) async {
    setState(() {
      isLoading = true;
    });

    try {
      final response = await http.post(
        Uri.parse(ServerAPI.baseURL + 'client/home/search'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode( query),
      );

      if (response.statusCode == 200) {
        final List<dynamic> responseData = json.decode(response.body);

        setState(() {
          filteredServices = responseData.cast<Map<String, dynamic>>();
        });
      } else {
        throw Exception('Failed to fetch search results');
      }
    } catch (error) {
      _showSnackBar('Error searching services: $error', Colors.red);
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> _applyFilter(String attribute, String query) async {
    setState(() {
      isLoading = true;
    });

    try {
      final response = await http.post(
        Uri.parse(ServerAPI.baseURL + 'client/home/filter'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'field': attribute,
          'query': query,
        }),

      );

      if (response.statusCode == 200) {
        final List<dynamic> responseData = json.decode(response.body);

        setState(() {
          filteredServices = responseData.cast<Map<String, dynamic>>();
        });
      } else {
        throw Exception('Failed to apply filter');
      }
    } catch (error) {
      _showSnackBar('Error applying filter: $error', Colors.red);
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> _sortServices(String field) async {
    setState(() {
      isLoading = true;
    });

    try {
      final response = await http.post(
        Uri.parse(ServerAPI.baseURL + 'client/home/sort'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(field),
      );

      if (response.statusCode == 200) {
        final List<dynamic> responseData = json.decode(response.body);

        setState(() {
          filteredServices = responseData.cast<Map<String, dynamic>>();
        });
      } else {
        throw Exception('Failed to sort services');
      }
    } catch (error) {
      _showSnackBar('Error sorting services: $error', Colors.red);
    } finally {
      setState(() {
        isLoading = false;
      });
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
      appBar: NotificationScreen(type: Type.USER),
      body: Column(
        children: [
          // Search Bar and Actions
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    decoration: InputDecoration(
                      hintText: 'Search...',
                      filled: true,
                      fillColor: Colors.white,
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10),
                        borderSide: BorderSide.none,
                      ),
                      contentPadding: const EdgeInsets.symmetric(horizontal: 16),
                    ),
                    onSubmitted: (value) {
                      _applySearch(value);
                    },
                  ),
                ),
                IconButton(
                  icon: const Icon(Icons.refresh),
                  onPressed: fetchAllServices,
                ),
                IconButton(
                  icon: const Icon(Icons.filter_alt),
                  onPressed: () {
                    _showFilterDialog();
                  },
                ),
                PopupMenuButton<String>(
                  icon: const Icon(Icons.sort),
                  onSelected: (value) {
                    _sortServices(value);
                  },
                  itemBuilder: (context) => [
                    const PopupMenuItem(
                      value: 'rate',
                      child: Text('Sort by Rate'),
                    ),
                    const PopupMenuItem(
                      value: 'experience',
                      child: Text('Sort by Experience'),
                    ),
                  ],
                ),
              ],
            ),
          ),
          // Body
          Expanded(
            child: isLoading
                ? const Center(child: CircularProgressIndicator())
                : filteredServices.isNotEmpty
                ? GridView.builder(
              itemCount: filteredServices.length,
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2,
                crossAxisSpacing: 5,
                mainAxisSpacing: 10,
                childAspectRatio: 1,
              ),
              itemBuilder: (context, index) {
                final service = filteredServices[index];
                print(service);
                return Container(
                  decoration: BoxDecoration(
                    color: Colors.grey.shade200,
                    borderRadius: BorderRadius.circular(10),
                    border: Border.all(color: Colors.purple, width: 1),
                  ),
                  padding: const EdgeInsets.symmetric(vertical: 10.0,horizontal: 5.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(service['domain']),
                      Text('Tech: ${service['fname']} ${service['lname']}'),
                      Text('Governorate: ${service['governorate']}'),
                      Text('district: ${service['district']}'),
                      Text('Rate: ${service['rate']} ⭐'),
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

  void _showFilterDialog() {
    final TextEditingController queryController = TextEditingController();
    String? selectedAttribute;
    final List<String> attributes = ['domain', 'district', 'governorate', 'rate'];

    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Filter Services'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            DropdownButtonFormField<String>(
              decoration: const InputDecoration(labelText: 'Attribute'),
              items: attributes.map((attribute) {
                return DropdownMenuItem<String>(
                  value: attribute,
                  child: Text(attribute),
                );
              }).toList(),
              onChanged: (value) {
                setState(() {
                  selectedAttribute = value;
                });
              },
            ),
            const SizedBox(height: 10),
            TextField(
              controller: queryController,
              decoration: const InputDecoration(labelText: 'Query'),
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context);
            },
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () {
              if (selectedAttribute != null && queryController.text.trim().isNotEmpty) {
                Navigator.pop(context);
                _applyFilter(selectedAttribute!, queryController.text.trim());
              } else {
                _showSnackBar('Please select an attribute and enter a query', Colors.red);
              }
            },
            child: const Text('Apply'),
          ),
        ],
      ),
    );
  }
}
