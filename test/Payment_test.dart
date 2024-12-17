import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';
import 'dart:convert';
import 'package:test_app/Payment.dart'; // Adjust import path
import 'package:test_app/API/serverAPI.dart';
import 'package:mockito/mockito.dart';

// Mock class for HTTP Client
class MockHttpClient extends Mock implements http.Client {}

void main() {
  late MockClient mockClient;

  setUp(() {
    // Mock client for simulating HTTP requests
    mockClient = MockClient((http.Request request) async {
      if (request.url.toString() == '${ServerAPI.baseURL}/client/home/') {
        // Handle successful and failed payment cases
        final body = jsonDecode(request.body);
        if (body['paymentMethod'] == 'Credit Card' &&
            body['cardNumber'] == '4111111111111111' &&
            body['expiryDate'] == '12/34' &&
            body['cvv'] == '123') {
          return http.Response('{"status": "success"}', 200);
        }
        return http.Response('{"status": "error"}', 400);
      }
      return http.Response('Not Found', 404);
    });
  });
  group('PaymentPage Widget Tests', () {
    testWidgets('switches payment method on dropdown change', (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: PaymentPage()));

      // Verify Credit Card fields are visible
      expect(find.text('Card Number'), findsOneWidget);

      // Tap on the dropdown to select PayPal
      await tester.tap(find.byType(DropdownButton<String>));
      await tester.pumpAndSettle(); // Wait for the dropdown animation to complete
      await tester.tap(find.text('PayPal').last); // Select PayPal
      await tester.pumpAndSettle(); // Wait for the UI to rebuild

      // Verify PayPal field is now visible
      expect(find.text('PayPal Email'), findsOneWidget);
    });

    testWidgets('switches payment method on dropdown change', (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: PaymentPage()));

      // Initially, Credit Card fields are shown
      expect(find.text('Card Number'), findsOneWidget);
      expect(find.text('Expiry Date (MM/YY)'), findsOneWidget);
      expect(find.text('CVV'), findsOneWidget);

      // Change the dropdown to PayPal
      await tester.tap(find.byType(DropdownButton<String>));
      await tester.pumpAndSettle();
      await tester.tap(find.text('PayPal').last); // Tap on PayPal
      await tester.pumpAndSettle();

      // Verify that PayPal field is shown
      expect(find.text('PayPal Email'), findsOneWidget);
    });

    testWidgets('Validates and submits payment for Credit Card successfully', (WidgetTester tester) async {
      // Pump the PaymentPage with the mock client
      await tester.pumpWidget(MaterialApp(
        home: PaymentPage(httpClient: mockClient), // Injecting MockClient
      ));

      // Enter Credit Card details
      await tester.enterText(find.byType(TextField).at(0), '4111111111111111'); // Card Number
      await tester.enterText(find.byType(TextField).at(1), '12/34'); // Expiry Date
      await tester.enterText(find.byType(TextField).at(2), '123'); // CVV

      // Tap the submit button (shows confirmation dialog)
      await tester.tap(find.text('Submit Payment'));
      await tester.pumpAndSettle(); // Wait for the dialog to appear

      // Assert: Verify confirmation dialog is shown
      expect(find.text('Confirm Submission'), findsOneWidget);
      expect(find.text('Are you sure you want to submit your payment details?'), findsOneWidget);

      // Tap the "Confirm" button to proceed
      await tester.tap(find.text('Confirm'));
      await tester.pump(); // Wait for dialog to dismiss and UI to start rebuilding

      // Wait for the loading indicator to appear
      await tester.pump(); // Trigger state rebuild after `isSubmitting = true`
      expect(find.byType(CircularProgressIndicator), findsOneWidget);

      // Wait for the async operation to complete
      await tester.pumpAndSettle(); // Wait for HTTP request and UI rebuild

      // Assert: Verify success message
      expect(find.text('Payment processed successfully!'), findsOneWidget);
    });




    testWidgets('Displays error for failed payment submission', (WidgetTester tester) async {
      // Pump the PaymentPage with the mock client
      await tester.pumpWidget(MaterialApp(
        home: PaymentPage(httpClient: mockClient), // Injecting MockClient
      ));

      // Enter Credit Card details with invalid data
      await tester.enterText(find.byType(TextField).at(0), '4111111111111111'); // Card Number
      await tester.enterText(find.byType(TextField).at(1), '12/34'); // Expiry Date
      await tester.enterText(find.byType(TextField).at(2), '456'); // Invalid CVV (forcing an error)

      // Tap the submit button (shows confirmation dialog)
      await tester.tap(find.text('Submit Payment'));
      await tester.pumpAndSettle(); // Wait for the dialog to appear

      // Assert: Verify confirmation dialog is shown
      expect(find.text('Confirm Submission'), findsOneWidget);
      expect(find.text('Are you sure you want to submit your payment details?'), findsOneWidget);

      // Tap the "Confirm" button to proceed
      await tester.tap(find.text('Confirm'));
      await tester.pump(); // Wait for dialog to dismiss and submission to start

      // Assert: Verify the loading indicator is shown
      expect(find.byType(CircularProgressIndicator), findsOneWidget);

      // Wait for async operation to complete
      await tester.pump();

      // Assert: Verify error message
      expect(find.text('Payment failed. Please try again.'), findsOneWidget);
    });

    testWidgets('Does not submit payment when confirmation is canceled', (WidgetTester tester) async {
      // Pump the PaymentPage with the mock client
      await tester.pumpWidget(MaterialApp(
        home: PaymentPage(httpClient: mockClient), // Injecting MockClient
      ));

      // Enter Credit Card details
      await tester.enterText(find.byType(TextField).at(0), '4111111111111111'); // Card Number
      await tester.enterText(find.byType(TextField).at(1), '12/34'); // Expiry Date
      await tester.enterText(find.byType(TextField).at(2), '123'); // CVV

      // Tap the submit button (shows confirmation dialog)
      await tester.tap(find.text('Submit Payment'));
      await tester.pumpAndSettle(); // Wait for the dialog to appear

      // Assert: Verify confirmation dialog is shown
      expect(find.text('Confirm Submission'), findsOneWidget);
      expect(find.text('Are you sure you want to submit your payment details?'), findsOneWidget);

      // Tap the "Cancel" button to dismiss the dialog
      await tester.tap(find.text('Cancel'));
      await tester.pump(); // Wait for dialog to dismiss

      // Assert: Verify the loading indicator is NOT shown
      expect(find.byType(CircularProgressIndicator), findsNothing);

      // Assert: Verify no success or error messages are shown
      expect(find.text('Payment processed successfully!'), findsNothing);
      expect(find.text('Payment failed. Please try again.'), findsNothing);
    });
  });
}
