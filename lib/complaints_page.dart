import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:test_app/API/serverAPI.dart';
import 'complaint_details_page.dart';

class ComplaintsPage extends StatefulWidget {
  @override
  _ComplaintsPageState createState() => _ComplaintsPageState();
}

class _ComplaintsPageState extends State<ComplaintsPage> {
  List complaints = [];
  List filteredComplaints = [];
  TextEditingController searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    fetchComplaints(); // Fetch complaints from the backend
    searchController.addListener(() => filterComplaints());
  }

  Future<void> fetchComplaints() async {
    try {
      final response = await http.get(Uri.parse('${ServerAPI.baseURL}/admin'));
      if (response.statusCode == 200) {
        setState(() {
          complaints = json.decode(response.body); // Decode JSON response
          filteredComplaints = complaints; // Initialize filtered list
        });
      } else {
        showError("Failed to load complaints. Please try again.");
      }
    } catch (e) {
      showError("Error: $e");
    }
  }

  void filterComplaints() {
    String query = searchController.text.toLowerCase();
    setState(() {
      filteredComplaints = complaints.where((complaint) {
        return complaint.values.any(
              (value) => value.toString().toLowerCase().contains(query),
        );
      }).toList();
    });
  }

  void showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(message, style: TextStyle(color: Colors.red))));
  }

  TextSpan buildHighlightedText(String text, String query) {
    if (query.isEmpty) {
      return TextSpan(text: text, style: TextStyle(color: Colors.black));
    }

    String lowerText = text.toLowerCase();
    String lowerQuery = query.toLowerCase();

    List<TextSpan> spans = [];
    int start = 0;

    while (true) {
      int index = lowerText.indexOf(lowerQuery, start);
      if (index == -1) {
        spans.add(TextSpan(text: text.substring(start)));
        break;
      }

      if (index > start) {
        spans.add(TextSpan(
          text: text.substring(start, index),
          style: TextStyle(color: Colors.black),
        ));
      }

      spans.add(TextSpan(
        text: text.substring(index, index + query.length),
        style: TextStyle(color: Colors.deepPurple, fontWeight: FontWeight.bold),
      ));

      start = index + query.length;
    }

    return TextSpan(children: spans);
  }

  // Modified deleteComplaint with confirmation dialog
  void deleteComplaint(String id) async {
    // Show confirmation dialog before deleting
    bool confirmDelete = await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Confirm Deletion'),
          content: Text('Are you sure you want to delete this complaint?'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(false);  // Return false if "Cancel" is pressed
              },
              child: Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(true);  // Return true if "Delete" is pressed
              },
              child: Text('Delete'),
            ),
          ],
        );
      },
    );

    // If the user confirmed, proceed with the deletion
    if (confirmDelete) {
      try {
        final response = await http.delete(Uri.parse('${ServerAPI.baseURL}/api/complaints/$id'));
        if (response.statusCode == 200) {
          setState(() {
            complaints.removeWhere((complaint) => complaint['id'] == id);
            filteredComplaints.removeWhere((complaint) => complaint['id'] == id);
          });
          ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text("Complaint deleted successfully.")));
        } else {
          showError("Failed to delete complaint.");
        }
      } catch (e) {
        showError("Error: $e");
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.deepPurple,
        title: Text("EL Osta - Complaints"),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: [
            // Search Section
            TextField(
              controller: searchController,
              decoration: InputDecoration(
                hintText: "Search by complaint body or names",
                prefixIcon: Icon(Icons.search),
                filled: true,
                fillColor: Colors.grey[200],
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8.0),
                  borderSide: BorderSide.none,
                ),
              ),
            ),
            SizedBox(height: 10),
            // Refreshable Complaint List
            Expanded(
              child: RefreshIndicator(
                onRefresh: fetchComplaints, // Call fetchComplaints when user pulls down to refresh
                child: filteredComplaints.isEmpty
                    ? Center(
                  child: Text(
                    "No complaints found",
                    style: TextStyle(fontSize: 18),
                  ),
                )
                    : ListView.builder(
                  itemCount: filteredComplaints.length,
                  itemBuilder: (context, index) {
                    final complaint = filteredComplaints[index];
                    String query = searchController.text;

                    return Card(
                      margin: EdgeInsets.symmetric(vertical: 5),
                      color: Colors.deepPurple[50],
                      child: ListTile(
                        title: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            RichText(
                              text: buildHighlightedText(
                                complaint['direction'] == 0
                                    ? '${complaint['clientName']}  complaint against ${complaint['technicianName']}.'
                                    : '${complaint['technicianName']}  complaint against ${complaint['clientName']}.',
                                query,
                              ),
                            ),
                            SizedBox(height: 16), // Adds space between the title and the subtitle
                            RichText(
                              text: buildHighlightedText(
                                complaint['complaintBody'],
                                query,
                              ),
                            ),
                          ],
                        ),
                        trailing: IconButton(
                          icon: Icon(Icons.delete, color: Colors.red),
                          onPressed: () => deleteComplaint(complaint['id']), // Trigger delete confirmation
                        ),
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => ComplaintDetailsPage(complaintId: complaint['id']),
                            ),
                          );
                        },
                      ),
                    );
                  },
                ),
              ),
            ),
          ],
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        backgroundColor: Colors.grey[800], // Darker background for better visibility
        selectedItemColor: Colors.white,
        unselectedItemColor: Colors.grey[900],
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
          // Handle navigation here if needed
        },
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: "Home"),
          BottomNavigationBarItem(icon: Icon(Icons.work), label: "Professions"),
          BottomNavigationBarItem(icon: Icon(Icons.list), label: "Requests"),
          BottomNavigationBarItem(icon: Icon(Icons.people), label: "Clients"),
          BottomNavigationBarItem(icon: Icon(Icons.feedback), label: "Complains"),
        ],
      ),
    );
  }
  int _currentIndex = 4;

  void onTabTapped(int index) {
    if (index == 3) {
      // Navigate to ProfilePage if "Profile" tab is selected
      // Navigator.push(
      //   context,
      //   MaterialPageRoute(builder: (context) => const TechnicianPage()),
      // );
    } else {
      setState(() {
        _currentIndex = index; // Update the current index
      });
    }
  }
}
