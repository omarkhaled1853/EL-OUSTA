import 'dart:convert';
import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/main.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:el_ousta/models/Notification.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import '../old files/homeclient.dart';
import '../screens/userTechScreen.dart';
import 'package:el_ousta/common/userTech.dart';
// void main() {
//   runApp(MyApp());
// }
//
// class MyApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       title: 'Notification App',
//       theme: ThemeData(
//         primarySwatch: Colors.blue,
//       ),
//       home: NotificationScreen(),
//     );
//   }
// }

class NotificationScreen extends StatefulWidget implements PreferredSizeWidget {
  @override
  _NotificationScreenState createState() => _NotificationScreenState();
  final dynamic type;
  final bool addBackButton;

  const NotificationScreen({super.key, required this.type, this.addBackButton = true});
  @override
  // TODO: implement preferredSize
  Size get preferredSize =>
      const Size.fromHeight(kToolbarHeight); // Standard AppBar height
}

class _NotificationScreenState extends State<NotificationScreen> {
  late StompClient stompClient;
  bool isConnected = false;
  int notificationCount = 0;
  final secureStorage = const FlutterSecureStorage();
  int? userId = 0; // Change to the actual user ID
  // final String token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzM0NDI3MTI1LCJleHAiOjE3MzQ1MTM1MjV9.ys0dqqEwTETu4igdTWLvAds9-AnGEdPzl8Lev0vGt50'; // Replace with your Bearer token
  String? token = "";
  List<NotificationObject> notifications = [];

  @override
  void initState() {
    super.initState();
    initializeData(); // Initialize the token and notifications
  }

  Future<void> initializeData() async {
    token = await secureStorage.read(
        key: 'auth_token'); // Await the asynchronous call
    print(token);
    String? stringID = await secureStorage.read(key: 'id');
    userId = int.parse(stringID!);
    initializeWebSocket(); // Initialize WebSocket
    fetchNotifications(); // Fetch notifications only after token is retrieved
  }

  @override
  void dispose() {
    stompClient.deactivate();
    super.dispose();
  }

  // Fetch notifications from backend
  Future<void> fetchNotifications() async {
    final String url;
    if (widget.type == Type.USER) {
      url = '${ServerAPI.baseURL}/client/notifications/$userId';
    } else {
      url = '${ServerAPI.baseURL}/tech/notifications/$userId';
    }

    final response = await http.get(
      Uri.parse(url),
      headers: {
        'Authorization':
            'Bearer $token', // Add Bearer token to Authorization header
      },
    );

    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      setState(() {
        notifications =
            data.map((item) => NotificationObject.fromJson(item)).toList();
      });
    } else {
      throw Exception('Failed to load notifications');
    }
  }

  // Initialize WebSocket for real-time notifications
  void initializeWebSocket() {
    stompClient = StompClient(
      config: StompConfig(
        url: '${ServerAPI.baseURLSocket}/elousta-websocket',
        onConnect: (frame) {
          setState(() {
            isConnected = true;
          });
          print('Connected: $frame');

          // Subscribe to the notifications topic for the specific user
          stompClient.subscribe(
            destination: (widget.type == Type.USER)
                ? '/subscribe/client/$userId'
                : '/subscribe/tech/$userId',
            callback: (frame) {
              if (frame.body != null) {
                setState(() {
                  // Parse the incoming notification and add to the list
                  notifications.add(
                      NotificationObject.fromJson(json.decode(frame.body!)));
                  notificationCount++;
                });
              }
            },
          );
        },
        onWebSocketError: (error) {
          print('WebSocket error: $error');
        },
        onStompError: (frame) {
          print('STOMP error: ${frame.headers['message']}');
        },
      ),
    );

    stompClient.activate();
  }

  // Reset the notification count when the user views notifications
  void resetNotificationCount() {
    setState(() {
      notificationCount = 0;
    });
  }

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: const Text('El Ousta'),
      automaticallyImplyLeading: widget.addBackButton,
      backgroundColor: Colors.deepPurple,
      actions: [
        IconButton(
          icon: const Icon(
            Icons.logout,
            color: Colors.black,
          ),
          onPressed: () async {
            await storage.delete(key: 'auth_token');
            Navigator.of(context).pushReplacement(
                MaterialPageRoute(builder: (ctx) => const UserTechScreen()));
          },
        ),
        PopupMenuButton<String>(
          icon: Stack(
            children: [
              const Icon(
                Icons.notifications_none,
                color: Colors.black,
              ),
              if (notificationCount > 0)
                Positioned(
                  right: 0,
                  top: 0,
                  child: Container(
                    padding: const EdgeInsets.all(2),
                    decoration: const BoxDecoration(
                      color: Colors.red,
                      shape: BoxShape.circle,
                    ),
                    child: Text(
                      '$notificationCount',
                      style: const TextStyle(color: Colors.white, fontSize: 12),
                    ),
                  ),
                ),
            ],
          ),
          onSelected: (String value) {
            // Handle selection if necessary
          },
          onCanceled: () {
            resetNotificationCount();
          },
          itemBuilder: (BuildContext context) {
            return [
              PopupMenuItem<String>(
                child: Container(
                  padding: const EdgeInsets.all(10),
                  width: 300,
                  height: 300,
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(8),
                    boxShadow: [
                      BoxShadow(
                        color: Colors.grey.withOpacity(0.2),
                        spreadRadius: 1,
                        blurRadius: 4,
                        offset: const Offset(0, 3), // changes position of shadow
                      ),
                    ],
                  ),
                  child: notifications.isNotEmpty
                      ? ListView.builder(
                          itemCount: notifications.length,
                          itemBuilder: (context, index) {
                            final notification = notifications[index];
                            return Container(
                              margin: const EdgeInsets.only(bottom: 10),
                              decoration: BoxDecoration(
                                color: Colors.lightBlue[50],
                                borderRadius: BorderRadius.circular(8),
                              ),
                              child: ListTile(
                                contentPadding: const EdgeInsets.symmetric(
                                  horizontal: 15,
                                  vertical: 10,
                                ),
                                title: Text(
                                  notification.message,
                                  style: const TextStyle(
                                    fontWeight: FontWeight.bold,
                                    fontSize: 14,
                                  ),
                                ),
                                subtitle: Text(
                                  '${notification.date}',
                                  style: const TextStyle(fontSize: 12),
                                ),
                                tileColor: Colors.white,
                              ),
                            );
                          },
                        )
                      : const Center(child: Text('No notifications available.')),
                ),
              ),
            ];
          },
        ),
      ],
      // body: Center(child: CircularProgressIndicator()),
    );
  }
}
