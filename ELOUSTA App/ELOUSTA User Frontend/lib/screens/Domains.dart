import 'package:el_ousta/screens/profesions.dart';
import 'package:el_ousta/API/serverAPI.dart';
import 'package:flutter/material.dart';
import '../client_requests/requests_page.dart';
import '../widgets/appBarWithNotification.dart';
import 'user_profile.dart';
import 'package:el_ousta/screens/userTechScreen.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:el_ousta/common/userTech.dart';

const storage = FlutterSecureStorage();

class DomainPage extends StatefulWidget {
  const DomainPage({super.key});

  @override
  State<DomainPage> createState() => _DomainPageState();
}

class _DomainPageState extends State<DomainPage> {
  List<Map<String, dynamic>> domainList = []; // List to store DomainDTO data
  bool isLoading = false;
  @override
  void initState() {
    super.initState();
    fetchAllDomains();
  }

  // Fetch the DomainDTO list from the backend
  Future<void> fetchAllDomains() async {
    setState(() {
      isLoading = true;
    });
    String? token = await storage.read(key: 'auth_token');
    try {
      final response = await http.get(
          Uri.parse('${ServerAPI.baseURL}/client/home/'),
          headers: {'Authorization': 'Bearer $token'});
      print(response.statusCode);
      if (response.statusCode == 200) {
        final List<dynamic> responseData = json.decode(response.body);
        setState(() {
          domainList = responseData.cast<Map<String, dynamic>>();
        });
      } else {
        throw Exception('Failed to fetch domain list');
      }
    } catch (error) {
      _showSnackBar('Error fetching domains: $error', Colors.red);
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
    if (index == 2) {
      // Navigate to ProfilePage if "Profile" tab is selected
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const ProfilePage()),
      );
    } else if (index == 1) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => RequestsPage()),
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
      appBar: const NotificationScreen(
        type: Type.USER,
        addBackButton: false,
      ),
      body: isLoading
          ? const Center(
              child:
                  CircularProgressIndicator()) // Show a loader while fetching data
          : domainList.isEmpty
              ? const Center(
                  child: Text(
                    'No Domains Found',
                    style: TextStyle(fontSize: 18, color: Colors.grey),
                  ),
                )
              : ListView.builder(
                  itemCount: domainList.length,
                  itemBuilder: (context, index) {
                    final domain = domainList[index];

                    // Render a clickable card for each DomainDTO
                    return GestureDetector(
                      onTap: () async {
                        // TODO   change the pagename to your page
                        // Navigate to DomainDetailsPage with the domain ID
                        String? token = await storage.read(key: 'auth_token');
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ProfessionsScreen(
                              professionType:
                                  domain['name'], // Pass the domain ID
                              token: token,
                            ),
                          ),
                        );
                      },
                      child: Card(
                        margin: const EdgeInsets.symmetric(
                            horizontal: 10, vertical: 5),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        elevation: 4,
                        child: Padding(
                          padding: const EdgeInsets.all(15.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                domain['name'] ?? 'Unnamed Domain',
                                style: const TextStyle(
                                  fontSize: 18,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              const SizedBox(height: 5),
                              domain['photo'] != null
                                  ? Image.memory(
                                      base64Decode(domain['photo']),
                                      height: 100,
                                      width: double.infinity,
                                      fit: BoxFit.cover,
                                    )
                                  : const Text(
                                      'No Image Available',
                                      style: TextStyle(
                                        color: Colors.grey,
                                        fontSize: 14,
                                      ),
                                    ),
                            ],
                          ),
                        ),
                      ),
                    );
                  },
                ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        selectedItemColor: Colors.deepPurple,
        unselectedItemColor: Colors.grey,
        onTap: onTabTapped,
        items: const [
          // BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.work), label: 'Professions'),
          BottomNavigationBarItem(icon: Icon(Icons.list), label: 'Requests'),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Profile'),
        ],
      ),
    );
  }
}
