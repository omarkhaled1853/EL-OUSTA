import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'API/serverAPI.dart';

class PaymentPage extends StatefulWidget {
  final http.Client? httpClient;

  const PaymentPage({Key? key, this.httpClient}) : super(key: key);
  @override
  _PaymentPageState createState() => _PaymentPageState();
}

class _PaymentPageState extends State<PaymentPage> {
  late final http.Client client;
  String selectedPaymentMethod = 'Credit Card';

  // Controllers for input fields
  final TextEditingController cardNumberController = TextEditingController();
  final TextEditingController expiryDateController = TextEditingController();
  final TextEditingController cvvController = TextEditingController();
  final TextEditingController paypalEmailController = TextEditingController();

  bool isSubmitting = false; // For showing a loading indicator

  void initState() {
    super.initState();
    client = widget.httpClient ?? http.Client();
  }
  @override
  void dispose() {
    // Dispose controllers
    cardNumberController.dispose();
    expiryDateController.dispose();
    cvvController.dispose();
    paypalEmailController.dispose();
    super.dispose();
  }

  Future<void> _submitPayment() async {
    setState(() {
      isSubmitting = true; // Show loading indicator
    });

    // Prepare data to send
    Map<String, dynamic> paymentData = {
      'paymentMethod': selectedPaymentMethod,
    };

    if (selectedPaymentMethod == 'Credit Card') {
      paymentData['cardNumber'] = cardNumberController.text;
      paymentData['expiryDate'] = expiryDateController.text;
      paymentData['cvv'] = cvvController.text;
    } else if (selectedPaymentMethod == 'PayPal') {
      paymentData['paypalEmail'] = paypalEmailController.text;
    }

    try {
      // Replace with your backend endpoint
      const String apiUrl = '${ServerAPI.baseURL}/client/home/';

      final response = await http.post(
        Uri.parse(apiUrl),
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonEncode(paymentData),
      );

      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Payment processed successfully!')),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Payment failed. Please try again.')),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: ${e.toString()}')),
      );
    } finally {
      setState(() {
        isSubmitting = false; // Hide loading indicator
      });
    }
  }

  Future<void> _showConfirmationDialog() async {
    // Show the confirmation dialog
    bool confirmed = await showDialog<bool>(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Confirm Submission'),
          content: const Text(
              'Are you sure you want to submit your payment details?'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(false); // Cancel
              },
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop(true); // Confirm
              },
              child: const Text('Confirm'),
            ),
          ],
        );
      },
    ) ??
        false; // Default to false if dismissed

    if (confirmed) {
      _submitPayment(); // Proceed with submission if confirmed
    }
  }

  Widget _buildPaymentMethodFields() {
    switch (selectedPaymentMethod) {
      case 'Credit Card':
        return Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextField(
              controller: cardNumberController,
              decoration: const InputDecoration(
                labelText: 'Card Number',
                border: OutlineInputBorder(),
              ),
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 16),
            Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: expiryDateController,
                    decoration: const InputDecoration(
                      labelText: 'Expiry Date (MM/YY)',
                      border: OutlineInputBorder(),
                    ),
                    keyboardType: TextInputType.datetime,
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: TextField(
                    controller: cvvController,
                    decoration: const InputDecoration(
                      labelText: 'CVV',
                      border: OutlineInputBorder(),
                    ),
                    keyboardType: TextInputType.number,
                  ),
                ),
              ],
            ),
          ],
        );
      case 'PayPal':
        return TextField(
          controller: paypalEmailController,
          decoration: const InputDecoration(
            labelText: 'PayPal Email',
            border: OutlineInputBorder(),
          ),
          keyboardType: TextInputType.emailAddress,
        );
      default:
        return const SizedBox.shrink();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Payment Page',
          style: TextStyle(color: Colors.white),
        ),
        centerTitle: true,
        backgroundColor: Colors.deepPurple,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Select Payment Method',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            DropdownButton<String>(
              value: selectedPaymentMethod,
              isExpanded: true,
              onChanged: (String? newValue) {
                setState(() {
                  selectedPaymentMethod = newValue!;
                });
              },
              items: ['Credit Card', 'PayPal']
                  .map<DropdownMenuItem<String>>(
                    (String value) => DropdownMenuItem<String>(
                  value: value,
                  child: Text(value),
                ),
              )
                  .toList(),
            ),
            const SizedBox(height: 16),
            const Text(
              'Enter Payment Details',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            _buildPaymentMethodFields(),
            const Spacer(),
            isSubmitting
                ? const Center(
              child: CircularProgressIndicator(), // Show a loader while submitting
            )
                : ElevatedButton(
              onPressed: _showConfirmationDialog, // Trigger confirmation dialog
              style: ElevatedButton.styleFrom(
                minimumSize: const Size(double.infinity, 50),
                backgroundColor: Colors.deepPurple,
              ),
              child: const Text(
                'Submit Payment',
                style: TextStyle(fontSize: 18, color: Colors.white),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
