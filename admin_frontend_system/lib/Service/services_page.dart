import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

class ServicesPage extends StatefulWidget {
  const ServicesPage({Key? key}) : super(key: key);

  @override
  _ServicesPageState createState() => _ServicesPageState();
}

class _ServicesPageState extends State<ServicesPage> {
  late Future<List<Map<String, String>>> services;
  late Future<AdminDashBoardDTO> dashboardData;

  @override
  void initState() {
    super.initState();
    services = fetchServices();
    dashboardData = fetchDashboardData();
  }

  // Fetch the dashboard data from the backend
  Future<AdminDashBoardDTO> fetchDashboardData() async {
    const String url = "http://192.168.245.168:8085/admin/home/dash_board";

    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        return AdminDashBoardDTO.fromJson(data);
      } else {
        throw Exception("Failed to load dashboard data");
      }
    } catch (e) {
      throw Exception("Error fetching dashboard data: $e");
    }
  }

  // Fetch services
  Future<List<Map<String, String>>> fetchServices() async {
    const String state = "Done"; // Replace with the desired state
    const String url =
        "http://192.168.245.168:8085/admin/home/Done_Requests/$state";
    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map<Map<String, String>>((item) {
          return {
            "techName": item["techName"] ?? "",
            "location": item["location"] ?? "",
            "date": item["date"] ?? "",
            "serviceName": item["description"] ?? ""
          };
        }).toList();
      } else {
        throw Exception("Failed to load services");
      }
    } catch (e) {
      throw Exception("Error fetching data: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color.fromARGB(255, 1, 125, 187),
        title: const Text(
          'EL Osta',
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
      ),
      body: Column(
        children: [
          // Dashboard section
          FutureBuilder<AdminDashBoardDTO>(
            future: dashboardData,
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const Padding(
                  padding: EdgeInsets.all(16.0),
                  child: Center(child: CircularProgressIndicator()),
                );
              } else if (snapshot.hasError) {
                return Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Center(
                    child: Text("Error: ${snapshot.error}"),
                  ),
                );
              } else if (snapshot.hasData) {
                final data = snapshot.data!;
                return Padding(
                  padding: EdgeInsets.symmetric(
                      vertical: screenHeight * 0.02,
                      horizontal: screenWidth * 0.05),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      buildCircularCard(
                        label: "Clients",
                        value: data.numofclients.toString(),
                      ),
                      buildCircularCard(
                        label: "Technicians",
                        value: data.numoftechs.toString(),
                      ),
                      buildCircularCard(
                        label: "Complaints",
                        value: data.numofcomplains.toString(),
                      ),
                      buildCircularCard(
                        label: "Requests",
                        value: data.numofrequests.toString(),
                      ),
                    ],
                  ),
                );
              } else {
                return const Padding(
                  padding: EdgeInsets.all(16.0),
                  child: Center(child: Text("No dashboard data available.")),
                );
              }
            },
          ),
          const Divider(height: 20, color: Colors.black),
          // Services section
          Expanded(
            child: FutureBuilder<List<Map<String, String>>>(
              future: services,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(child: CircularProgressIndicator());
                } else if (snapshot.hasError) {
                  return Center(child: Text("Error: ${snapshot.error}"));
                } else if (snapshot.hasData) {
                  final servicesData = snapshot.data!;
                  if (servicesData.isEmpty) {
                    return const Center(child: Text("No services available."));
                  }
                  return ListView.builder(
                    padding: EdgeInsets.symmetric(
                      vertical: screenHeight * 0.02,
                    ),
                    itemCount: servicesData.length,
                    itemBuilder: (context, index) {
                      final service = servicesData[index];
                      return Padding(
                        padding: EdgeInsets.symmetric(
                          horizontal: screenWidth * 0.04,
                          vertical: screenHeight * 0.01,
                        ),
                        child: Card(
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(15),
                          ),
                          color: const Color.fromARGB(255, 1, 125, 187),
                          child: Padding(
                            padding: EdgeInsets.all(screenWidth * 0.04),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              children: [
                                buildServiceInfo(
                                    'Tech Name', service['techName']!),
                                buildServiceInfo(
                                    'Location', service['location']!),
                                buildServiceInfo('Date', service['date']!),
                                buildServiceInfo(
                                    'Service', service['serviceName']!),
                              ],
                            ),
                          ),
                        ),
                      );
                    },
                  );
                } else {
                  return const Center(child: Text("No data available."));
                }
              },
            ),
          ),
        ],
      ),
    );
  }

  Widget buildCircularCard({
    required String label,
    required String value,
  }) {
    return MouseRegion(
      cursor: SystemMouseCursors.click,
      child: AnimatedContainer(
        duration: const Duration(milliseconds: 300),
        height: 90,
        width: 90,
        decoration: BoxDecoration(
          color: const Color.fromARGB(255, 1, 125, 187),
          shape: BoxShape.circle,
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.2),
              spreadRadius: 2,
              blurRadius: 5,
              offset: const Offset(0, 3),
            ),
          ],
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              value,
              style: const TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 17),
            ),
            const SizedBox(height: 5),
            Text(
              label,
              style: const TextStyle(
                color: Colors.white,
                fontSize: 14,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildServiceInfo(String label, String value) {
    return Column(
      children: [
        Text(
          label,
          style:
              const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 5),
        Text(
          value,
          style: const TextStyle(color: Colors.white),
          overflow: TextOverflow.ellipsis,
          maxLines: 1,
        ),
      ],
    );
  }
}

// Data model for AdminDashBoardDTO
class AdminDashBoardDTO {
  final int numofclients;
  final int numoftechs;
  final int numofcomplains;
  final int numofrequests;

  AdminDashBoardDTO({
    required this.numofclients,
    required this.numoftechs,
    required this.numofcomplains,
    required this.numofrequests,
  });

  factory AdminDashBoardDTO.fromJson(Map<String, dynamic> json) {
    return AdminDashBoardDTO(
      numofclients: json['numofclients'] as int,
      numoftechs: json['numoftechs'] as int,
      numofcomplains: json['numofcomplains'] as int,
      numofrequests: json['numofrequests'] as int,
    );
  }
}
