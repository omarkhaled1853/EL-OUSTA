import 'package:flutter/material.dart';

class RequestButton extends StatelessWidget {
  final String text;
  final Color color;
  final IconData icon;
  final Function() onPressed;
  const RequestButton(
      {super.key,
      required this.text,
      required this.color,
      required this.icon,
      required this.onPressed});

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: onPressed,
      style: ElevatedButton.styleFrom(backgroundColor: color),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(icon),
          const SizedBox(width: 4),
          Text(text),
        ],
      ),
    );
  }
}
