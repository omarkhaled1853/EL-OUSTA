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
      final response = await http.get(Uri.parse('https://example.com/api/client'));
      if (response.statusCode == 200) {
        setState(() {
          technicianData = json.decode(response.body); // Parse the fetched data
          isLoading = false; // Loading completed
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
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                        content: Text(
                          'Passwords must match and be at least 6 characters long!',
                        ),
                        backgroundColor: Colors.red,
                      ),
                    );
                  } else {
                    print('Password reset successfully: $newPassword');
                    Navigator.pop(context);
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                        content: Text('Password reset successfully!'),
                        backgroundColor: Colors.green,
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

  Future<void> _changeProfilePhoto() async {
    final pickedFile = await _picker.pickImage(source: ImageSource.gallery);
    if (pickedFile != null) {
      setState(() {
        _profilePhoto = File(pickedFile.path);
        technicianData['profilePhoto'] = pickedFile.path;
      });
      print('Profile photo sent to backend: ${pickedFile.path}');
    }
  }

  // Publish a new photo
  Future<void> _publishPhoto() async {
    final pickedFile = await _picker.pickImage(source: ImageSource.gallery);
    if (pickedFile != null) {
      setState(() {
        _newPhoto = File(pickedFile.path);
        technicianData['photos'].add(pickedFile.path);
      });
      print('Photo published and sent to backend: ${pickedFile.path}');
    }
  }

  void _updateDescription() {
    setState(() {
      technicianData['description'] = _descriptionController.text;
    });
    print('Updated description sent to backend: ${technicianData['description']}');
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
    // Simulate sending the new list of photos to the backend
    print('Sending updated photo list to backend: ${technicianData['photos']}');
    // Make the API call here, e.g., using http.post() or other methods
  }

  // Show full screen photo view
  void _viewPhotoFullScreen(String photoPath) {
    showDialog(
      context: context,
      builder: (context) {
        return Dialog(
          child: Stack(
            children: [
              Center(
                child: Image.file(
                  File(photoPath),
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
                        onPressed: () {
                          setState(() {
                            technicianData['photos'].remove(photoPath);
                            _updatePhotoListToBackend(); // Send the updated list to backend
                          });
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
                          : (technicianData['profilePhoto'] != null
                          ? FileImage(File(technicianData['profilePhoto']))
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
                    technicianData['name'],
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
                        'Start Date: ${technicianData['startDate']}',
                        style: const TextStyle(fontSize: 16),
                      ),
                      const Spacer(),
                      Text(
                        'Rating: ${technicianData['rate']} ⭐',
                        style: const TextStyle(fontSize: 16),
                      ),
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
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                _buildStatCard('Requests', technicianData['numberOfRequests'].toString()),
                _buildStatCard('Accepted', technicianData['accepted'].toString()),
                _buildStatCard('Refused', technicianData['refused'].toString()),
              ],
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
            technicianData['photos'].isEmpty
                ? const Center(child: Text('No photos published yet.'))
                : GridView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: technicianData['photos'].length,
              itemBuilder: (context, index) {
                String photoPath = technicianData['photos'][index];
                return GestureDetector(
                  onTap: () {
                    _viewPhotoFullScreen(photoPath);
                  },
                  child: Image.file(
                    File(photoPath),
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
}
