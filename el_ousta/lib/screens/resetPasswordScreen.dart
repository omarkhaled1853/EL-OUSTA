import 'dart:convert';
import 'dart:developer';

import 'package:el_ousta/old%20files/techinican_home.dart';
import 'package:el_ousta/screens/profesions.dart';
import 'package:el_ousta/screens/technicianRequestsScreen.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:el_ousta/common/userTech.dart';
import 'package:http/http.dart' as http;
import '../API/serverAPI.dart';
import '../old files/homeclient.dart';
class Resetpasswordscreen extends StatefulWidget {
  final dynamic type;
  final dynamic user;

  const Resetpasswordscreen({super.key, required this.type, required this.user});

  @override
  State<Resetpasswordscreen> createState() => _ResetpasswordscreenState();
}

class _ResetpasswordscreenState extends State<Resetpasswordscreen> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmPasswordController = TextEditingController();
  final secureStorage = const FlutterSecureStorage();
  late bool _passwordVisible = true;
  String? _passwordErrorText = null;
  String? _confirmPasswordErrorText = null;
  bool _isPasswordValid = false;
  bool _isConfirmPasswordValid = false;
  bool isFormValid = false;
  bool loggedIn = false;

  bool _validatePassword(String password) {
    if(_passwordController.text.length < 8) {
      setState(() {
        _passwordErrorText = "password length must be at least 8 characters";
        _isPasswordValid = false;
      });
    }
    else {
      setState(() {
        _passwordErrorText = null;
        _isPasswordValid = true;
      });
    }
    validateForm();
    return _passwordController.text.length >= 8;
  }
  bool _validateConfirmPassword(String password) {
    if(_passwordController.text != _confirmPasswordController.text) {
      setState(() {
        _confirmPasswordErrorText = "password must be as above";
        _isConfirmPasswordValid = false;
      });
    }
    else {
      setState(() {
        _confirmPasswordErrorText = null;
        _isConfirmPasswordValid = true;
      });
    }
    validateForm();
    return _confirmPasswordController.text != _passwordController.text;
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _passwordController.dispose();
    super.dispose();
  }
  void _submitForm() async {

    if(isFormValid) {
      var url;
      if(widget.type == Type.USER) {
        url = Uri.parse(ServerAPI.baseURL + '/client/resetPassword');
      } else {
        url = Uri.parse(ServerAPI.baseURL + '/tech/resetPassword');
      }
      // make http get request
      var response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonEncode({
          'username': widget.user.username,
          'password': _passwordController.text,
        }),
      );
      if(response.statusCode == 200) {
        log(response.body);
        // Storing the token
        if(response.body != 'fail') {
          await secureStorage.write(key: 'auth_token', value: response.body);
          await secureStorage.write(key: 'type', value: (widget.type == Type.USER) ? 'USER' : 'TECH');
          Navigator.of(context).pushReplacement(
              MaterialPageRoute(
                  builder: (ctx) => (widget.type == Type.USER) ? ProfessionsScreen(professionType: 'Electrical', token: response.body) : RequestHomePage()
              )
          );
        }
        else {
          setState(() {
            _passwordErrorText = "";
          });
          ScaffoldMessenger.of(context).clearSnackBars();
          ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text("Something went wrong, please try again")));
        }
      }
      else {
        log("Sign in failed with status: ${response.statusCode}.");
        setState(() {
          _passwordErrorText = "";
        });
        ScaffoldMessenger.of(context).clearSnackBars();
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text("Something went wrong, please try again")));
      }
    }
  }
  void validateForm() {
    // isFormValid = _formKey.currentState?.validate() ?? false;
    isFormValid = _isPasswordValid && _isConfirmPasswordValid;
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: SingleChildScrollView(
        child: Container(
          constraints: BoxConstraints(minHeight: MediaQuery
              .of(context)
              .size
              .height),
          child: Padding(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              // Centers horizontally
              mainAxisAlignment: MainAxisAlignment.start,
              // Aligns content at the top
              children: [
                const Center(
                  child: Text(
                    "Enter your new password",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      color: Colors.black,
                      fontSize: 24.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                const SizedBox(height: 32.0),
                Form(
                  key: _formKey,
                  child: Column(
                    children: [
                      TextFormField(
                        decoration: InputDecoration(
                          labelText: "Password",
                          prefixIcon: const Icon(Icons.password),
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(8.0),
                          ),
                          errorText: _passwordErrorText,
                          suffixIcon: IconButton(
                            icon: Icon(_passwordVisible
                                ? Icons.visibility
                                : Icons.visibility_off),
                            onPressed: () {
                              setState(() {
                                _passwordVisible = !_passwordVisible;
                              });
                            },
                          ),
                        ),
                        controller: _passwordController,
                        obscureText: _passwordVisible,
                        validator: (value) => _passwordErrorText,
                        onChanged: _validatePassword,
                      ),
                      const SizedBox(height: 16.0),
                      TextFormField(
                        decoration: InputDecoration(
                          labelText: "re-enter password",
                          prefixIcon: const Icon(Icons.password),
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(8.0),
                          ),
                          errorText: _confirmPasswordErrorText,
                          suffixIcon: IconButton(
                            icon: Icon(_passwordVisible
                                ? Icons.visibility
                                : Icons.visibility_off),
                            onPressed: () {
                              setState(() {
                                _passwordVisible = !_passwordVisible;
                              });
                            },
                          ),
                        ),
                        controller: _confirmPasswordController,
                        obscureText: _passwordVisible,
                        validator: (value) => _confirmPasswordErrorText,
                        onChanged: _validateConfirmPassword,
                      ),
                      const SizedBox(height: 16.0),
                      SizedBox(
                        width: double.infinity,
                        child: ElevatedButton(
                          onPressed: () {
                            _submitForm();
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: isFormValid
                                ? Colors.deepPurple
                                : Colors.purple.shade50, // Background color
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(
                                  8.0), // Rounded corners
                            ),
                            padding: const EdgeInsets.symmetric(vertical: 12.0,
                                horizontal: 20.0), // Optional for sizing
                          ),
                          child: const Text(
                            "Reset password",
                            style: TextStyle(
                                color: Colors.white, // Text color
                                fontSize: 18.0, // Font size
                                fontWeight: FontWeight.bold
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
