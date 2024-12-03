import 'package:flutter/material.dart';

class FilteredDropdownScreen extends StatefulWidget {
  const FilteredDropdownScreen({super.key});

  @override
  _FilteredDropdownScreenState createState() => _FilteredDropdownScreenState();
}

class _FilteredDropdownScreenState extends State<FilteredDropdownScreen> {
  final List<String> _allOptions = [
    'Cairo',
    'Alexandria',
    'Giza',
    'Luxor',
    'Aswan',
    'Hurghada',
    'Sharm El-Sheikh',
  ];
  List<String> _filteredOptions = [];
  final TextEditingController _controller = TextEditingController();
  String? _selectedValue;

  @override
  void initState() {
    super.initState();
    _filteredOptions = _allOptions; // Initially show all options
    _controller.addListener(_filterOptions);
  }

  @override
  void dispose() {
    _controller.removeListener(_filterOptions);
    _controller.dispose();
    super.dispose();
  }

  void _filterOptions() {
    setState(() {
      final input = _controller.text.toLowerCase();
      _filteredOptions = _allOptions
          .where((option) => option.toLowerCase().contains(input))
          .toList();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Filtered Dropdown Example')),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextField(
              controller: _controller,
              decoration: const InputDecoration(
                labelText: 'City',
                border: OutlineInputBorder(),
                prefixIcon: Icon(Icons.location_city),
              ),
            ),
            const SizedBox(height: 16.0),
            if (_filteredOptions.isNotEmpty)
              Material(
                elevation: 4.0,
                child: ListView.builder(
                  shrinkWrap: true,
                  itemCount: _filteredOptions.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(_filteredOptions[index]),
                      onTap: () {
                        setState(() {
                          _selectedValue = _filteredOptions[index];
                          _controller.text = _selectedValue!;
                          _filteredOptions = _allOptions; // Reset options
                        });
                      },
                    );
                  },
                ),
              ),
            if (_filteredOptions.isEmpty)
              const Text('No options match your input.'),
          ],
        ),
      ),
    );
  }
}
