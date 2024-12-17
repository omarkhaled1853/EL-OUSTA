import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class FilterSection extends StatefulWidget {
  const FilterSection({super.key});

  @override
  FilterSectionState createState() => FilterSectionState();
}

class FilterSectionState extends State<FilterSection> {
  String selectedProfession = 'Profession';
  Future<void> saveProfession(String profession) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('selectedProfession', profession);
  }

  Future<void> loadSavedProfession() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      selectedProfession =
          prefs.getString('selectedProfession') ?? 'Profession';
    });
  }

  // Get the selected profession (returning Future<String>)
  String getselectedprofession() {
    return selectedProfession;
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: Row(
        children: [
          const Text(
            'Filter by',
            style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
          const SizedBox(width: 16),
          _buildFilterButton(
            context: context,
            text: selectedProfession,
            options: ['Carpenters', 'Plumbers', 'electrician'],
            onSelected: (value) async {
              setState(() {
                selectedProfession = value;
              });
            },
          ),
          const SizedBox(width: 16),
        ],
      ),
    );
  }

  Widget _buildFilterButton({
    required BuildContext context,
    required String text,
    required List<String> options,
    required void Function(String) onSelected,
  }) {
    return PopupMenuButton<String>(
      onSelected: onSelected,
      itemBuilder: (context) => options
          .map(
            (option) => PopupMenuItem(
              value: option,
              child: Text(option),
            ),
          )
          .toList(),
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
        decoration: BoxDecoration(
          color: const Color.fromARGB(255, 146, 146, 146),
          borderRadius: BorderRadius.circular(4),
        ),
        child: Row(
          children: [
            Text(text, style: const TextStyle(color: Colors.white)),
            const Icon(Icons.arrow_drop_down, color: Colors.white),
          ],
        ),
      ),
    );
  }
}
