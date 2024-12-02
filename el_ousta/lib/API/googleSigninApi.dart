import 'package:google_sign_in/google_sign_in.dart';

class GoogleSignInApi {
  static final _googleSignIn = GoogleSignIn();
  static Future<GoogleSignInAccount?> login() => _googleSignIn.signIn();

  static Future<void> signOutFromGoogle() async {
    try {
      await _googleSignIn.signOut();
      print("User signed out successfully");
    } catch (error) {
      print("Error signing out: $error");
    }
  }
}