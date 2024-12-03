import 'package:el_ousta/screens/enterCodeScreen.dart';
import 'package:flutter/material.dart';

class ForgetPasswordScreen extends StatelessWidget {
  const ForgetPasswordScreen({super.key, required this.type});
  final dynamic type;
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
              onTap: () {
                // Navigate to email verification screen
                Navigator.of(context).pushReplacement(
                    MaterialPageRoute(
                        builder: (ctx) => EnterCodeScreen(type: type, method: 'mail',)
                    )
                );
              },
              child: Card(
                elevation: 4,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
                child: const Padding(
                  padding: EdgeInsets.all(16.0),
                  child: Row(
                    children: [
                      Icon(Icons.email, color: Colors.deepPurple, size: 40),
                      SizedBox(width: 16),
                      Text(
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
              onTap: () {
                // Navigate to SMS verification screen
                Navigator.of(context).pushReplacement(
                    MaterialPageRoute(
                        builder: (ctx) => EnterCodeScreen(type: type, method: 'sms', )
                    )
                );
              },
              child: Card(
                elevation: 4,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
                child: const Padding(
                  padding: EdgeInsets.all(16.0),
                  child: Row(
                    children: [
                      Icon(Icons.sms, color: Colors.deepPurple, size: 40),
                      SizedBox(width: 16),
                      Text(
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
