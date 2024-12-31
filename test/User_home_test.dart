import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/testing.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:test_app/screens/Domains.dart';
import 'package:test_app/API/serverAPI.dart';

void main() {
  late MockClient mockClient;

  setUp(() {
    mockClient = MockClient((request) async {
      if (request.url.toString() == '${ServerAPI.baseURL}/client/home/') {
        // Simulate a successful response with a list of domains
        return http.Response(jsonEncode([
          {'id': 1, 'name': 'Domain 1'},
          {'id': 2, 'name': 'Domain 2'},
        ]), 200);
      }
      // Simulate an error response
      return http.Response('Error fetching domains', 500);
    });
  });

  group('DomainPage Widget Tests', () {
    testWidgets('Displays loading indicator while fetching domains', (tester) async {
      // Act
      await tester.pumpWidget(
        MaterialApp(
          home: DomainPage(httpClient: mockClient),
        ),
      );

      // Assert: Check that the loading indicator is shown
      expect(find.byType(CircularProgressIndicator), findsOneWidget);
    });

    testWidgets('Displays a list of domains when fetched successfully', (tester) async {
      // Act
      await tester.pumpWidget(
        MaterialApp(
          home: DomainPage(httpClient: mockClient),
        ),
      );

      // Wait for the async fetch to complete
      await tester.pump();

      // Assert: Verify the domain names are displayed
      expect(find.text('Domain 1'), findsOneWidget);
      expect(find.text('Domain 2'), findsOneWidget);
    });

    testWidgets('Displays error message on fetch failure', (tester) async {
      // Simulate error by overriding the mock client
      mockClient = MockClient((request) async {
        return http.Response('Error fetching domains', 500);
      });

      // Act
      await tester.pumpWidget(
        MaterialApp(
          home: DomainPage(httpClient: mockClient),
        ),
      );

      // Wait for the async fetch to complete
      await tester.pump();

      // Assert: Verify error message is shown in a snackbar
      expect(find.textContaining('Error fetching domains'), findsOneWidget);
    });
  });
}
