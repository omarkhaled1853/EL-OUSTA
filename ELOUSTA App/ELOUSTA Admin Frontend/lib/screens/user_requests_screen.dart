import 'package:flutter/material.dart';
import '../models/client_request.dart';
import '../services/client_service.dart';
import '../widgets/client_card.dart';
import 'add_admin_screen.dart';

class UserRequestsScreen extends StatefulWidget {
  const UserRequestsScreen({Key? key}) : super(key: key);

  @override
  _UserRequestsScreenState createState() => _UserRequestsScreenState();
}

class _UserRequestsScreenState extends State<UserRequestsScreen> {
  final ClientService _clientService = ClientService();
  List<ClientRequest> _clients = [];
  List<ClientRequest> _filteredClients = [];
  bool _isLoading = true;
  String _searchQuery = '';

  @override
  void initState() {
    super.initState();
    _loadClients();
  }

  Future<void> _loadClients() async {
    try {
      final clients = await _clientService.getClientRequests();
      setState(() {
        _clients = clients;
        _filteredClients = clients;
        _isLoading = false;
      });
    } catch (e) {
      setState(() => _isLoading = false);
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(e.toString()), backgroundColor: Colors.red),
        );
      }
    }
  }

  void _filterClients(String query) {
    setState(() {
      _searchQuery = query;
      _filteredClients = _clients
          .where((client) =>
          client.username.toLowerCase().contains(query.toLowerCase()))
          .toList();
    });
  }

  Future<void> _deleteClient(int id) async {
    try {
      final success = await _clientService.deleteClient(id);
      if (success) {
        await _loadClients();
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Client deleted successfully')),
          );
        }
      } else {
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('You do not have permission to delete clients'),
              backgroundColor: Colors.red,
            ),
          );
        }
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(e.toString()), backgroundColor: Colors.red),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('User Requests'),
        actions: [
          IconButton(
            icon: const Icon(Icons.add),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => const AddAdminScreen()),
              );
            },
          ),
        ],
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              decoration: const InputDecoration(
                labelText: 'Search by username',
                prefixIcon: Icon(Icons.search),
                border: OutlineInputBorder(),
              ),
              onChanged: _filterClients,
            ),
          ),
          Expanded(
            child: _isLoading
                ? const Center(child: CircularProgressIndicator())
                : _filteredClients.isEmpty
                ? const Center(child: Text('No clients found'))
                : ListView.builder(
              itemCount: _filteredClients.length,
              itemBuilder: (context, index) {
                return ClientCard(
                  client: _filteredClients[index],
                  onDelete: _deleteClient,
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}