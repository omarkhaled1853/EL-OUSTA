import 'package:flutter/material.dart';
import '../services/storage_service.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final _storageService = StorageService();

  @override
  void initState() {
    super.initState();
    _printStoredCredentials();
  }

  Future<void> _printStoredCredentials() async {
    final token = await _storageService.readData('token');
    final userId = await _storageService.readData('userId');
    print('Stored Token: $token');
    print('Stored User ID: $userId');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('EL-OUSTA Admin'),
      ),
      body: const Center(
        child: Text('Welcome to Home Screen'),
      ),
    );
  }
}