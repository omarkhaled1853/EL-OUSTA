import 'package:flutter/material.dart';

class CustomSearchBar extends StatelessWidget {
  final TextEditingController searchController;
  final FocusNode focusNode;
  final void Function(String) onSearch;
  final String profession;

  const CustomSearchBar({
    super.key,
    required this.profession,
    required this.searchController,
    required this.focusNode,
    required this.onSearch,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: TextField(
        controller: searchController,
        focusNode: focusNode,
        decoration: InputDecoration(
          hintText: 'Search by name of tech',
          filled: true,
          fillColor: const Color.fromARGB(255, 146, 146, 146),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8),
            borderSide: BorderSide.none,
          ),
          contentPadding: const EdgeInsets.symmetric(horizontal: 16),
        ),
        onSubmitted: onSearch,
      ),
    );
  }
}
