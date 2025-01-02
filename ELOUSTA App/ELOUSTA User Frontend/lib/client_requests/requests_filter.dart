import 'package:flutter/material.dart';

class RequestsFilter extends StatefulWidget {
  const RequestsFilter({super.key});

  @override
  State<RequestsFilter> createState() => _RequestsFilterState();
}

class _RequestsFilterState extends State<RequestsFilter> {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: ElevatedButton(
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) {
              // String tempFilterQuery = filterQuery;
              return AlertDialog(
                title: const Text("Filter by Location"),
                content: TextField(
                  decoration:
                      const InputDecoration(labelText: "Enter Location"),
                  onChanged: (value) {
                    // tempFilterQuery = value;
                  },
                ),
                actions: [
                  TextButton(
                    onPressed: () => Navigator.pop(context),
                    child: const Text("Cancel"),
                  ),
                  TextButton(
                    onPressed: () {
                      setState(() {
                        // filterQuery = tempFilterQuery;
                      });
                      // applyFilter();
                      Navigator.pop(context);
                    },
                    child: const Text("Apply"),
                  ),
                ],
              );
            },
          );
        },
        child: const Text("Filter by Location"),
      ),
    );
  }
}
