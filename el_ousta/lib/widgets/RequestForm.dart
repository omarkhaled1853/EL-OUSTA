import 'package:el_ousta/API/serverAPI.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:el_ousta/Service/request_service.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

FlutterSecureStorage requestFormSecureStorage = const FlutterSecureStorage();

class RequestForm extends StatefulWidget {
  final Function(DateTime, DateTime, String, String) onSubmit;

  const RequestForm({super.key, required this.onSubmit});

  @override
  State<RequestForm> createState() => _RequestFormState();
}

class _RequestFormState extends State<RequestForm> {
  final _formKey = GlobalKey<FormState>();
  final _detailsController = TextEditingController();
  final _locationController = TextEditingController();
  DateTime? _startDate;
  DateTime? _endDate;

  @override
  void dispose() {
    _detailsController.dispose();
    _locationController.dispose();
    super.dispose();
  }

  Future<void> _selectDate(BuildContext context, bool isStartDate) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(const Duration(days: 365)),
    );

    if (picked != null) {
      setState(() {
        if (isStartDate) {
          _startDate = picked;
        } else {
          _endDate = picked;
        }
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Column(
        children: [
          TextField(
            controller: _detailsController,
            maxLines: 3,
            decoration: InputDecoration(
              labelText: 'Details of Request',
            ),
            inputFormatters: [
              LengthLimitingTextInputFormatter(100),
            ],
          ),
          const SizedBox(height: 16),
          _buildTextField(
            controller: _locationController,
            labelText: 'Location',
          ),
          const SizedBox(height: 16),
          _buildDateField(
            label: 'Date of Start',
            value: _startDate,
            onTap: () => _selectDate(context, true),
          ),
          const SizedBox(height: 16),
          _buildDateField(
            label: 'Date of End',
            value: _endDate,
            onTap: () => _selectDate(context, false),
          ),
          const SizedBox(height: 16),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              ElevatedButton(
                onPressed: () {
                  if (_startDate != null && _endDate != null) {
                    // Call the onSubmit callback when form is ready
                    widget.onSubmit(
                      _startDate!,
                      _endDate!,
                      _detailsController.text,
                      _locationController.text,
                    );
                    Navigator.of(context).pop();
                  }
                },
                child: const Text("Submit Request"),
              ),
              ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                  child: const Text("Cancel")),
            ],
          )
        ],
      ),
    );
  }

  Widget _buildTextField({
    required TextEditingController controller,
    required String labelText,
    int maxLines = 1,
  }) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.grey[200],
        borderRadius: BorderRadius.circular(8),
      ),
      child: TextField(
        controller: controller,
        maxLines: maxLines,
        decoration: InputDecoration(
          labelText: labelText,
          border: InputBorder.none,
          contentPadding: const EdgeInsets.all(16),
        ),
      ),
    );
  }

  Widget _buildDateField({
    required String label,
    required DateTime? value,
    required VoidCallback onTap,
  }) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        padding: const EdgeInsets.all(16),
        decoration: BoxDecoration(
          color: Colors.grey[200],
          borderRadius: BorderRadius.circular(8),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(label),
            Text(
              value != null
                  ? '${value.day}/${value.month}/${value.year}'
                  : 'Select date',
            ),
          ],
        ),
      ),
    );
  }
}

class RequestPopupDialog extends StatelessWidget {
  final String techName;
  final double rating;
  final String professionName;
  final int techid;
  final String token;
  const RequestPopupDialog({
    super.key,
    required this.techid,
    required this.professionName,
    required this.techName,
    required this.rating,
    required this.token,
  });

  @override
  Widget build(BuildContext context) {
    return Dialog(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(16),
      ),
      child: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                professionName,
                style: const TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 16),
              RequestForm(
                onSubmit: (startDate, endDate, details, location) async {
                  // Handle the form submission
                  // -----------------------------------------------------
                  final String? userStringId = await requestFormSecureStorage
                      .read(key: 'id'); // Replace with actual user ID
                  final int userId = int.parse(userStringId!);
                  print(userId);
                  final int techId =
                      this.techid; // Replace with actual technician ID
                  // ---------------------------------------------
                  // Call your service to send the request
                  print(techId);
                  RequestService(baseUrl: ServerAPI.baseURL)
                      .sendRequest(userId, techId, details, location, startDate,
                          endDate, context, this.token)
                      .then((success) {
                    if (success) {
                      ScaffoldMessenger.of(context)
                          .clearSnackBars(); // Clear any existing SnackBars
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                          content: Text('Request successfully sent!'),
                          backgroundColor: Colors.green,
                        ),
                      );
                    } else {
                      ScaffoldMessenger.of(context)
                          .clearSnackBars(); // Clear any existing SnackBars
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                          content: Text('Failed to send the request.'),
                          backgroundColor: Colors.red,
                        ),
                      );
                    }
                  });
                },
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildTextField({
    required TextEditingController controller,
    required String labelText,
    int maxLines = 1,
    List<TextInputFormatter>? inputFormatters, // Add this parameter
  }) {
    return TextField(
      controller: controller,
      maxLines: maxLines,
      decoration: InputDecoration(
        labelText: labelText,
      ),
      inputFormatters: inputFormatters, // Pass the input formatters here
    );
  }

  Widget _buildActionButton({
    required VoidCallback onPressed,
    required IconData icon,
    required Color color,
  }) {
    return Container(
      decoration: BoxDecoration(
        color: color,
        shape: BoxShape.circle,
      ),
      child: IconButton(
        icon: Icon(icon, color: Colors.white),
        onPressed: onPressed,
      ),
    );
  }
}
