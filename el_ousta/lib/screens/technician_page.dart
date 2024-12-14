import 'dart:typed_data';

import 'package:el_ousta/API/serverAPI.dart';
import 'package:flutter/material.dart';
import 'dart:io';
import 'package:image_picker/image_picker.dart';
import 'techinican_home.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

class TechnicianPage extends StatefulWidget {
  const TechnicianPage({Key? key}) : super(key: key);

  @override
  State<TechnicianPage> createState() => _TechnicianPageState();
}

class _TechnicianPageState extends State<TechnicianPage> {
  bool isLoading = true;

  Map<String, dynamic> technicianData = {
    'name': 'Hossam Osama',
    'email': 'hossam86543.com',
    'profilePhoto': null,
    'numberOfRequests': 20,
    'accepted': 15,
    'refused': 5,
    'description': 'I am an experienced technician specializing in electrical and plumbing work.',
    'startDate': '01-01-2022',
    'rate': 4.5,
    'photos': [],
  };

  final TextEditingController _descriptionController = TextEditingController();
  final ImagePicker _picker = ImagePicker();

  File? _profilePhoto;
  File? _newPhoto;

  Future<void> fetchClientData() async {
    try {
      final response = await http.get(Uri.parse(ServerAPI.baseURL + '/tech/profile/{id}')); // todo
      if (response.statusCode == 200) {

        setState(() {
          technicianData = json.decode(response.body); // Parse the fetched data
          isLoading = false; // Loading completed
          print(technicianData);
        });
      } else {
        throw Exception('Failed to load Technician data');
      }
    } catch (error) {
      setState(() {
        isLoading = false;
      });
      print('Error fetching Technician data: $error');
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Failed to load data. Please try again later.'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }


  @override
  void initState() {
    super.initState();
    fetchClientData(); // Fetch data when page loads
    _descriptionController.text = technicianData['description'];
  }

  @override
  void dispose() {
    _descriptionController.dispose();
    super.dispose();
  }



  Future<void> _updateDescription() async {
    final newDescription = _descriptionController.text;

    final confirm = await _showConfirmDialog(
      'Update Description',
      'Are you sure you want to update your description?',
    );

    if (confirm) {
      try {
        final response = await http.post(
          Uri.parse('http://192.168.1.6:8080/update-description'),
          headers: {'Content-Type': 'application/json'},
          body: jsonEncode({'description': newDescription}),
        );

        if (response.statusCode == 200) {
          setState(() {
            technicianData['description'] = newDescription;
          });
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Description updated successfully!'),
              backgroundColor: Colors.green,
            ),
          );
        } else {
          throw Exception('Failed to update description.');
        }
      } catch (error) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Error updating description: $error'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  int _currentIndex = 3;

  void onTabTapped(int index) {
    if (index == 0) {
      // Navigate to ProfilePage if "Profile" tab is selected
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const TechnicianHome()),
      );
    } else {
      setState(() {
        _currentIndex = index; // Update the current index
      });
    }
  }
  // API simulation for updating backend
  Future<void> _updatePhotoListToBackend() async {
    try {
      // Make the API call to send the full list of photos to the backend
      final response = await http.post(
        Uri.parse('http://192.168.1.6:8080/update-photos'), // Replace with your actual endpoint
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'photos': technicianData['photos']}), // Send the full photo list
      );

      if (response.statusCode == 200) {
        // Success response
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Photo list updated successfully!'),
            backgroundColor: Colors.green,
          ),
        );
      } else {
        // Handle non-200 responses
        throw Exception('Failed to update photo list. Status code: ${response.statusCode}');
      }
    } catch (error) {
      // Handle errors and show failure message
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Error updating photo list: $error'),
          backgroundColor: Colors.red,
        ),
      );
      print('Error updating photo list: $error'); // Log the error for debugging
    }
  }

  // Show full screen photo view
  void _viewPhotoFullScreen(String base64Photo) {
    showDialog(
      context: context,
      builder: (context) {
        return Dialog(
          child: Stack(
            children: [
              Center(
                child: Image(
                  image: MemoryImage(base64Decode(base64Photo)),
                  fit: BoxFit.contain,
                  height: MediaQuery.of(context).size.height * 0.8,
                  width: MediaQuery.of(context).size.width * 0.8,
                ),
              ),
              Positioned(
                top: 20,
                right: 20,
                child: IconButton(
                  icon: const Icon(Icons.close),
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                ),
              ),
              Positioned(
                bottom: 20,
                left: 20,
                right: 20,
                child: Row(
                  children: [
                    // Add padding to the left side of the buttons to avoid overflow
                    Expanded(
                      child: ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).pop(); // Close the dialog
                        },
                        child: const Text('Back to Profile'),
                      ),
                    ),
                    const SizedBox(width: 10), // Small gap between the buttons
                    Expanded(
                      child: ElevatedButton(
                        onPressed: () async {
                          // Call the _removePhoto function
                          await _removePhoto(base64Photo);
                          Navigator.of(context).pop(); // Close the dialog
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(
                              content: Text('Photo removed successfully!'),
                              backgroundColor: Colors.green,
                            ),
                          );
                        },
                        child: const Text('Remove Photo'),
                        style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        );
      },
    );
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Technician Profile'),
        backgroundColor: Colors.purple,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator()) // Show a loader while data is loading
          : Padding(
        padding: const EdgeInsets.all(16.0),
        child: ListView(
          children: [
            Center(
              child: Column(
                children: [
                  GestureDetector(
                    onTap: _changeProfilePhoto,
                    child: CircleAvatar(
                      radius: 50,
                      backgroundImage: _profilePhoto != null
                          ? FileImage(_profilePhoto!)
                          : (technicianData['profilePicture'] != null
                          ? MemoryImage(base64Decode(
                          technicianData['profilePicture']))
                          : const AssetImage('assets/default_user.png')) as ImageProvider,
                      child: _profilePhoto == null
                          ? const Align(
                        alignment: Alignment.bottomRight,
                        child: Icon(Icons.camera_alt, size: 30),
                      )
                          : null,
                    ),
                  ),
                  const SizedBox(height: 10),
                  Text(
                    technicianData['firstName']+' '+technicianData['lastName'],
                    style: const TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 5),
                  Text(
                    technicianData['email'],
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.grey,
                    ),
                  ),
                  const SizedBox(height: 20),
                  Row(
                    children: [

                      Text(
                        'Experience: ${technicianData['experience']}',
                        style: const TextStyle(fontSize: 16),
                      ),
                      const Spacer(),
                      Text(
                        'Rating: ${technicianData['rate']} ⭐',
                        style: const TextStyle(fontSize: 16),
                      ),
                    ],
                  ),
                  const SizedBox(height: 20),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      _buildStatCard('Requests', technicianData['requests'].toString()),
                      _buildStatCard('Accepted', technicianData['accepted'].toString()),
                      _buildStatCard('Refused', technicianData['cancelled'].toString()),
                    ],
                  ),

                ],
              ),
            ),
            const SizedBox(height: 20),
            const Text(
              'Description:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            TextFormField(
              controller: _descriptionController,
              maxLines: 3,
              decoration: InputDecoration(
                hintText: 'Enter your description here...',
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                ),
              ),
            ),
            const SizedBox(height: 10),
            ElevatedButton(
              onPressed: _updateDescription,
              style: ElevatedButton.styleFrom(backgroundColor: Colors.blue),
              child: const Text('Confirm Description Update'),
            ),



            const SizedBox(height: 20),
            // Publish Photo Button
            ElevatedButton(
              onPressed: _publishPhoto,
              style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
              child: const Text('Publish Photo'),
            ),
            const SizedBox(height: 20),
            // Display Published Photos
            const Text(
              'Published Photos:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            technicianData['portfolioDto'].isEmpty
                ? const Center(child: Text('No photos published yet.'))
                : GridView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: technicianData['portfolioDto'].length,
              itemBuilder: (context, index) {
                final photoBytes = technicianData['portfolioDto'][index]['photo']; // Each item is a byte[]
                return GestureDetector(
                  onTap: () {
                    _viewPhotoFullScreen(photoBytes);
                  },
                  child: Image(
                    image: MemoryImage(base64Decode(photoBytes)),
                    fit: BoxFit.cover,
                  ),
                );
              },
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                _resetPassword(context);
              },
              style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              child: const Text('Reset Password'),
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
  Future<void> _changeProfilePhoto() async {
    final pickedFile = await _picker.pickImage(source: ImageSource.gallery);

    if (pickedFile != null) {
      final confirm = await _showConfirmDialog(
        'Change Profile Photo',
        'Do you want to update your profile photo?',
      );

      if (confirm) {
        try {
          final request = http.MultipartRequest(
            'POST',
            Uri.parse('http://192.168.1.6:8080/update-profile-photo'),
          );
          request.files.add(await http.MultipartFile.fromPath(
            'profilePhoto',
            pickedFile.path,
          ));

          final response = await request.send();

          if (response.statusCode == 200) {
            setState(() {
              _profilePhoto = File(pickedFile.path);
              technicianData['profilePhoto'] = pickedFile.path;
            });
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(
                content: Text('Profile photo updated successfully!'),
                backgroundColor: Colors.green,
              ),
            );
          } else {
            throw Exception('Failed to update profile photo.');
          }
        } catch (error) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('Error updating profile photo: $error'),
              backgroundColor: Colors.red,
            ),
          );
        }
      }
    }
  }


  Future<void> _resetPassword(BuildContext context) async {
    String? newPassword;
    String? confirmPassword;

    await showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (context) {
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
                onChanged: (value) => newPassword = value,
              ),
              const SizedBox(height: 10),
              TextField(
                obscureText: true,
                decoration: const InputDecoration(
                  labelText: 'Confirm Password',
                  border: OutlineInputBorder(),
                ),
                onChanged: (value) => confirmPassword = value,
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
                      Colors.red,
                    );
                  } else {
                    final confirm = await _showConfirmDialog1(
                      context,
                      'Reset Password',
                      'Are you sure you want to reset your password?',
                    );

                    if (confirm) {
                      try {
                        final response = await http.post(
                          Uri.parse('http://192.168.1.6:8080/reset-password'),
                          headers: {'Content-Type': 'application/json'},
                          body: jsonEncode({'newPassword': newPassword}),
                        );

                        if (response.statusCode == 200) {
                          Navigator.pop(context);
                          _showDialog(
                            context,
                            'Success',
                            'Password reset successfully!',
                            Colors.green,
                          );
                        } else {
                          throw Exception('Failed to reset password.');
                        }
                      } catch (error) {
                        _showDialog(
                          context,
                          'Error',
                          'Error resetting password: $error',
                          Colors.red,
                        );
                      }
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

  Future<void> _publishPhoto() async {
    final pickedFile = await _picker.pickImage(source: ImageSource.gallery);

    if (pickedFile != null) {
      final confirm = await _showConfirmDialog(
        'Publish Photo',
        'Are you sure you want to publish this photo?',
      );

      if (confirm) {
        final tempPhotos = List<String>.from(technicianData['photos'])..add(pickedFile.path);

        try {
          final response = await http.post(
            Uri.parse('http://192.168.1.6:8080/publish-photos'),
            headers: {'Content-Type': 'application/json'},
            body: jsonEncode({'photos': tempPhotos}),
          );

          if (response.statusCode == 200) {
            setState(() {
              technicianData['photos'] = tempPhotos;
            });
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(
                content: Text('Photos updated and published successfully!'),
                backgroundColor: Colors.green,
              ),
            );
          } else {
            throw Exception('Failed to publish photos.');
          }
        } catch (error) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('Error publishing photos: $error'),
              backgroundColor: Colors.red,
            ),
          );
        }
      }
    }
  }


  Future<void> _removePhoto(String photoPath) async {
    final confirm = await _showConfirmDialog(
      'Remove Photo',
      'Are you sure you want to remove this photo?',
    );

    if (confirm) {
      final tempPhotos = List<String>.from(technicianData['photos'])..remove(photoPath);

      try {
        final response = await http.post(
          Uri.parse('http://192.168.1.6:8080/update-photos'),
          headers: {'Content-Type': 'application/json'},
          body: jsonEncode({'photos': tempPhotos}),
        );

        if (response.statusCode == 200) {
          setState(() {
            technicianData['photos'] = tempPhotos;
          });
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Photo removed successfully!'),
              backgroundColor: Colors.green,
            ),
          );
        } else {
          throw Exception('Failed to remove photo.');
        }
      } catch (error) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Error removing photo: $error'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }


  Future<bool> _showConfirmDialog(String title, String content) async {
    return await showDialog<bool>(
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
}
Future<bool> _showConfirmDialog1(
    BuildContext context, String title, String message) async {
  return await showDialog<bool>(
    context: context,
    builder: (context) => AlertDialog(
      title: Text(title),
      content: Text(message),
      actions: [
        TextButton(
          onPressed: () => Navigator.pop(context, false), // Cancel
          child: const Text('Cancel'),
        ),
        TextButton(
          onPressed: () => Navigator.pop(context, true), // Confirm
          child: const Text('Confirm'),
        ),
      ],
    ),
  ) ??
      false; // Return false if dialog is dismissed

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