import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

class ProfessionsPage extends StatefulWidget {
  const ProfessionsPage({Key? key}) : super(key: key);

  @override
  _ProfessionsPageState createState() => _ProfessionsPageState();
}

class _ProfessionsPageState extends State<ProfessionsPage> {
  List<Map<String, dynamic>> professions = [];
  String _filterOrder = 'ascending';
  final String backendUrl =
      "http://192.168.245.168:8085/admin/profession/get_techs"; // Replace with your actual backend URL

  @override
  void initState() {
    super.initState();
    fetchProfessions(); // Fetch data from the backend
  }

  Future<void> fetchProfessions() async {
    try {
      final response = await http.get(Uri.parse(backendUrl));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        setState(() {
          professions = data.map((item) {
            return {
              "techId": item["techId"],
              "techName": item["techName"],
              "email": item["email"],
              "rate": item["rate"].toString(),
              "complains": item["complainNumber"].toString(),
              "professionName": item["professionName"],
            };
          }).toList();
          // print(professions.toString());
          _sortProfessions(); // Sort initially
        });
      } else {
        print("Error fetching data: ${response.statusCode}");
      }
    } catch (e) {
      print("Error fetching data: $e");
    }
  }

  Future<DialogDTO?> fetchTechnicianDetails(int id) async {
    print("-----------------------------------------------------------");
    print(id);
    final String url =
        'http://192.168.245.168:8085/admin/profession/get_dialog/$id';
    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        print(
            "------------------get data succesfully ------------------------------");
        return DialogDTO.fromJson(json.decode(response.body));
      } else {
        throw Exception("Failed to load technician details");
      }
    } catch (e) {
      debugPrint("Error fetching technician details: $e");
      return null;
    }
  }

  void _sortProfessions() {
    setState(() {
      if (_filterOrder == 'ascending') {
        professions.sort((a, b) =>
            int.parse(a['complains']).compareTo(int.parse(b['complains'])));
      } else {
        professions.sort((a, b) =>
            int.parse(b['complains']).compareTo(int.parse(a['complains'])));
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color.fromARGB(255, 1, 125, 187),
        title: const Text(
          'EL Osta',
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        actions: [
          InkWell(
            onTap: () {
              _showAddProfessionDialog(context);
            },
            onHover: (isHovering) {
              setState(() {
                // Handle hovering effect
              });
            },
            child: const Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Row(
                children: [
                  const Icon(
                    Icons.add,
                    color: Colors.white,
                  ),
                  const SizedBox(width: 8), // Add space between icon and text
                  const Text(
                    'Add Profession',
                    style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
      body: professions.isEmpty
          ? const Center(
              child: CircularProgressIndicator()) // Loading indicator
          : SingleChildScrollView(
              child: Column(
                children: [
                  const Padding(
                    padding: EdgeInsets.symmetric(vertical: 10.0),
                    child: Text(
                      'Technicians',
                      style:
                          TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 20.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Text(
                          'Filter by Complains:',
                          style: TextStyle(
                              fontSize: 16, fontWeight: FontWeight.bold),
                        ),
                        DropdownButton<String>(
                          value: _filterOrder,
                          items: const [
                            DropdownMenuItem(
                              value: 'ascending',
                              child: Text('Ascending'),
                            ),
                            DropdownMenuItem(
                              value: 'descending',
                              child: Text('Descending'),
                            ),
                          ],
                          onChanged: (value) {
                            setState(() {
                              _filterOrder = value!;
                              _sortProfessions();
                            });
                          },
                        ),
                      ],
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 10),
                    child: ListView.builder(
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      itemCount: professions.length,
                      itemBuilder: (context, index) {
                        final profession = professions[index];
                        return Padding(
                          padding: const EdgeInsets.symmetric(
                              horizontal: 15.0, vertical: 5.0),
                          child: Card(
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(25),
                            ),
                            child: ListTile(
                              tileColor: const Color.fromARGB(255, 1, 125, 187),
                              title: Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceEvenly,
                                children: [
                                  buildProfessionInfo(
                                      '', profession['techName']),
                                  buildProfessionInfo('', profession['email']),
                                  buildProfessionInfo('', profession['rate']),
                                  buildProfessionInfo(
                                      '', profession['complains']),
                                  buildProfessionInfo(
                                      '', profession['professionName']),
                                ],
                              ),
                              trailing: Row(
                                mainAxisSize: MainAxisSize.min,
                                children: [
                                  IconButton(
                                    icon: const Icon(Icons.co_present_outlined,
                                        color: Colors.black),
                                    onPressed: () async {
                                      // Safely retrieve the techId
                                      final techId = profession[
                                          'techId']; // Adjust the key name to match your backend response
                                      if (techId != null && techId is int) {
                                        try {
                                          print(
                                              "--------------------------------");
                                          print(techId);

                                          DialogDTO? dialogData =
                                              await fetchTechnicianDetails(
                                                  techId);

                                          if (dialogData != null) {
                                            showDialog(
                                              context: context,
                                              builder: (BuildContext context) {
                                                return AlertDialog(
                                                  title: Text(
                                                      dialogData.techName ??
                                                          'Technician Details'),
                                                  content:
                                                      SingleChildScrollView(
                                                    child: ListBody(
                                                      children: [
                                                        buildDialogRow(
                                                            "Profession",
                                                            dialogData
                                                                .profession),
                                                        buildDialogRow("City",
                                                            dialogData.city),
                                                        buildDialogRow(
                                                            "Email",
                                                            dialogData
                                                                .emailAddress),
                                                        buildDialogRow(
                                                            "Phone",
                                                            dialogData
                                                                .phoneNumber),
                                                        buildDialogRow(
                                                            "Rate",
                                                            dialogData.rate
                                                                .toString()),
                                                        buildDialogRow(
                                                            "Sign-Up Date",
                                                            dialogData
                                                                .signUpDate),
                                                      ],
                                                    ),
                                                  ),
                                                  actions: [
                                                    TextButton(
                                                      onPressed: () {
                                                        Navigator.of(context)
                                                            .pop();
                                                      },
                                                      child:
                                                          const Text("Close"),
                                                    ),
                                                  ],
                                                );
                                              },
                                            );
                                          }
                                        } catch (e) {
                                          print(
                                              "Error fetching technician details: $e");
                                        }
                                      } else {
                                        print(
                                            "techId is missing or not an integer.");
                                      }
                                    },
                                  ),
                                  IconButton(
                                    icon: const Icon(Icons.delete,
                                        color: Colors.black),
                                    onPressed: () async {
                                      final techId = profession[
                                          'techId']; // Adjust the key name to match your backend response
                                      if (techId != null && techId is int) {
                                        bool confirmDelete = await showDialog(
                                              context: context,
                                              builder: (BuildContext context) {
                                                return AlertDialog(
                                                  title: const Text(
                                                      "Confirm Deletion"),
                                                  content: const Text(
                                                      "Are you sure you want to delete this technician?"),
                                                  actions: [
                                                    TextButton(
                                                      onPressed: () => Navigator
                                                              .of(context)
                                                          .pop(false), // Cancel
                                                      child:
                                                          const Text("Cancel"),
                                                    ),
                                                    TextButton(
                                                      onPressed: () => Navigator
                                                              .of(context)
                                                          .pop(true), // Confirm
                                                      child:
                                                          const Text("Delete"),
                                                    ),
                                                  ],
                                                );
                                              },
                                            ) ??
                                            false;

                                        if (confirmDelete) {
                                          try {
                                            final String deleteUrl =
                                                'http://192.168.245.168:8085/admin/profession/deletetech/$techId';
                                            final response = await http
                                                .delete(Uri.parse(deleteUrl));

                                            if (response.statusCode == 200) {
                                              setState(() {
                                                professions.removeWhere(
                                                    (item) =>
                                                        item['techId'] ==
                                                        techId);
                                              });
                                              ScaffoldMessenger.of(context)
                                                  .showSnackBar(
                                                const SnackBar(
                                                    content: Text(
                                                        "Technician deleted successfully")),
                                              );
                                            } else {
                                              throw Exception(
                                                  "Failed to delete technician: ${response.statusCode}");
                                            }
                                          } catch (e) {
                                            ScaffoldMessenger.of(context)
                                                .showSnackBar(
                                              SnackBar(
                                                  content: Text(
                                                      "Error deleting technician: $e")),
                                            );
                                          }
                                        }
                                      } else {
                                        ScaffoldMessenger.of(context)
                                            .showSnackBar(
                                          const SnackBar(
                                              content: Text(
                                                  "Technician ID is invalid.")),
                                        );
                                      }
                                    },
                                  ),
                                ],
                              ),
                            ),
                          ),
                        );
                      },
                    ),
                  ),
                ],
              ),
            ),
    );
  }

  Widget buildProfessionInfo(String label, String value) {
    return Flexible(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            label,
            style: const TextStyle(
                color: Colors.white, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 5),
          Text(
            value,
            style: const TextStyle(color: Colors.white),
          ),
        ],
      ),
    );
  }
}

class DialogDTO {
  final String techName;
  final String profession;
  final String city;
  final String emailAddress;
  final String phoneNumber;
  final double rate;
  final String signUpDate;

  DialogDTO({
    required this.techName,
    required this.profession,
    required this.city,
    required this.emailAddress,
    required this.phoneNumber,
    required this.rate,
    required this.signUpDate,
  });

  factory DialogDTO.fromJson(Map<String, dynamic> json) {
    return DialogDTO(
      techName: json['techName'] ?? '',
      profession: json['profession'] ?? '',
      city: json['city'] ?? '',
      emailAddress: json['emailAddress'] ?? '',
      phoneNumber: json['phoneNumber'] ?? '',
      rate: json['rate']?.toDouble() ?? 0.0,
      signUpDate: json['signUpDate'] ?? '',
    );
  }
}

Widget buildDialogRow(String label, String? value) {
  return Padding(
    padding: const EdgeInsets.symmetric(vertical: 4.0),
    child: Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(
          label,
          style: const TextStyle(fontWeight: FontWeight.bold),
        ),
        Flexible(
          child: Text(
            value ?? 'N/A',
            overflow: TextOverflow.ellipsis,
            maxLines: 1,
          ),
        ),
      ],
    ),
  );
}

void _showAddProfessionDialog(BuildContext context) {
  final TextEditingController professionNameController =
      TextEditingController();
  final TextEditingController photoController =
      TextEditingController(); // For photo URL

  showDialog(
    context: context,
    builder: (BuildContext context) {
      return AlertDialog(
        title: const Text("Add New Profession"),
        content: SingleChildScrollView(
          child: Column(
            children: [
              _buildTextField("Profession Name", professionNameController),
              _buildTextField("Photo URL", photoController), // For photo input
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.of(context).pop(); // Close dialog
            },
            child: const Text("Cancel"),
          ),
          TextButton(
            onPressed: () async {
              final newProfession = {
                "name": professionNameController.text,
                "photo": photoController.text,
              };

              await _addProfession(newProfession);
              Navigator.of(context).pop(); // Close dialog
            },
            child: const Text("Add"),
          ),
        ],
      );
    },
  );
}

Widget _buildTextField(String label, TextEditingController controller) {
  return Padding(
    padding: const EdgeInsets.symmetric(vertical: 8.0),
    child: TextField(
      controller: controller,
      decoration: InputDecoration(
        labelText: label,
        border: const OutlineInputBorder(),
      ),
    ),
  );
}

Future<void> _addProfession(Map<String, dynamic> newProfession) async {
  // Replace with your API call to send the data to the backend
  final url =
      Uri.parse('http://192.168.245.168:8085/admin/profession/addprofession');
  final response = await http.post(
    url,
    headers: {'Content-Type': 'application/json'},
    body: json.encode(newProfession),
  );

  if (response.statusCode == 200) {
    // Success
    print("Profession added successfully");
  } else {
    // Failure
    print("Failed to add profession: ${response.statusCode}");
  }
}
