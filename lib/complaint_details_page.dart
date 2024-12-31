import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'complaints_page.dart';
import 'package:test_app/API/serverAPI.dart';

class ComplaintDetailsPage extends StatefulWidget {
  final int complaintId;
  final int adminId;
  ComplaintDetailsPage({required this.complaintId, required this.adminId});

  @override
  _ComplaintDetailsPageState createState() => _ComplaintDetailsPageState();
}

class _ComplaintDetailsPageState extends State<ComplaintDetailsPage> {
  Map complaint = {};

  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchComplaintDetails();

  }

  Future<void> fetchComplaintDetails() async {
    try {
      final response = await http.get(Uri.parse('${ServerAPI.baseURL}/admin/${widget.complaintId}'));
      if (response.statusCode == 200) {
        setState(() {
          complaint = json.decode(response.body);
          isLoading = false;
        });
      } else {
        showError("Failed to load complaint details.");
      }
    } catch (e) {
      showError("Error: $e");
    }
  }


  // Modified removeUser with confirmation dialog
  void removeUser() async {
    bool confirmDelete = await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Confirm Removal'),
          content: Text('Are you sure you want to remove this Client?'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(false);  // Return false if "Cancel" is pressed
              },
              child: Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(true);  // Return true if "Remove" is pressed
              },
              child: Text('Remove'),
            ),
          ],
        );
      },
    );

    // If the user confirmed, proceed with the removal
    if (confirmDelete) {
      try {
        final response = await http.delete(
          Uri.parse('${ServerAPI.baseURL}/admin/remove-user/${complaint['clientId']}?adminId=${widget.adminId}'),
          headers: {'Content-Type': 'application/json'},
        );

        if (response.statusCode == 200) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text("User removed successfully!")),
          );
        }  else if(response.statusCode == 403){
          showError("You don't have access.");
        }else {
          showError("Failed to remove user.");
        }
      } catch (e) {
        showError("Error: $e");
      }
    }
  }

  // Modified removeTech with confirmation dialog
  void removeTech() async {
    bool confirmDelete = await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Confirm Removal'),
          content: Text('Are you sure you want to remove this technician?'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(false);  // Return false if "Cancel" is pressed
              },
              child: Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(true);  // Return true if "Remove" is pressed
              },
              child: Text('Remove'),
            ),
          ],
        );
      },
    );

    // If the user confirmed, proceed with the removal
    if (confirmDelete) {
      try {
        final response = await http.delete(
          Uri.parse('${ServerAPI.baseURL}/admin/remove-tech/${complaint['technicianId']}?adminId=${widget.adminId}'),
          headers: {'Content-Type': 'application/json'},
        );

        if (response.statusCode == 200) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text("Technician removed successfully!")),
          );
        } else if(response.statusCode == 403){
          showError("You don't have access.");
        }
        else {
          showError("Failed to remove technician.");
        }
      } catch (e) {
        showError("Error: $e");
      }
    }
  }

  void showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message, style: TextStyle(color: Colors.red))),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.deepPurple,
        title: Text("Complaint Details"),
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (complaint['direction'] == 0) ...[
              Text("Complainer:", style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 5),
              Text(complaint['clientName'] ?? "Unknown"),
              ElevatedButton(
                onPressed: () => removeUser(),
                child: Text("Remove Complainer"),
                style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              ),
              Divider(height: 20),

              Text("Complained:", style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 5),
              Text(complaint['technicianName'] ?? "Unknown"),
              ElevatedButton(
                onPressed: () => removeTech(),
                child: Text("Remove Complained"),
                style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              ),
            ] else if (complaint['direction'] == 1) ...[
              Text("Complainer:", style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 5),
              Text(complaint['technicianName']?? "Unknown"),
              ElevatedButton(
                onPressed: () => removeTech(),
                child: Text("Remove Complainer"),
                style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              ),

              Divider(height: 20),

              Text("Complained:", style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(height: 5),
              Text(complaint['clientName'] ?? "Unknown"),
              ElevatedButton(
                onPressed: () => removeUser(),
                child: Text("Remove Complained"),
                style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              ),
            ],
            Divider(height: 20),
            Text("Complaint Body:", style: TextStyle(fontWeight: FontWeight.bold)),
            SizedBox(height: 5),
            Text(complaint['complaintBody'] ?? "Unknown"),
          ],
        ),
      ),
    );
  }
}
