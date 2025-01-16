import 'dart:convert';
import 'dart:developer';

import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/screens/resetPasswordScreen.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
class EnterCodeScreen extends StatefulWidget {
  final dynamic type;
  final dynamic method;

  final dynamic user;

  const EnterCodeScreen({super.key, required this.type, required this.method, required this.user});

  @override
  _EnterCodeScreenState createState() => _EnterCodeScreenState();
}

class _EnterCodeScreenState extends State<EnterCodeScreen> {
  final List<TextEditingController> _controllers = List.generate(6, (_) => TextEditingController());
  final List<FocusNode> _focusNodes = List.generate(6, (_) => FocusNode());
  bool _isOTPValid = false;
  String _otp = "";
  bool _validateOTP() {
    _otp = "";
    for(var controller in _controllers) {
      _otp += controller.text;
      if(controller.text.isEmpty) {
        setState(() {
          _isOTPValid = false;
        });
        return false;
      }
    }
    setState(() {
      _isOTPValid = true;
    });
    return true;
  }
  @override
  void dispose() {
    // Clean up controllers and focus nodes
    for (var controller in _controllers) {
      controller.dispose();
    }
    for (var node in _focusNodes) {
      node.dispose();
    }
    super.dispose();
  }

  void _handleInput(String value, int index) {
    if (value.isNotEmpty) {
      // Move to the next field if available
      if (index < _focusNodes.length - 1) {
        _focusNodes[index + 1].requestFocus();
      } else {
        _focusNodes[index].unfocus();
      }
    }
  }

  void _handleBackspace(String value, int index) {
    if (value.isEmpty && index > 0) {
      // Move to the previous field if backspace is pressed on an empty field
      _focusNodes[index - 1].requestFocus();
    }
  }

  Widget _buildOTPField(int index) {
    return Container(
      width: 40,
      height: 50,
      decoration: BoxDecoration(
        color: Colors.deepPurple[100],
        borderRadius: BorderRadius.circular(8),
        // border: Border.all(color: Colors.deepPurple),
      ),
      child: TextField(
        controller: _controllers[index],
        focusNode: _focusNodes[index],
        textAlign: TextAlign.center,
        style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
        keyboardType: TextInputType.number,
        maxLength: 1,
        decoration: const InputDecoration(
          counterText: "",
          border: InputBorder.none,
        ),
        inputFormatters: [FilteringTextInputFormatter.digitsOnly],
        onChanged: (value) => {_handleInput(value, index), _validateOTP()},
        onSubmitted: (_) => _handleInput(_controllers[index].text, index),
        onTapOutside: (event) => _focusNodes[index].unfocus(),
        onEditingComplete: () {
          if (_controllers[index].text.isEmpty && index > 0) {
            _handleBackspace("", index);
          }
        },
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: <Widget>[
          TextButton(
            onPressed: (_validateOTP())
              ? () async {
              var url, response;
              if(widget.method == 'mail') {
                url = Uri.parse('${ServerAPI.baseURL}/mail/verification/${widget.user.emailAddress}/${_otp}');
                response = await http.post(
                  url,
                  headers: {
                    'Content-Type': 'application/json', // Explicitly set JSON Content-Type
                  },
                  body: jsonEncode({
                    "subject": "test send email",
                    "message": "this test message"
                  }),
                );
              }
              else {
                url = Uri.parse('${ServerAPI.baseURL}/twilio-otp/verification');
                response = await http.post(
                  url,
                  headers: {
                    'Content-Type': 'application/json', // Explicitly set JSON Content-Type
                  },
                  body: jsonEncode({
                    "userphonenumber": "+201283348918",
                    "otp": _otp
                  }),
                );
              }
              if(response.statusCode == 200) {
                log(response.body);
                if(response.body == 'true') {
                  // Navigate to email verification screen
                  Navigator.of(context).pushReplacement(
                      MaterialPageRoute(
                          builder: (ctx) => Resetpasswordscreen(type: widget.type, user: widget.user, )
                      )
                  );
                }
                else {
                  ScaffoldMessenger.of(context).clearSnackBars();
                  ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text("Invalid Code")));
                  Navigator.pop(context);
                }
              }
              else {
                ScaffoldMessenger.of(context).clearSnackBars();
                ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text("Something went wrong, please try again")));
                Navigator.pop(context);
              }
            }
            : null,
            child: (_isOTPValid) ? const Text("send", style: TextStyle(color: Colors.deepPurple, fontWeight: FontWeight.bold, fontSize: 18),)
                : Text("send", style: TextStyle(color: Colors.deepPurple[100], fontWeight: FontWeight.bold, fontSize: 18),)
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const SizedBox(height: 20),
            const Text(
              "Enter the Code",
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            Text(
              (widget.method == 'sms')
                  ? 'We\'ve sent a code to your phone number'
                  :'We\'ve sent a code to your email address',
              style: TextStyle(fontSize: 16, color: Colors.grey[600]),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 40),
            // OTP Fields
            Container(
              padding: const EdgeInsets.all(20),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: List.generate(6, (index) => _buildOTPField(index)),
              ),
            ),
            const SizedBox(height: 40),
            // Resend and Cancel Options
            TextButton(
              onPressed: () {
                // Resend the code
              },
              child: const Text(
                "Resend Code",
                style: TextStyle(
                  color: Colors.deepPurple,
                  fontWeight: FontWeight.bold,
                  fontSize: 18
                ),
              ),
            ),
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text(
                "Cancel",
                style: TextStyle(color: Colors.red, fontSize: 16),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
