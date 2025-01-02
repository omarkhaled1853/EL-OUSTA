import 'package:el_ousta/client_requests/api_service.dart';
import 'package:el_ousta/client_requests/complain_dto.dart';
import 'package:flutter/material.dart';

class ComplaintDialog extends StatefulWidget {
  final int clientId;
  final int techId;

  const ComplaintDialog(
      {super.key, required this.clientId, required this.techId});

  @override
  State<ComplaintDialog> createState() => _ComplaintDialogState();
}

class _ComplaintDialogState extends State<ComplaintDialog> {
  final _complaintController = TextEditingController();
  final ApiService apiService = ApiService();

  @override
  void dispose() {
    _complaintController.dispose();
    super.dispose();
  }

  Future<void> submitComplaint() async {
    // Create the complaint object
    final complaint = ComplaintDTO(
      clientId: widget.clientId,
      techId: widget.techId,
      complaintBody: _complaintController.text,
    );
    
 try {
    // Wait for the API call to complete
    await apiService.addComplaint(complaint);

    // Optionally, show success feedback to the user
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Complaint submitted successfully!')),
    );
  } catch (e) {
    // Handle any errors (e.g., network or API errors)
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Error: $e')),
    );
  } finally {
    Navigator.pop(context); // Close the dialog
  }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Submit Complaint'),
      content: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: _complaintController,
              decoration: const InputDecoration(
                labelText: 'Enter your complaint',
                border: OutlineInputBorder(),
              ),
              maxLines: 4,
            ),
          ],
        ),
      ),
      actions: [
        TextButton(
          onPressed: () {
            Navigator.pop(context); // Close the dialog
          },
          child: const Text('Cancel'),
        ),
        ElevatedButton(
          onPressed: submitComplaint,
          child: const Text('Submit Complaint'),
        ),
      ],
    );
  }
}
