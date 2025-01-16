import 'dart:convert';
import 'dart:developer';
import 'dart:ffi';
import 'package:country_state_city/country_state_city.dart' as statecity;
import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/screens/Domains.dart';
import 'package:el_ousta/screens/UserSignupContinueScreen.dart';
import 'package:el_ousta/screens/loginScreen.dart';
import 'package:el_ousta/old%20files/techinican_home.dart';
import 'package:el_ousta/screens/profesions.dart';
import 'package:el_ousta/screens/technicianRequestsScreen.dart';
import 'package:email_validator/email_validator.dart';
import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:social_login_buttons/social_login_buttons.dart';
import 'package:el_ousta/common/userTech.dart';
import 'package:http/http.dart' as http;
import '../API/googleSigninApi.dart';
import '../main.dart';
import 'TechSignupContinueScreen.dart';
import '../old files/homeclient.dart';
import 'dart:convert';


class SignupScreen extends StatefulWidget {
  final dynamic type;

  const SignupScreen({super.key, required this.type});

  @override
  State<SignupScreen> createState() => _SignupScreenState();
}

class _SignupScreenState extends State<SignupScreen> {
  final _formKey = GlobalKey<FormState>();
  final List<statecity.City> _allEgyptCities = [];
  List<statecity.City> _filteredCities = [];
  String? _selectedCity;
  final TextEditingController _cityController = TextEditingController();
  final FocusNode _cityFocusNode = FocusNode(); // FocusNode to track focus on the TextField
  bool _isDropdownVisible = false;
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmPasswordController = TextEditingController();
  late bool _passwordVisible = true;
  String? _emailErrorText;
  String? _passwordErrorText;
  String? _confirmPasswordErrorText;
  bool isFormValid = false;

  Future signIn() async {
    await GoogleSignInApi.signOutFromGoogle();
    final user = await GoogleSignInApi.login();
    if (user != null) {
      log(user.email);
      log(user.displayName ?? 'No display name');
      log(user.id);
      log(user.serverAuthCode ?? 'No server auth code');
      Uri url;
      if(widget.type == Type.USER) {
        url = Uri.parse('${ServerAPI.baseURL}/client/signIn/google');
      } else {
        url = Uri.parse('${ServerAPI.baseURL}/tech/signIn/google');
      }
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
        print(data['status']); // Access a specific field
        if(data['status'] != 'fail') {
          log(response.body);
          // Storing the token
          print(data['token']);
          print(data['id']);
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
            Navigator.of(context).push(
              MaterialPageRoute(
                  builder: (ctx) => UserSignupContinueScreen(email: user.email, password: user.id,)
              )
            );
          }
          else {
            Navigator.of(context).push(
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
  void _validateEmail(String value) {
    if (value.isEmpty) {
      setState(() {
        _emailErrorText = 'Email is required';
      });
    } else if (!isEmailValid(value)) {
      setState(() {
        _emailErrorText = 'Enter a valid email address';
      });
    } else {
      setState(() {
        _emailErrorText = null;
      });
    }
    validateForm();
  }

  bool isEmailValid(String email) {
    return EmailValidator.validate(email);
  }
  bool _validatePassword(String password) {
    if(_passwordController.text.length < 8) {
      setState(() {
        _passwordErrorText = "password length must be at least 8 characters";
      });
    }
    else {
      setState(() {
        _passwordErrorText = null;
      });
    }
    validateForm();
    return _passwordController.text.length >= 8;
  }
  bool _validateConfirmPassword(String password) {
    if(_passwordController.text != _confirmPasswordController.text) {
      setState(() {
        _confirmPasswordErrorText = "password must be as above";
      });
    }
    else {
      setState(() {
        _confirmPasswordErrorText = null;
      });
    }
    validateForm();
    return _passwordController.text == _confirmPasswordController.text;
  }

  @override
  void initState() {
    super.initState();
    _loadCities();
    _cityController.addListener(_filterCities);
    _cityFocusNode.addListener(() {
      setState(() {
        _isDropdownVisible = _cityFocusNode.hasFocus;
      });
    });
  }

  @override
  void dispose() {
    _cityController.dispose();
    _cityFocusNode.dispose();
    _passwordController.dispose();
    _confirmPasswordController.dispose();
    _emailController.dispose();
    super.dispose();
  }

  Future<void> _loadCities() async {
    try {
      final country = await statecity.getCountryFromCode('EG');
      if (country != null) {
        final cities = await statecity.getCountryCities(country.isoCode);
        setState(() {
          _allEgyptCities.addAll(cities);
          _filteredCities = cities;
        });
      }
    } catch (e) {
      log('Error loading cities: $e');
    }
  }

  void _filterCities() {
    setState(() {
      final input = _cityController.text.toLowerCase();
      _filteredCities = _allEgyptCities
          .where((city) => city.name.toLowerCase().contains(input))
          .toList();
    });
  }
  Future<void> _submitForm() async {
    bool result;
    if(isFormValid) {
      if(widget.type == Type.USER) {
        result = await Navigator.of(context).push(
          MaterialPageRoute(
              builder: (ctx) => UserSignupContinueScreen(
                email: _emailController.text,
                password: _passwordController.text,
              )
          )
        );
      }
      else {
        result = await Navigator.of(context).push(
            MaterialPageRoute(
                builder: (ctx) => TechSignupContinueScreen(
                  email: _emailController.text,
                  password: _passwordController.text,
                )
            )
        );
      }
      setState(() {
        _emailErrorText = "email already in use, sign in instead";
      });
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
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            children: [
              const Text(
                "Let's create an account..",
                style: TextStyle(
                  color: Colors.black,
                  fontSize: 24.0,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 32.0),
              Form(
                key: _formKey,
                child: Column(
                  children: [

                    TextFormField(
                      decoration: InputDecoration(
                        labelText: "Email",
                        prefixIcon: const Icon(Icons.email_outlined),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(8.0),
                        ),
                        errorText: _emailErrorText,
                      ),
                      controller: _emailController,
                      validator: (value) => _emailErrorText,
                      onChanged: _validateEmail,
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
                    const SizedBox(height: 16.0),
                    TextFormField(
                      decoration: InputDecoration(
                        labelText: "Confirm Password",
                        prefixIcon: const Icon(Icons.password),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(8.0),
                        ),
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
                        errorText: _confirmPasswordErrorText
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
                          backgroundColor: isFormValid ? Colors.deepPurple : Colors.purple.shade50, // Background color
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8.0), // Rounded corners
                          ),
                          padding: const EdgeInsets.symmetric(vertical: 12.0, horizontal: 20.0), // Optional for sizing
                        ),
                        child: const Text(
                          "Continue",
                          style: TextStyle(
                            color: Colors.white, // Text color
                            fontSize: 18.0, // Font size
                            fontWeight: FontWeight.bold
                          ),
                        ),
                      ),
                    ),
                    // const SizedBox(height: 12.0),
                    Row(
                      children: [
                        const Text("Already have an Account?", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 14, color: Colors.grey),),
                        TextButton(onPressed: () {
                          dynamic result = Navigator.of(context).push(
                              MaterialPageRoute(
                                  builder: (ctx) => LoginScreen(
                                    type: widget.type,
                                  )
                              )
                          );
                        }, child: const Text("Login", style: TextStyle(color: Colors.deepPurple, fontWeight: FontWeight.bold),))
                      ],
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
    );
  }
}