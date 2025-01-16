import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/widgets/CheckoutButton.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:el_ousta/common/userTech.dart';
import '../widgets/appBarWithNotification.dart';

const techSecureStorage = FlutterSecureStorage();
late int id;
late String token;
//
// void main() {
//   runApp(RequestsApp());
// }
//
// class RequestsApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       debugShowCheckedModeBanner: false,
//       home: RequestHomePage(),
//     );
//   }
// }

class RequestHomePage extends StatefulWidget {
  const RequestHomePage({super.key});

  @override
  _RequestHomePageState createState() => _RequestHomePageState();
}

class _RequestHomePageState extends State<RequestHomePage> {
  @override
  void initState() {
    super.initState();
    initId();
  }

  Future<void> initId() async {
    String? idString = await techSecureStorage.read(key: 'id');
    id = int.parse(idString!);
    String? stringToken =
        (await techSecureStorage.read(key: 'auth_token')) as String;
    token = stringToken;
    print(token);
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: const NotificationScreen(
          type: Type.TECHNICIAN,
          addBackButton: false,
        ),
        body: Scaffold(
          appBar: TabBar(
            indicatorColor: Colors.white,
            tabs: const [
              Tab(icon: Icon(Icons.pending), text: "Pending"),
              Tab(icon: Icon(Icons.work), text: "In Progress"),
              Tab(icon: Icon(Icons.done), text: "Completed"),
            ],
            onTap: (index) {
              // Call fetchRequests() when a tab is tapped
              final requestList =
                  context.findAncestorStateOfType<_RequestListState>();
              print(requestList);
              if (requestList != null) {
                requestList.fetchRequests();
              }
            },
          ),
          body: TabBarView(
            children: [
              RequestList(
                state: "PENDING",
                isPending: true,
                endpoint: "/tech/requests/get/pending",
                boxColor: Colors.orange[50],
              ),
              RequestList(
                state: "IN-PROGRESS",
                isPending: false,
                endpoint: "/tech/requests/get/inProgress",
                boxColor: Colors.blue[50],
              ),
              RequestList(
                state: "COMPLETED",
                isPending: false,
                endpoint: "/tech/requests/get/completed",
                boxColor: Colors.green[50],
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class RequestList extends StatefulWidget {
  final String state;
  final bool isPending;
  final String endpoint;
  final Color? boxColor;

  const RequestList({super.key, 
    required this.state,
    required this.isPending,
    required this.endpoint,
    required this.boxColor,
  });

  @override
  _RequestListState createState() => _RequestListState();
}

class _RequestListState extends State<RequestList> {
  List<Request> displayedRequests = [];
  String filterQuery = "";
  String searchQuery = "";
  String sortType = "none";
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    initId();
    fetchRequests();
  }

  Future<void> fetchRequests() async {
    setState(() {
      isLoading = true;
    });
    initId();
    // print("HELLO FROM HERE");
    final url = Uri.parse('${ServerAPI.baseURL}${widget.endpoint}/$id');
    final response =
        await http.get(url, headers: {'Authorization': 'Bearer $token'});

    if (response.statusCode == 200) {
      final List<dynamic> jsonResponse = json.decode(response.body);
      setState(() {
        displayedRequests =
            jsonResponse.map((data) => Request.fromJson(data)).toList();
        isLoading = false;
      });
    } else {
      setState(() {
        displayedRequests = [];
        isLoading = false;
      });
    }
  }

  Future<void> applyFilter() async {
    final url = Uri.parse('${ServerAPI.baseURL}/tech/requests/filter');
    final body =
        json.encode({"id": id, "state": widget.state, "query": filterQuery});

    final response = await http.post(url, body: body, headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });

    if (response.statusCode == 200) {
      final List<dynamic> jsonResponse = json.decode(response.body);
      setState(() {
        displayedRequests =
            jsonResponse.map((data) => Request.fromJson(data)).toList();
      });
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('Failed to apply filter for ${widget.state}'),
        backgroundColor: Colors.red,
      ));
    }
  }

  Future<void> applySearch() async {
    final url = Uri.parse('${ServerAPI.baseURL}/tech/requests/search');
    final body =
        json.encode({"id": id, "state": widget.state, "query": searchQuery});

    final response = await http.post(url, body: body, headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });

    if (response.statusCode == 200) {
      final List<dynamic> jsonResponse = json.decode(response.body);
      setState(() {
        displayedRequests =
            jsonResponse.map((data) => Request.fromJson(data)).toList();
      });
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('Failed to search ${widget.state} requests'),
        backgroundColor: Colors.red,
      ));
    }
  }

  Future<void> applySort() async {
    final url = Uri.parse('${ServerAPI.baseURL}/tech/requests/sort');
    final body =
        json.encode({"id": id, "type": sortType, "state": widget.state});

    final response = await http.post(url, body: body, headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });

    if (response.statusCode == 200) {
      final List<dynamic> jsonResponse = json.decode(response.body);
      setState(() {
        displayedRequests =
            jsonResponse.map((data) => Request.fromJson(data)).toList();
      });
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('Failed to sort ${widget.state} requests'),
        backgroundColor: Colors.red,
      ));
    }
  }

  Future<void> refuseTask(Request request) async {
    final url = Uri.parse('${ServerAPI.baseURL}/tech/requests/refuse');
    final body = json.encode({
      "id": request.id,
      "techId": request.techId,
      "clientId": request.clientId,
    });

    final response = await http.post(url, body: body, headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });

    if (response.statusCode == 200) {
      setState(() {
        displayedRequests.remove(request);
      });
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
        content: Text("Task refused successfully"),
        backgroundColor: Colors.red,
      ));
    } else {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
        content: Text("Failed to refuse task"),
        backgroundColor: Colors.red,
      ));
    }
  }

  @override
  Widget build(BuildContext context) {
    return isLoading
        ? const Center(child: CircularProgressIndicator())
        : displayedRequests.isEmpty
            ? const Center(
                child: Text("No Data", style: TextStyle(fontSize: 18)))
            : Column(
                children: [
                  // Search Bar
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: TextField(
                      decoration: InputDecoration(
                        hintText: "Search requests...",
                        prefixIcon: const Icon(Icons.search),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                      onChanged: (value) {
                        searchQuery = value;
                        applySearch();
                      },
                    ),
                  ),

                  // Filter and Sort Buttons
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 8.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        ElevatedButton(
                          onPressed: () {
                            showDialog(
                              context: context,
                              builder: (context) {
                                String tempFilterQuery = filterQuery;
                                return AlertDialog(
                                  title: const Text("Filter by Location"),
                                  content: TextField(
                                    decoration: const InputDecoration(
                                        labelText: "Enter Location"),
                                    onChanged: (value) {
                                      tempFilterQuery = value;
                                    },
                                  ),
                                  actions: [
                                    TextButton(
                                      onPressed: () => Navigator.pop(context),
                                      child: const Text("Cancel"),
                                    ),
                                    TextButton(
                                      onPressed: () {
                                        setState(() {
                                          filterQuery = tempFilterQuery;
                                        });
                                        applyFilter();
                                        Navigator.pop(context);
                                      },
                                      child: const Text("Apply"),
                                    ),
                                  ],
                                );
                              },
                            );
                          },
                          child: const Text("Filter by Location"),
                        ),
                        DropdownButton<String>(
                          value: sortType,
                          items: const [
                            DropdownMenuItem(
                                value: "none", child: Text("No Sort")),
                            DropdownMenuItem(
                                value: "startDate",
                                child: Text("Sort by Start Date")),
                            DropdownMenuItem(
                                value: "endDate",
                                child: Text("Sort by End Date")),
                          ],
                          onChanged: (value) {
                            if (value != null) {
                              setState(() {
                                sortType = value;
                              });
                              applySort();
                            }
                          },
                        ),
                      ],
                    ),
                  ),

                  // Request List
                  Expanded(
                    child: ListView.builder(
                      itemCount: displayedRequests.length,
                      itemBuilder: (context, index) {
                        final request = displayedRequests[index];
                        return Card(
                          color: widget.boxColor,
                          elevation: 4,
                          margin: const EdgeInsets.symmetric(
                              vertical: 8, horizontal: 16),
                          child: Padding(
                            padding: const EdgeInsets.all(16.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  request.description,
                                  style: const TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 16),
                                ),
                                const SizedBox(height: 8),
                                Text('Start: ${request.startDate}'),
                                Text('End: ${request.endDate}'),
                                Text('Location: ${request.location}'),
                                if (widget.isPending)
                                  Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceBetween,
                                    children: [
                                      CheckoutButton(
                                          token: token,
                                          id: request.id,
                                          userId: request.clientId,
                                          techId: request.techId),
                                      ElevatedButton(
                                        onPressed: () => refuseTask(request),
                                        style: ElevatedButton.styleFrom(
                                            backgroundColor: Colors.red),
                                        child: const Text("Refuse Task"),
                                      ),
                                    ],
                                  ),
                              ],
                            ),
                          ),
                        );
                      },
                    ),
                  ),
                ],
              );
  }

  Future<void> initId() async {
    String? idString = await techSecureStorage.read(key: 'id');
    id = int.parse(idString!);
    String? stringToken =
        (await techSecureStorage.read(key: 'auth_token')) as String;
    token = stringToken;
    print(token);
  }
}

class Request {
  final int techId;
  final int clientId;
  final int id;
  final String location;
  final String description;
  final String startDate;
  final String endDate;
  final String state;

  Request({
    required this.techId,
    required this.clientId,
    required this.location,
    required this.id,
    required this.description,
    required this.startDate,
    required this.endDate,
    required this.state,
  });

  factory Request.fromJson(Map<String, dynamic> json) {
    return Request(
      id: json['id'],
      description: json['description'],
      startDate: json['startDate'],
      endDate: json['endDate'],
      techId: json['techId'],
      clientId: json['clientId'],
      state: json['state'],
      location: json['location'],
    );
  }
}
