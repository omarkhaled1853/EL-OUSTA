import 'package:flutter/material.dart';
import 'dart:io';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'homeclient.dart';

class ProfilePage extends StatefulWidget {
  const ProfilePage({Key? key}) : super(key: key);

  @override
  State<ProfilePage> createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  Map<String, dynamic>? userData = {
    'Name': 'Hossam Osama',
    'Email': 'hossam86543.com',
    'ProfilePhoto': null, // Initially null; updated by user later
    'numberOfRequests': 15,
    'accepted': 10,
    'refused': 5,
  };

  bool isLoading = true;
  File? _selectedImage;
  int _currentIndex = 3;

  @override
  void initState() {
    super.initState();
    fetchClientData(); // Fetch data when page loads
  }

  Future<void> fetchClientData() async {
    try {
      final response = await http.get(Uri.parse('https://example.com/api/client'));
      if (response.statusCode == 200) {
        setState(() {
          userData = json.decode(response.body); // Parse the fetched data
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

  Future<void> _pickImage() async {
    final picker = ImagePicker();
    final pickedFile = await picker.pickImage(source: ImageSource.gallery);

    if (pickedFile != null) {
      final confirm = await _showConfirmDialog(
          'Change Profile Photo', 'Do you want to update your profile photo?');
      if (confirm) {
        setState(() {
          _selectedImage = File(pickedFile.path);
        });
        _showSnackBar('Profile photo updated!', Colors.green);
      }
    }
  }

  void onTabTapped(int index) {
    if (index == 0) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const ClientPage()),
      );
    } else {
      setState(() {
        _currentIndex = index;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Profile'),
        backgroundColor: Colors.purple,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Center(
              child: Column(
                children: [
                  GestureDetector(
                    onTap: _pickImage,
                    child: CircleAvatar(
                      radius: 60,
                      backgroundImage: _selectedImage != null
                          ? FileImage(_selectedImage!)
                          : userData != null &&
                          userData!['ProfilePhoto'] != null
                          ? MemoryImage(base64Decode(
                          userData!['ProfilePhoto']))
                          : const AssetImage('assets/hossam.jpg')
                      as ImageProvider,
                      child: _selectedImage == null
                          ? const Align(
                        alignment: Alignment.bottomRight,
                        child: Icon(Icons.camera_alt, size: 30),
                      )
                          : null,
                    ),
                  ),
                  const SizedBox(height: 10),
                  Text(
                    userData?['Name'] ?? 'User Name',
                    style: const TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 5),
                  Text(
                    userData?['Email'] ?? 'user@example.com',
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.grey,
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                _buildStatCard(
                    "Requests",
                    userData?['numberOfRequests'].toString() ??
                        '1'),
                _buildStatCard('Accepted',
                    userData?['accepted'].toString() ?? '1'),
                _buildStatCard('Refused',
                    userData?['refused'].toString() ?? '1'),
              ],
            ),
            const Spacer(),
            Column(
              children: [
                _buildButton('Change Profile Photo', Colors.blue, _pickImage),
                const SizedBox(height: 10),
                _buildButton('Reset Password', Colors.red, () {
                  _resetPassword(context);
                }),
              ],
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

  Widget _buildStatCard(String title, String value) {
    return Column(
      children: [
        Container(
          padding: const EdgeInsets.all(10),
          decoration: BoxDecoration(
            color: Colors.grey.shade200,
            borderRadius: BorderRadius.circular(10),
          ),
          child: Text(
            value,
            style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
        ),
        const SizedBox(height: 5),
        Text(
          title,
          style: const TextStyle(fontSize: 14, color: Colors.grey),
        ),
      ],
    );
  }

  Widget _buildButton(String label, Color color, VoidCallback onPressed) {
    return ElevatedButton(
      onPressed: onPressed,
      style: ElevatedButton.styleFrom(
        backgroundColor: color,
        minimumSize: const Size(double.infinity, 50),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10),
        ),
      ),
      child: Text(label),
    );
  }

  Future<bool> _showConfirmDialog(String title, String content) async {
    return await showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(title),
        content: Text(content),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true),
            child: const Text('Confirm'),
          ),
        ],
      ),
    ) ??
        false;
  }

  void _resetPassword(BuildContext context) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (context) {
        String? newPassword;
        String? confirmPassword;

        return Padding(
          padding: EdgeInsets.only(
            left: 20,
            right: 20,
            top: 20,
            bottom: MediaQuery.of(context).viewInsets.bottom + 20,
          ),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const Text(
                'Reset Password',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 20),
              TextField(
                obscureText: true,
                decoration: const InputDecoration(
                  labelText: 'New Password',
                  border: OutlineInputBorder(),
                ),
                onChanged: (value) {
                  newPassword = value;
                },
              ),
              const SizedBox(height: 10),
              TextField(
                obscureText: true,
                decoration: const InputDecoration(
                  labelText: 'Confirm Password',
                  border: OutlineInputBorder(),
                ),
                onChanged: (value) {
                  confirmPassword = value;
                },
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  if (newPassword == null ||
                      newPassword!.length < 6 ||
                      newPassword != confirmPassword) {
                    // Show the Snackbar above the modal without dismissing it
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(
                        content: const Text(
                          'Passwords must match and be at least 6 characters long!',
                        ),
                        backgroundColor: Colors.red,
                        behavior: SnackBarBehavior.floating, // Makes it float above other elements
                        margin: const EdgeInsets.only(bottom: 120.0, left: 16.0, right: 16.0), // Adjust position
                        duration: const Duration(seconds: 3), // Optional: set duration
                      ),
                    );
                  } else {
                    print('Password reset successfully: $newPassword');
                    Navigator.pop(context); // Dismiss the modal on success
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(
                        content: const Text('Password reset successfully!'),
                        backgroundColor: Colors.green,
                        behavior: SnackBarBehavior.floating,
                        margin: const EdgeInsets.only(bottom: 120.0, left: 16.0, right: 16.0),
                      ),
                    );
                  }
                },

                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.purple,
                  minimumSize: const Size(double.infinity, 50),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
                child: const Text('Reset Password'),
              ),
            ],
          ),
        );
      },
    );
  }

  void _showSnackBar(String message, Color color) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: color,
      ),
    );
  }
}
