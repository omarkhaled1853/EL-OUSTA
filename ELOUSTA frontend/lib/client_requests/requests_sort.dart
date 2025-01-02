import 'package:flutter/material.dart';

class RequestsSort extends StatefulWidget {
  const RequestsSort({super.key});

  @override
  State<RequestsSort> createState() => _RequestsSortState();
}

class _RequestsSortState extends State<RequestsSort> {
  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
      // value: sortType,
      items: const [
        DropdownMenuItem(value: "none", child: Text("No Sort")),
        DropdownMenuItem(value: "startDate", child: Text("Sort by Start Date")),
        DropdownMenuItem(value: "endDate", child: Text("Sort by End Date")),
      ],
      onChanged: (value) {
        if (value != null) {
          setState(() {
            // sortType = value;
          });
          // applySort();
        }
      },
    );
  }
}
