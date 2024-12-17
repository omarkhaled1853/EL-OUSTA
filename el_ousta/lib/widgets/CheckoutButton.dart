import 'dart:math';

import 'package:el_ousta/API/serverAPI.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_stripe/flutter_stripe.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
class CheckoutButton extends StatefulWidget {
  const CheckoutButton({super.key});

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
            String clientSecret = await createPaymentIntent(50); // $10.00
            print(clientSecret);
            // 2. Show the payment sheet
            await presentPaymentSheet(clientSecret);

            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text('Payment Successful!'), backgroundColor: Colors.green,),
            );
          } catch (e) {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text('Payment Failed: $e')),
            );
          }
        },
        child: Text('Pay Now'),
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
      headers: {'Content-Type': 'application/json'},
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
