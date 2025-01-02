import 'dart:convert';
import 'dart:developer';
import 'dart:ffi';

// import 'package:country_state_city/country_state_city.dart' as statecity;
import 'package:el_ousta/screens/UserSignupContinueScreen.dart';
import 'package:el_ousta/screens/forgetPasswordScreen.dart';
import 'package:email_validator/email_validator.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:social_login_buttons/social_login_buttons.dart';
import 'package:el_ousta/common/userTech.dart';
import 'package:http/http.dart' as http;
import '../API/googleSigninApi.dart';
import '../API/serverAPI.dart';
import '../models/User.dart';
import 'TechSignupContinueScreen.dart';

class EnterusernameScreen extends StatefulWidget {
  final dynamic type;

  const EnterusernameScreen({super.key, required this.type});

  @override
  State<EnterusernameScreen> createState() => _EnterusernameScreenState();
}

class _EnterusernameScreenState extends State<EnterusernameScreen> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _usernameController = TextEditingController();
  String? usernameErrorText = null;
  bool _isUsernameValid = false;
  bool isFormValid = false;
  // bool loggedIn = false;

  void _validateUsername(String username) {
    setState(() {
      if(username.isNotEmpty) {
        usernameErrorText = null;
        _isUsernameValid = true;
      } else {
        usernameErrorText = "field can't be empty";
        _isUsernameValid = false;
      }
    });
    validateForm();
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _usernameController.dispose();
    super.dispose();
  }
  void _submitForm() async {
    dynamic result = true;
    if(isFormValid) {
      var url, user;
      if(widget.type == Type.USER) {
        url = Uri.parse(ServerAPI.baseURL + "/client/fetchUser");
      }
      else {
        url = Uri.parse(ServerAPI.baseURL + "/tech/fetchTch");
      }
      final response = await http.post(
          url,
          headers: {
            'Content-Type': 'application/json',
          },
          body: jsonEncode({
            'username': _usernameController.text,
          }),
      );
      print(response.statusCode);
      if (response.statusCode == 200) {
        // If the server did return a 200 OK response,
        // then parse the JSON.
        print(response.body);
        // user = User.fromJson(jsonDecode(response.body) as Map<String, dynamic>);
        final data = jsonDecode(response.body);
        User user = new User(
          username: data['username'],
          password: data['password'],
          emailAddress: data['emailAddress'],
          firstName: data['firstName'],
          lastName: data['lastName'],
          dob: DateTime.parse(data['dob'] as String),
          phoneNumber: data['phoneNumber'],
          city: data['city'],
          signUpDate: DateTime.parse(data['signUpDate']),
          roles: data['roles'],
          clientNotifications: []
        );
        log(user.phoneNumber);
        log(user.emailAddress);
        Navigator.of(context).push(
            MaterialPageRoute(
                builder: (ctx) => ForgetPasswordScreen(type: widget.type, user: user,)
            )
        );
      }
      else {
        ScaffoldMessenger.of(context).clearSnackBars();
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text("Couldn't find username, please recheck your username and try again")));
      }
    }
  }
  void validateForm() {
    setState(() {
      isFormValid = _formKey.currentState?.validate() ?? false;
    });
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: SingleChildScrollView(
        child: Container(
          constraints: BoxConstraints(minHeight: MediaQuery.of(context).size.height),
          child: Padding(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center, // Centers horizontally
              mainAxisAlignment: MainAxisAlignment.start, // Aligns content at the top
              children: [
                const Center(
                  child: Text(
                    "Enter your username",
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
                          controller: _usernameController,
                          decoration: InputDecoration(
                            labelText: "Username",
                            prefixIcon: const Icon(Icons.edit),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(8.0),
                            ),
                            errorText: usernameErrorText,
                          ),
                          onChanged: _validateUsername,
                          validator: (value) => usernameErrorText
                      ),
                      const SizedBox(height: 16.0),
                      SizedBox(
                        width: double.infinity,
                        child: ElevatedButton(
                          onPressed: () {
                            _submitForm();
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: isFormValid ? Colors.deepPurple : Colors.purple.shade50, // Background color
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(8.0), // Rounded corners
                            ),
                            padding: const EdgeInsets.symmetric(vertical: 12.0, horizontal: 20.0), // Optional for sizing
                          ),
                          child: const Text(
                            "Send Code",
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