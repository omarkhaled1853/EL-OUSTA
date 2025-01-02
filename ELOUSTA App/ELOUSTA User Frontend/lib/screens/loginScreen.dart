import 'dart:convert';
import 'dart:developer';
import 'dart:ffi';

// import 'package:country_state_city/country_state_city.dart' as statecity;
import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/screens/Domains.dart';
import 'package:el_ousta/screens/UserSignupContinueScreen.dart';
import 'package:el_ousta/screens/enterCodeScreen.dart';
import 'package:el_ousta/screens/enterUsernameScreen.dart';
import 'package:el_ousta/screens/forgetPasswordScreen.dart';
import 'package:el_ousta/screens/profesions.dart';
import 'package:el_ousta/old%20files/techinican_home.dart';
import 'package:el_ousta/screens/technicianRequestsScreen.dart';
import 'package:email_validator/email_validator.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:social_login_buttons/social_login_buttons.dart';
import 'package:el_ousta/common/userTech.dart';
import 'package:http/http.dart' as http;
import '../API/googleSigninApi.dart';
import 'TechSignupContinueScreen.dart';
import '../old files/homeclient.dart';

class LoginScreen extends StatefulWidget {
  final dynamic type;

  const LoginScreen({super.key, required this.type});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _usernameController = TextEditingController();
  final secureStorage = const FlutterSecureStorage();
  late bool _passwordVisible = true;
  String? _passwordErrorText = null;
  String? usernameErrorText = null;
  bool _isUsernameValid = false;
  bool _isPasswordValid = false;
  bool isFormValid = false;
  bool loggedIn = false;

  Future signIn() async {
    await GoogleSignInApi.signOutFromGoogle();
    final user = await GoogleSignInApi.login();
    if (user != null) {
      log(user.email);
      log(user.displayName ?? 'No display name');
      log(user.id);
      log(user.serverAuthCode ?? 'No server auth code');
      var url;
      if(widget.type == Type.USER)
        url = Uri.parse(ServerAPI.baseURL + '/client/signIn/google');
      else
        url = Uri.parse(ServerAPI.baseURL + '/tech/signIn/google');
      // make http get request
      var response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json', // Explicitly set JSON Content-Type
        },
        body: jsonEncode({
          'emailAddress': user.email,
        }),
      );
      // check the status code for the result
      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        print(data); // Prints the parsed JSON object (Map or List)
        print(data['token']); // Access a specific field
        if(data['status'] != 'fail') {
          log(response.body);
          // Storing the token
          await secureStorage.write(key: 'auth_token', value: data['token']);
          await secureStorage.write(key: 'id', value: data['id']);
          await secureStorage.write(key: 'type', value: (widget.type == Type.USER) ? 'USER' : 'TECH');
          Navigator.of(context).pushReplacement(
              MaterialPageRoute(
                  builder: (ctx) => (widget.type == Type.USER) ? const DomainPage() : RequestHomePage()
              )
          );
        }
        else {
          log("Sign in failed with status: ${response.statusCode}.");
          if(widget.type == Type.USER) {
            Navigator.of(context).pushReplacement(
                MaterialPageRoute(
                    builder: (ctx) => UserSignupContinueScreen(email: user.email, password: user.id,)
                )
            );
          }
          else {
            Navigator.of(context).pushReplacement(
                MaterialPageRoute(
                    builder: (ctx) => TechSignupContinueScreen(email: user.email, password: user.id,)
                )
            );
          }
        }
      }
      else {
        log('Request failed with status: ${response.statusCode}.');
        ScaffoldMessenger.of(context).clearSnackBars();
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text("Something went wrong, please try again")));
      }
    } else {
      log('User canceled the sign-in process');
    }
  }
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

  bool isEmailValid(String email) {
    return EmailValidator.validate(email);
  }
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

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _passwordController.dispose();
    _usernameController.dispose();
    super.dispose();
  }
  Future<void> _submitForm() async {
    dynamic result = true;
    if(isFormValid) {
      var url;
      if(widget.type == Type.USER)
        url = Uri.parse(ServerAPI.baseURL + '/client/signIn');
      else
        url = Uri.parse(ServerAPI.baseURL + '/tech/signIn');
      // make http get request
      var response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonEncode({
          'username': _usernameController.text,
          'password': _passwordController.text,
        }),
      );
      if(response.statusCode == 200) {
        final data = jsonDecode(response.body);
        print(data); // Prints the parsed JSON object (Map or List)
        print(data['token']); // Access a specific field
        // Storing the token
        if(data['status'] != 'fail') {
          await secureStorage.write(key: 'auth_token', value: data['token']);
          await secureStorage.write(key: 'id', value: data['id']);
          await secureStorage.write(key: 'type', value: (widget.type == Type.USER) ? 'USER' : 'TECH');

          result = Navigator.of(context).pushReplacement(
              MaterialPageRoute(
                  builder: (ctx) => (widget.type == Type.USER) ? const DomainPage() : RequestHomePage()
              )
          );
        }
        else {
          setState(() {
            usernameErrorText = "";
            _passwordErrorText = "";
          });
          ScaffoldMessenger.of(context).clearSnackBars();
          ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text("Something went wrong, please try again")));
        }
      }
      else {
        log("Sign in failed with status: ${response.statusCode}.");
        setState(() {
          usernameErrorText = "";
          _passwordErrorText = "";
        });
        ScaffoldMessenger.of(context).clearSnackBars();
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text("Something went wrong, please try again")));
      }
    }
  }
  void validateForm() {
    setState(() {
      isFormValid = _formKey.currentState?.validate() ?? false;
      isFormValid = _isUsernameValid && _isPasswordValid;
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
                    "Fill In the Fields Below to Return to your Account",
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
                      // const SizedBox(height: 16.0),
                      TextButton(
                          onPressed: () {
                            Navigator.of(context).pushReplacement(
                                MaterialPageRoute(
                                    builder: (ctx) => EnterusernameScreen(type: widget.type)
                                )
                            );
                          },
                          child: const Align(
                            alignment: Alignment.centerLeft,
                            child: Text(
                              "Forget Password?",
                              textAlign: TextAlign.right,
                              style: TextStyle(
                                color: Colors.deepPurple,
                                fontSize: 16.0
                              ),
                            ),

                          )
                      ),
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
                            "Login",
                            style: TextStyle(
                                color: Colors.white, // Text color
                                fontSize: 18.0, // Font size
                                fontWeight: FontWeight.bold
                            ),
                          ),
                        ),
                      ),

                      const SizedBox(height: 16.0),
                      const Row(
                          children: <Widget>[
                            Expanded(
                                child: Divider()
                            ),
                            Text("or", style: TextStyle(color: Colors.deepPurple),),
                            Expanded(
                                child: Divider()
                            ),
                          ]
                      ),
                      const SizedBox(height: 16,),
                      SocialLoginButton(
                        buttonType: SocialLoginButtonType.google,
                        onPressed: signIn,
                      )
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