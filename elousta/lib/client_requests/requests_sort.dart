import 'package:flutter/material.dart';

class RequestsSort extends StatefulWidget {
  String sortType;
  final Function() applySort;
  RequestsSort({super.key, required this.sortType, required this.applySort});

  @override
  State<RequestsSort> createState() => _RequestsSortState();
}

class _RequestsSortState extends State<RequestsSort> {
  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
      value: widget.sortType,
      items: const [
        DropdownMenuItem(value: "none", child: Text("No Sort")),
        DropdownMenuItem(value: "startDate", child: Text("Sort by Start Date")),
        DropdownMenuItem(value: "endDate", child: Text("Sort by End Date")),
      ],
      onChanged: (value) {
        if (value != null) {
          setState(() {
            widget.sortType = value;
          });
          widget.applySort();
        }
      },
    );
  }
}
