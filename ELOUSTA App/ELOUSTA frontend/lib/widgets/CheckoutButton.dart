import 'dart:math';

import 'package:el_ousta/API/serverAPI.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_stripe/flutter_stripe.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
class CheckoutButton extends StatefulWidget {
  final dynamic token;

  final dynamic id;

  final dynamic techId;

  final dynamic userId;

  const CheckoutButton({super.key, required this.token,
      required this.id,
      required this.techId,
      required this.userId
    }
  );

  @override
  State<CheckoutButton> createState() => _CheckoutButtonState();
}

class _CheckoutButtonState extends State<CheckoutButton> {
  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
        onPressed: () async {
          try {
            // 1. Create a payment intent on the backend
            String clientSecret = await createPaymentIntent(50);
            print(clientSecret);

            await presentPaymentSheet(clientSecret);
            final response = await http.post(
                Uri.parse(ServerAPI.baseURL + '/tech/requests/accept'),
                body: jsonEncode({
                  "id": widget.id,
                  "techId": widget.techId,
                  "clientId": widget.userId,
                }),
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ${widget.token}'
                }
            );
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text('Payment Successful!'), backgroundColor: Colors.green,),
            );
          } on StripeException catch (e) {
            // Handle specific Stripe exceptions
            if (e.error.code == 'Canceled') {
              print('Payment process was canceled by the user.');
            } else {
              print('Stripe error: ${e.error.message}');
            }
          } catch (e) {
            // Handle general errors
            print('An error occurred: $e');
          }
        },
        style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
        child: const Text("Accept Task"),
      );
  }


  Future<void> presentPaymentSheet(String clientSecret) async {
    try {
      // Initialize the payment sheet
      await Stripe.instance.initPaymentSheet(
        paymentSheetParameters: SetupPaymentSheetParameters(
          paymentIntentClientSecret: clientSecret,
          merchantDisplayName: 'ELOUSTA',
        ),
      );

      // Present the payment sheet
      await Stripe.instance.presentPaymentSheet();

      print('Payment successful!');
    } catch (e) {
      print('Error: $e');
    }
  }
  Future<String> createPaymentIntent(int amount) async {
    final response = await http.post(
      Uri.parse(ServerAPI.baseURL + '/tech/create-payment-intent'),
      headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ${widget.token}'},
      body: jsonEncode({'amount': amount, 'currency': "EGP"}),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      return data['clientSecret'];
    } else {
      print(response.body);
      throw Exception('Failed to create PaymentIntent');
    }
  }


}
