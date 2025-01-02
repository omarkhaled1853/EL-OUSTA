import 'package:flutter/material.dart';
import 'package:el_ousta/client_requests/api_service.dart';
import 'package:el_ousta/client_requests/request_class.dart';

class RequestsController extends ChangeNotifier {
  final ApiService apiService;
  final int userId;

  late TabController tabController;

  Future<List<Request>>? currentRequests;

  List<Request> searchResults = []; // For search results
  bool isSearching = false; // Track if search is applied

  RequestsController({required this.apiService, required this.userId});

  void initializeController(TickerProvider vsync) {
    tabController = TabController(length: 3, vsync: vsync);
    tabController.addListener(_handleTabChange);

    // Load initial data
    _fetchRequests(0);
  }

  void _handleTabChange() {
    if (tabController.indexIsChanging) return;
    _fetchRequests(tabController.index);
  }

  void _fetchRequests(int tabIndex) {
    switch (tabIndex) {
      case 0:
        currentRequests = apiService.fetchPendingRequests(userId);
        break;
      case 1:
        currentRequests = apiService.fetchInProgressRequests(userId);
        break;
      case 2:
        currentRequests = apiService.fetchCompletedRequests(userId);
        break;
    }
    notifyListeners();
  }

  void searchRequests(String query) {
    String state = "";
    switch (tabController.index) {
      case 0:
        state = "PENDING";
        break;
      case 1:
        state = "IN-PROGRESS";
        break;
      case 2:
        state = "COMPLETED";
        break;
    }
    isSearching = true;
    apiService.searchRequests(userId, state, query).then((requests) {
      searchResults = requests; // Update search results
      notifyListeners();
    });
  }

  // Reset search results when tab is changed
  void resetSearch() {
    isSearching = false;
    searchResults = [];
    _fetchRequests(tabController.index);
    notifyListeners();
  }

  @override
  void dispose() {
    tabController.dispose();
    super.dispose();
  }
}
