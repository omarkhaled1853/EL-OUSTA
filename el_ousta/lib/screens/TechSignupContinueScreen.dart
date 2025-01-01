import 'dart:convert';
import 'dart:developer';
import 'dart:ffi';

import 'package:country_state_city/country_state_city.dart' as statecity;
import 'package:el_ousta/models/Technician.dart';
import 'package:el_ousta/old%20files/techinican_home.dart';
import 'package:el_ousta/screens/technicianRequestsScreen.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:intl/intl.dart';
import 'package:social_login_buttons/social_login_buttons.dart';
import 'package:http/http.dart' as http;
import '../API/serverAPI.dart';
import '../data/professions.dart';
import '../main.dart';
import '../old files/homeclient.dart';

class TechSignupContinueScreen extends StatefulWidget {
  final dynamic email;
  final dynamic password;

  // final dynamic type;

  const TechSignupContinueScreen(
      {super.key, required this.email, required this.password});

  @override
  State<TechSignupContinueScreen> createState() =>
      _TechSignupContinueScreenState();
}

class _TechSignupContinueScreenState extends State<TechSignupContinueScreen> {
  final List<statecity.City> _allEgyptCities = [];
  List<statecity.City> _filteredCities = [];
  String? _selectedCity;
  final TextEditingController _cityController = TextEditingController();
  final FocusNode _cityFocusNode =
      FocusNode(); // FocusNode to track focus on the TextField
  bool _isDropdownVisible = false;
  final TextEditingController _phoneNumberController = TextEditingController();
  bool _isValidPhoneNumber = true; // Initial state
  final TextEditingController _firstNameController = TextEditingController();
  final TextEditingController _lastNameController = TextEditingController();
  final TextEditingController _usernameController = TextEditingController();

  bool _isFirstNameValid = false;
  bool _isLastNameValid = false;
  bool _isUsernameValid = false;
  bool _isCityValid = false;
  bool _isDomainValid = false;
  bool isFormValid = false;

  String? lnErrorText = null;
  String? fnErrorText = null;
  String? usernameErrorText = null;
  String? phoneErrorText = null;
  String? dobErrorText = null;
  String? cityErrorText = null;
  String? domainErrorText = null;
  String? selectedProfession;
  int? selectedProfessionId;

  late List<dynamic> domainList;
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _dobController = TextEditingController();
  bool _isDobValid = true;
  DateTime? _selectedDob;
  Future<void> _submitForm() async {
    if (isFormValid) {
      // Technician userInfo = Technician(
      //     username: _usernameController.text,
      //     password: widget.password,
      //     emailAddress: widget.email,
      //     firstName: _firstNameController.text,
      //     lastName: _lastNameController.text,
      //     dob: DateTime.parse(_dobController.text),
      //     phoneNumber: _phoneNumberController.text,
      //     city: _cityController.text,
      //     roles: 'ROLE_USER',
      //     // domain: selectedProfession as String,
      //     signUpDate: new DateTime.now(),
      //     technicianNotifications: [],
      //     description: "experienced technition",
      //     jobStartDate: null,
      //     profilePicture: null,
      //     domainEntity: {
      //       id: 1
      //     }
      //     rates: null
      // );

      var url = Uri.parse(ServerAPI.baseURL + '/tech/signUp');
      // make http get request
      var response = await http.post(url,
          headers: {
            'Content-Type':
                'application/json', // Explicitly set JSON Content-Type
          },
          body: jsonEncode({
            'username': _usernameController.text,
            'password': widget.password,
            'emailAddress': widget.email,
            'firstName': _firstNameController.text,
            'lastName': _lastNameController.text,
            'dob': DateTime.parse(_dobController.text).toIso8601String(), // Convert DateTime to ISO format
            'phoneNumber': _phoneNumberController.text,
            'city': _cityController.text,
            'roles': 'ROLE_USER',
            'domainEntity': {
              'id': selectedProfessionId
            },
            'signUpDate': (new DateTime.now()).toIso8601String(), // Convert DateTime to ISO format
            'technicianNotifications': [],
            'jobStartDate': '1923-07-23',
            'profilePicture': null,
            'rate': 0.0
          })
      );
      // check the status code for the result
      if (response.statusCode == 200) {
        log(response.body);
        if(response.body != 'Invalid username' && response.body != 'Invalid email address') {
          String id = response.body;
          url = Uri.parse(ServerAPI.baseURL + '/tech/signIn');
          // make http get request
          response = await http.post(
            url,
            headers: {
              'Content-Type': 'application/json',
            },
            body: jsonEncode({
              'username': _usernameController.text,
              'password': widget.password,
            }),
          );
          if (response.statusCode == 200) {
            final data = jsonDecode(response.body);
            print(data); // Prints the parsed JSON object (Map or List)
            print(data['token']); // Access a specific field
            // Storing the token
            await secureStorage.write(key: 'auth_token', value: data['token']);
            await secureStorage.write(key: 'id', value: id);
            await secureStorage.write(key: 'type', value: 'TECH');
            Navigator.of(context)
                .pushReplacement(MaterialPageRoute(builder: (ctx) => RequestHomePage()));
          } else {
            log("Sign in failed with status: ${response.statusCode}.");
          }
        } else if (response.body == 'Invalid username') {
          setState(() {
            usernameErrorText =
                "username already in use, please choose another one.";
          });
        } else {
          Navigator.pop(context, false);
        }
      } else {
        log('Request failed with status: ${response.statusCode}.');
      }
    }
  }

  void _validateDomain() {
    setState(() {
      if (selectedProfession != null) {
        domainErrorText = null;
        _isDomainValid = true;
      } else {
        domainErrorText = "please specify your domain from below";
        _isDomainValid = false;
      }
    });
    validateForm();
  }

  void _validateDob() {
    setState(() {
      if (_selectedDob == null) {
        _isDobValid = false;
        return;
      }
      final today = DateTime.now();
      final age = today.year - _selectedDob!.year;
      if (_selectedDob!.month > today.month ||
          (_selectedDob!.month == today.month &&
              _selectedDob!.day > today.day)) {
        _isDobValid = age - 1 >= 18;
      } else {
        _isDobValid = age >= 18;
      }
    });
    validateForm();
  }

  void validateForm() {
    setState(() {
      isFormValid = _formKey.currentState?.validate() ?? false;
      isFormValid = _isDobValid &&
          _isCityValid &&
          _isLastNameValid &&
          _isValidPhoneNumber &&
          _isUsernameValid &&
          _isFirstNameValid;
    });
  }

  void _validateFirstName(String firstName) {
    setState(() {
      if (firstName.isNotEmpty) {
        fnErrorText = null;
        _isFirstNameValid = true;
      } else {
        fnErrorText = "field can't be empty";
        _isFirstNameValid = false;
      }
    });
    validateForm();
  }

  void _validateCity() {
    setState(() {
      if (_allEgyptCities.any((city) => city.name == _cityController.text)) {
        cityErrorText = null;
        _isCityValid = true;
      } else {
        cityErrorText = "please select a city from the list below";
        _isCityValid = false;
      }
    });
    validateForm();
  }

  void _validateLastName(String lastName) {
    setState(() {
      if (lastName.isNotEmpty) {
        lnErrorText = null;
        _isLastNameValid = true;
      } else {
        lnErrorText = "field can't be empty";
        _isLastNameValid = false;
      }
    });
    validateForm();
  }

  void _validateUsername(String username) {
    setState(() {
      if (username.isNotEmpty) {
        usernameErrorText = null;
        _isUsernameValid = true;
      } else {
        usernameErrorText = "field can't be empty";
        _isUsernameValid = false;
      }
    });
    validateForm();
  }

  void _validatePhoneNumber(String phoneNumber) {
    setState(() {
      if (phoneNumber.length == 14 && phoneNumber.startsWith("+20 ")) {
        phoneErrorText = null;
        _isValidPhoneNumber = true;
      } else {
        phoneErrorText = "please enter a valid phone number";
        _isValidPhoneNumber = false;
      }
    });
    validateForm();
  }

  @override
  void initState() {
    super.initState();
    _phoneNumberController.text = "+20 ";
    _loadCities();
    _cityController.addListener(_filterCities);
    _cityFocusNode.addListener(() {
      setState(() {
        _isDropdownVisible = _cityFocusNode.hasFocus;
      });
    });
    fetchAllDomains();
  }

  Future<void> fetchAllDomains() async {
    try {
      final response = await http.get(
          Uri.parse(ServerAPI.baseURL + '/client/home/'),
          // headers: {'Authorization': 'Bearer $token'}
      );
      print(response.statusCode);
      if (response.statusCode == 200) {
        final List<dynamic> responseData = json.decode(response.body);
        setState(() {
          domainList = responseData.cast<Map<String, dynamic>>();
          print(domainList);
        });
      } else {
        throw Exception('Failed to fetch domain list');
      }
    } catch (error) {
      print('Error fetching domains: $error');
    }
  }

  @override
  void dispose() {
    _cityController.dispose();
    _cityFocusNode.dispose();
    _usernameController.dispose();
    _firstNameController.dispose();
    _lastNameController.dispose();
    _dobController.dispose();
    _phoneNumberController.dispose();
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
                "Almost There..",
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
                    Row(
                      children: [
                        Expanded(
                          child: TextFormField(
                              controller: _firstNameController,
                              decoration: InputDecoration(
                                labelText: "First Name",
                                prefixIcon: const Icon(Icons.person),
                                border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                errorText: fnErrorText,
                              ),
                              onChanged: _validateFirstName,
                              validator: (value) => fnErrorText),
                        ),
                        const SizedBox(width: 16.0),
                        Expanded(
                          child: TextFormField(
                              controller: _lastNameController,
                              decoration: InputDecoration(
                                labelText: "Last Name",
                                prefixIcon: const Icon(Icons.person),
                                border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                errorText: lnErrorText,
                              ),
                              onChanged: _validateLastName,
                              validator: (value) => lnErrorText),
                        ),
                      ],
                    ),
                    const SizedBox(height: 16.0),
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
                        validator: (value) => usernameErrorText),
                    const SizedBox(height: 16.0),
                    TextFormField(
                        decoration: InputDecoration(
                          labelText: "Phone number",
                          prefixIcon: const Icon(Icons.phone),
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(8.0),
                          ),
                          errorText: (_isValidPhoneNumber)
                              ? null
                              : "Enter a valid phone number",
                        ),
                        keyboardType: TextInputType.number,
                        inputFormatters: <TextInputFormatter>[
                          FilteringTextInputFormatter.allow(
                              RegExp(r'^[0-9+\- ]*$')),
                        ],
                        controller: _phoneNumberController,
                        onChanged: _validatePhoneNumber,
                        validator: (value) => phoneErrorText),
                    const SizedBox(height: 16.0),
                    TextFormField(
                      controller: _dobController,
                      decoration: InputDecoration(
                        labelText: "Date of Birth",
                        prefixIcon: const Icon(Icons.calendar_today),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(8.0),
                        ),
                        errorText: _isDobValid
                            ? null
                            : "You must be 18 years or older",
                      ),
                      readOnly: true,
                      onTap: () async {
                        // Show date picker
                        final DateTime? pickedDate = await showDatePicker(
                          context: context,
                          initialDate: DateTime(2000), // Default date
                          firstDate: DateTime(1900), // Earliest date
                          lastDate: DateTime.now(), // Latest date
                        );
                        if (pickedDate != null) {
                          setState(() {
                            _selectedDob = pickedDate;
                            _dobController.text =
                                DateFormat('yyyy-MM-dd').format(pickedDate);
                          });
                          _validateDob();
                        }
                      },
                      validator: (value) {
                        if (!_isDobValid) {
                          return 'You must be 18 years or older';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16.0),
                    TextFormField(
                        controller: _cityController,
                        focusNode: _cityFocusNode,
                        decoration: InputDecoration(
                          labelText: "City",
                          prefixIcon: const Icon(Icons.location_city),
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(8.0),
                          ),
                          // errorText: (_isCityValid) ? null : "please choose a city from the menu"
                        ),
                        onChanged: (value) {
                          _validateCity();
                        },
                        validator: (value) => cityErrorText),
                    if (_isDropdownVisible && _filteredCities.isNotEmpty)
                      Material(
                        elevation: 2.0,
                        child: ListView.builder(
                          shrinkWrap: true,
                          itemCount: _filteredCities.length,
                          itemBuilder: (context, index) {
                            return ListTile(
                              title: Text(_filteredCities[index].name),
                              onTap: () {
                                setState(() {
                                  _selectedCity = _filteredCities[index].name;
                                  _cityController.text = _selectedCity!;
                                  _filteredCities =
                                      _allEgyptCities; // Reset filter
                                  _isDropdownVisible = false;
                                  _cityFocusNode
                                      .unfocus(); // Dismiss the keyboard
                                  _validateCity();
                                  _isCityValid = true;
                                });
                              },
                            );
                          },
                        ),
                      ),
                    if (_isDropdownVisible &&
                        _filteredCities.isEmpty &&
                        _cityController.text.isNotEmpty)
                      const Text('No matching cities found.'),
                    const SizedBox(height: 16.0),
                    DropdownButtonFormField<int>(
                      value: selectedProfessionId,
                      decoration: InputDecoration(
                          labelText: 'Choose a Profession',
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(8.0),
                          ),
                          contentPadding: const EdgeInsets.symmetric(
                              horizontal: 16.0, vertical: 18.0),
                          errorText: domainErrorText),
                      icon: const Icon(Icons.arrow_drop_down),
                      items: domainList.map((domain) {
                        return DropdownMenuItem<int>(
                          value: domain['id'], // Assign the ID as the value
                          child: Text(domain['name']), // Show the name in the dropdown
                        );
                      }).toList(),
                      onChanged: (int? newValue) {
                        setState(() {
                          selectedProfessionId = newValue!; // Assign the selected ID
                          print(selectedProfessionId);
                        });
                      },
                      validator: (value) => domainErrorText,
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
                            borderRadius:
                                BorderRadius.circular(8.0), // Rounded corners
                          ),
                          padding: const EdgeInsets.symmetric(
                              vertical: 12.0,
                              horizontal: 20.0), // Optional for sizing
                        ),
                        child: const Text(
                          "Create Account",
                          style: TextStyle(
                              color: Colors.white, // Text color
                              fontSize: 18.0, // Font size
                              fontWeight: FontWeight.bold),
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
    );
  }
}
