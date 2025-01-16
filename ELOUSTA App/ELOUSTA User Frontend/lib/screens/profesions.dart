import 'dart:convert';

import 'package:el_ousta/API/serverAPI.dart';
import 'package:el_ousta/Service/gettechcards.dart';
import 'package:el_ousta/Service/search_service.dart';
import 'package:el_ousta/data/professions.dart';
import 'package:flutter/material.dart';
import 'package:el_ousta/widgets/CustomSearchBar.dart';
import 'package:el_ousta/widgets/ProfessionCard.dart';
import 'package:el_ousta/common/navigationbar.dart';
import 'package:el_ousta/common/CustomAppBar.dart';
import 'package:el_ousta/models/TechCard.dart';
import 'package:http/http.dart' as http;
import 'package:el_ousta/common/userTech.dart';
import '../widgets/appBarWithNotification.dart';

List<TechCard>? techcards;
String newprofession = "";

class ProfessionsScreen extends StatefulWidget {
  final String professionType;
  final dynamic token;
  const ProfessionsScreen({super.key, required this.professionType, required this.token});

  @override
  _ProfessionsScreenState createState() => _ProfessionsScreenState();
}

class _ProfessionsScreenState extends State<ProfessionsScreen> {
  bool isLoaded = false;
  final TextEditingController _searchController = TextEditingController();
  final FocusNode _searchFocusNode = FocusNode();
  @override
  void dispose() {
    _searchController.dispose();
    _searchFocusNode.dispose();
    super.dispose();
  }

  void _performSearch(String query) async {
    try {
      List<TechCard>? results = await service_search(query, widget.professionType, widget.token);
      setState(() {
        techcards = results;
      });
    } catch (e) {
      print("Error during search: $e");
    }
  }

  @override
  void initState() {
    super.initState();
    newprofession = widget.professionType;
    fetchTechniciansdefault();
  }

  // Reload the page with the filtered data
  void reloadPageWithFilteredData(List<TechCard> filteredCards) {
    setState(() {
      techcards = filteredCards; // Update the cards list
    });
  }

  List<TechCard>? featched = [];

  /// get cards in initialization
  Future<void> fetchTechniciansdefault() async {
    try {
      featched = await Gettechcards().getcardsinstarting(widget.token, widget.professionType);
      setState(() {
        techcards = featched;
        isLoaded = true; // Mark as loaded after data is fetched
      });
    } catch (e) {
      print("Error fetching technicians: $e");
      setState(() {
        isLoaded = true; // Still mark as loaded to avoid indefinite loading
        techcards = []; // Fallback to an empty list
      });
    }
  }

  ///////////// serach service
  Future<void> searchTechnicians(String searchQuery) async {
    try {
      List<TechCard>? searchResults =
          await service_search(searchQuery, widget.professionType, widget.token);

      setState(() {
        techcards = searchResults; // Update the state with search results
      });
        } catch (e) {
      print("Error during search: $e");
      setState(() {
        techcards = []; // Handle error case
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    if (techcards == null) {
      return const Center(child: Text("No technicians available"));
    }

    return Scaffold(
      appBar: const NotificationScreen(type: Type.USER), // Custom AppBar
      body: Column(
        children: [
          // const Padding(
          //   padding: EdgeInsets.all(16.0),
          //   child: Text(
          //     'Professions',
          //     style: TextStyle(
          //       fontSize: 35,
          //       fontWeight: FontWeight.bold,
          //     ),
          //   ),
          // ),
          FilterSection(
              onFilterApplied: reloadPageWithFilteredData,
              token: widget.token,), // Pass callback
          CustomSearchBar(
            searchController: _searchController,
            focusNode: _searchFocusNode,
            onSearch: _performSearch,
            profession: widget.professionType,
          ),
          Expanded(
            child: GridView.builder(
              padding: const EdgeInsets.all(16),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2, // Two cards per row
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
                childAspectRatio: 0.8, // Adjust aspect ratio of cards
              ),
              itemCount: techcards!.length, // Number of cards
              itemBuilder: (context, index) {
                final techCard = techcards![index];
                return ProfessionCard(techCard: techCard, token: widget.token,);
              },
            ),
          ),
          const navigationbar(),
        ],
      ),
    );
  }
}

class FilterSection extends StatefulWidget {
  final Function(List<TechCard>) onFilterApplied;

  final dynamic token;

  const FilterSection({super.key, required this.onFilterApplied, required this.token});

  @override
  FilterSectionState createState() => FilterSectionState();
}

class FilterSectionState extends State<FilterSection> {
  String selectedProfession = 'Profession';
  Future<List<TechCard>> getCardsWithSpecificProfession(
      String profession) async {
    print("search on $profession");
    List<TechCard> testData = [];
    var client = http.Client();
    var uri = Uri.parse("${ServerAPI.baseURL}/client/home/filtercard");

    try {
      // The body must contain the "field" and "query" parameters for filtering
      var requestBody = jsonEncode({"field": "Domain", "query": profession});

      var response = await client.post(
        uri,
        headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ${widget.token}'},
        body: requestBody,
      );

      if (response.statusCode == 200) {
        print(response.body);
        print("Filter successful. Parsing response...");
        print(techCardFromJson(response.body));
        return techCardFromJson(response.body); // Parse JSON to List<TechCard>
      } else {
        print(
            "Failed to fetch technicians. Status code: ${response.statusCode}");
        print("Response body: ${response.body}");
        return [];
      }
    } catch (e) {
      print("Error during fetching technicians: $e");
      return testData;
    }
  }

  @override
  Widget build(BuildContext context) {
    return const Padding(
      padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: Row(
        children: [
          SizedBox(width: 25),
          // _buildFilterButton(
          //   context: context,
          //   text: selectedProfession,
          //   options: [
          //     'Building',
          //     'Plumbing',
          //     'Electrical'
          //   ], // editing when create admin so admin can add new
          //   onSelected: (value) async {
          //     setState(() {
          //       selectedProfession = value; // Update the selected value
          //       newprofession = value;
          //     });
          //
          //     List<TechCard>? filteredCards = [];
          //     // Fetch filtered cards asynchronously
          //     filteredCards = await _getFilteredCards(value);
          //     if (filteredCards != null) {
          //       widget.onFilterApplied(filteredCards); // Apply the filter
          //     } else
          //       print("not founded");
          //   },
          // ),
        ],
      ),
    );
  }

  Future<List<TechCard>?> _getFilteredCards(String profession) async {
    try {
      return await getCardsWithSpecificProfession(profession, );
    } catch (e) {
      print("Error fetching filtered cards: $e");
      return [];
    }
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
          // color: const Color.fromARGB(255, 146, 146, 146),
          borderRadius: BorderRadius.circular(4),

        ),
        child: Row(
          children: [
            Text(text, style: const TextStyle(color: Colors.black54, fontWeight: FontWeight.bold)),
            const Icon(Icons.arrow_drop_down, color: Colors.black),
          ],
        ),
      ),
    );
  }
}
