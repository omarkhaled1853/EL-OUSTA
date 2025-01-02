import 'dart:convert';
import 'dart:developer';

import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/screens/enterCodeScreen.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class ForgetPasswordScreen extends StatefulWidget {
  const ForgetPasswordScreen(
      {super.key, required this.type, required this.user});
  final dynamic type;
  final dynamic user;

  @override
  State<ForgetPasswordScreen> createState() => _ForgetPasswordScreenState();
}

class _ForgetPasswordScreenState extends State<ForgetPasswordScreen> {
  bool isLoading = false; // Tracks loading state
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const SizedBox(height: 20),
            const Text(
              "Forgot Your Password?",
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 10),
            Text(
              "Choose how you'd like to reset your password.",
              style: TextStyle(fontSize: 16, color: Colors.grey[600]),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 40),
            // Email Option
            GestureDetector(
              onTap: () async {
                setState(() {
                  isLoading = true; // Show loading indicator
                });

                var url = Uri.parse(
                    ServerAPI.baseURL + '/mail/send/${widget.user.emailAddress}');
                try {
                  var response = await http.post(
                    url,
                    headers: {
                      'Content-Type':
                          'application/json', // Explicitly set JSON Content-Type
                    },
                    body: jsonEncode({
                      "subject": "test send email",
                      "message": "this test message"
                    }),
                  );

                  if (response.statusCode == 200) {
                    log(response.body);
                    // Navigate to email verification screen
                    Navigator.of(context).pushReplacement(
                      MaterialPageRoute(
                        builder: (ctx) => EnterCodeScreen(
                          type: widget.type,
                          method: 'mail',
                          user: widget.user,
                        ),
                      ),
                    );
                  } else {
                    print(response.statusCode);
                    ScaffoldMessenger.of(context).clearSnackBars();
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                          content:
                              Text("Something went wrong, please try again")),
                    );
                  }
                } catch (e) {
                  ScaffoldMessenger.of(context).clearSnackBars();
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text("Error: $e")),
                  );
                } finally {
                  setState(() {
                    isLoading = false; // Hide loading indicator
                  });
                }
              },
              child: Card(
                elevation: 4,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Row(
                    children: [
                      Icon(Icons.email, color: Colors.deepPurple, size: 40),
                      const SizedBox(width: 16),
                      isLoading
                          ? const CircularProgressIndicator() // Show spinner if loading
                          : const Text(
                              "Send code via Email",
                              style: TextStyle(fontSize: 18),
                            ),
                    ],
                  ),
                ),
              ),
            ),
            const SizedBox(height: 20),
            // SMS Option
            GestureDetector(
              onTap: () async {
                setState(() {
                  isLoading = true; // Show loading indicator
                });

                var url = Uri.parse(
                    ServerAPI.baseURL + '/twilio-otp/twilio-sms');
                try {
                  var response = await http.post(
                    url,
                    headers: {
                      'Content-Type':
                      'application/json', // Explicitly set JSON Content-Type
                    },
                    body: jsonEncode({
                      "phonenumber": "+201283348918",
                      "massage": "test otp"
                    }),
                  );

                  if (response.statusCode == 200) {
                    log(response.body);
                    // Navigate to email verification screen
                    Navigator.of(context).pushReplacement(
                      MaterialPageRoute(
                        builder: (ctx) => EnterCodeScreen(
                          type: widget.type,
                          method: 'sms',
                          user: widget.user,
                        ),
                      ),
                    );
                  } else {
                    ScaffoldMessenger.of(context).clearSnackBars();
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                          content:
                          Text("Something went wrong, please try again")),
                    );
                  }
                } catch (e) {
                  ScaffoldMessenger.of(context).clearSnackBars();
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text("Error: $e")),
                  );
                } finally {
                  setState(() {
                    isLoading = false; // Hide loading indicator
                  });
                }
              },
              child: Card(
                elevation: 4,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Row(
                    children: [
                      const Icon(Icons.sms, color: Colors.deepPurple, size: 40),
                      const SizedBox(width: 16),
                      isLoading
                          ? const CircularProgressIndicator() // Show spinner if loading
                          : const Text(
                        "Send code via SMS",
                        style: TextStyle(fontSize: 18),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            const Spacer(),
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text(
                "Back to Login",
                style: TextStyle(color: Colors.deepPurple, fontSize: 18.0),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
