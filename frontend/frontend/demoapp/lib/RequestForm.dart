import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:demoapp/Service/request_service.dart';

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
              }
            },
            child: const Text("Submit Request"),
          ),
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

  const RequestPopupDialog({
    super.key,
    required this.techid,
    required this.professionName,
    required this.techName,
    required this.rating,
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
                onSubmit: (startDate, endDate, details, location) {
                  // Handle the form submission
                  // -----------------------------------------------------
                  final int userId = 123; // Replace with actual user ID
                  final int techId =
                      this.techid; // Replace with actual technician ID
                  // ---------------------------------------------
                  // Call your service to send the request
                  RequestService(baseUrl: 'http://192.168.1.12:8085')
                      .sendRequest(
                    userId,
                    techId,
                    details,
                    location,
                    startDate,
                    endDate,
                    context,
                  )
                      .then((success) {
                    if (success) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text('Request successfully sent!')),
                      );
                    } else {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text('Failed to send the request.')),
                      );
                    }
                  });
                },
              ),
              const SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  _buildActionButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    icon: Icons.close,
                    color: const Color.fromARGB(255, 2, 61, 89),
                  ),
                ],
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
