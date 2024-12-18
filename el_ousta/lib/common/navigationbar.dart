import 'package:flutter/material.dart';

class navigationbar extends StatelessWidget {
  const navigationbar({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 8),
      decoration: BoxDecoration(
        color: Colors.grey[200],
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          _buildNavItem('Home'),
          _buildNavItem('professions'),
          _buildNavItem('requests'),
          _buildNavItem('profile'),
        ],
      ),
    );
  }

  Widget _buildNavItem(String text) {
    return Text(
      text,
      style: const TextStyle(
        color: Colors.black54,
        fontSize: 14,
      ),
    );
  }
}