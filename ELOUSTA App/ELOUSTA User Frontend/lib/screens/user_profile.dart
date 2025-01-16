import 'dart:developer';

import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/main.dart';
import 'package:flutter/material.dart';
import 'dart:io';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:el_ousta/old%20files/homeclient.dart';

class ProfilePage extends StatefulWidget {
  const ProfilePage({super.key});

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
  String? token;
  int? id;
  bool isLoading = true;
  File? _selectedImage;
  int _currentIndex = 3;

  Future<void> initToken() async {
    log("Inside initToken");
    try {
      final tokenValue = await secureStorage.read(key: 'auth_token');
      final idString = await secureStorage.read(key: 'id');
      log("Token value: $tokenValue");
      log("ID value: $idString");

      if (tokenValue != null && idString != null) {
        setState(() {
          token = tokenValue;
          id = int.parse(idString);
        });
        log("Token and ID successfully set in state");
      } else {
        throw Exception("Token or ID is null");
      }
    } catch (e) {
      log("Error in initToken: $e");
    }
  }

  @override
  void initState() {
    super.initState();
    log("inside initstate");
    initializeData();
  }

  Future<void> fetchClientData() async {
    log("inside fetch client data");
    try {
      final response = await http.get(
          Uri.parse('${ServerAPI.baseURL}/client/profile/${id}'),
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer $token'
          }); // todo
      if (response.statusCode == 200) {
        log(response.body);
        setState(() {
          userData = json.decode(response.body); // Parse the fetched data
          print(userData);
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

  void initializeData() async {
    await initToken(); // Wait for initToken to complete
    log("finished initToken");
    await fetchClientData(); // Fetch data after token and id are set
    log("finished fetching client data");
  }

  Future<void> _pickImage() async {
    final picker = ImagePicker();
    final pickedFile = await picker.pickImage(source: ImageSource.gallery);

    if (pickedFile != null) {
      final confirm = await _showConfirmDialog(
          'Change Profile Photo', 'Do you want to update your profile photo?');
      if (confirm) {
        File? tempImage = File(pickedFile.path); // Temporary image
        try {
          final request = http.MultipartRequest(
            'POST',
            Uri.parse('${ServerAPI.baseURL}/update-profile-photo'),
          );
          request.files.add(await http.MultipartFile.fromPath(
            'profilePhoto',
            tempImage.path,
          ));
          final response = await request.send();

          if (response.statusCode == 200) {
            setState(() {
              _selectedImage = tempImage; // Update only on success
              userData!['profilePicture'] =
                  base64Encode(tempImage.readAsBytesSync());
            });
            _showSnackBar('Profile photo updated!', Colors.green);
          } else {
            _showSnackBar(
                'Failed to update profile photo. Try again.', Colors.red);
          }
        } catch (error) {
          _showSnackBar('Error updating profile photo: $error', Colors.red);
        }
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
        backgroundColor: Colors.deepPurple,
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
                                        userData!['profilePicture'] != null
                                    ? MemoryImage(base64Decode(
                                        userData!['profilePicture']))
                                    : const AssetImage('images/avatar.png')
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
                          userData?['firstName'] ?? 'User Name',
                          style: const TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 5),
                        Text(
                          userData?['email'] ?? 'user@example.com',
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
                          "Pending", userData?['pending'].toString() ?? '1'),
                      _buildStatCard('InProgress',
                          userData?['inProgress'].toString() ?? '1'),
                      _buildStatCard('Completed',
                          userData?['completed'].toString() ?? '1'),
                    ],
                  ),
                  const Spacer(),
                  Column(
                    children: [
                      _buildButton(
                          'Change Profile Photo', Colors.blue, _pickImage),
                      const SizedBox(height: 10),
                      _buildButton('Reset Password', Colors.red, () {
                        _resetPassword(context);
                      }),
                    ],
                  ),
                ],
              ),
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
                onPressed: () async {
                  if (newPassword == null ||
                      newPassword!.length < 6 ||
                      newPassword != confirmPassword) {
                    _showDialog(
                        context,
                        'Error',
                        'Passwords must match and be at least 6 characters long!',
                        Colors.red);
                  } else {
                    // Call API to reset password
                    try {
                      final response = await http.post(
                        Uri.parse('http://192.168.1.6:8080/reset-password'),
                        headers: {'Content-Type': 'application/json'},
                        body: jsonEncode({'newPassword': newPassword}),
                      );

                      if (response.statusCode == 200) {
                        Navigator.pop(context); // Dismiss modal
                        _showDialog(context, 'Success',
                            'Password reset successfully!', Colors.green);
                      } else {
                        throw Exception('Failed to reset password');
                      }
                    } catch (error) {
                      _showDialog(context, 'Error',
                          'Error resetting password: $error', Colors.red);
                    }
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

  void _showDialog(
      BuildContext context, String title, String message, Color titleColor) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(
          title,
          style: TextStyle(color: titleColor),
        ),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context); // Close the dialog
            },
            child: const Text('OK'),
          ),
        ],
      ),
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
