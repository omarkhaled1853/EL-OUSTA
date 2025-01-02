import 'package:el_ousta/screens/signupScreen.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:el_ousta/common/userTech.dart';
class UserTechScreen extends StatelessWidget {

  const UserTechScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      extendBodyBehindAppBar: true,
      body: Center(
        child: Padding(
            padding: const EdgeInsets.all(15.0),
            child: Column(
              mainAxisSize: MainAxisSize.min, // Minimize vertical spacing
              mainAxisAlignment: MainAxisAlignment.center, // Center widgets inside the Column
              children: [
                const Text("Who Are You...", style: TextStyle(
                  color: Colors.black,
                  fontSize: 32.0,
                  fontWeight: FontWeight.bold,
                ),),
                Card(
                  margin: const EdgeInsets.all(12),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                    // side: const BorderSide(color: Colors.deepPurple, width: 1.0),
                  ),
                  clipBehavior: Clip.hardEdge,
                  elevation: 5,
                  child: InkWell(
                    onTap: () async => {
                      Navigator.of(context).push(MaterialPageRoute(builder: (ctx) => const SignupScreen(type: Type.USER,)))
                    },
                    child: Column(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        const SizedBox(height: 5,),
                        Column(
                          children: [
                            Image.asset('images/user.png', height: 190,),
                            //const SizedBox(height: 100,),
                            SizedBox(
                              height: 35,
                              child: Text(
                                "CLIENT",
                                textAlign: TextAlign.center,
                                softWrap: true,
                                strutStyle: const StrutStyle(
                                    fontSize: 15,
                                    height: 1.0
                                ),
                                style: GoogleFonts.oswald(
                                  fontSize: 20,
                                  fontWeight: FontWeight.w600,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
                const SizedBox(height: 24.0,),
                const Divider(color: Colors.deepPurple,),
                const SizedBox(height: 24.0,),
                Card(
                  margin: const EdgeInsets.all(12),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                    // side: const BorderSide(color: Colors.deepPurple, width: 1.0),
                  ),
                  clipBehavior: Clip.hardEdge,
                  elevation: 5,
                  child: InkWell(
                    onTap: () async => {
                      Navigator.of(context).push(MaterialPageRoute(builder: (ctx) => const SignupScreen(type: Type.TECHNICIAN,)))
                    },
                    child: Column(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        const SizedBox(height: 5,),
                        Column(
                          children: [
                            Image.asset('images/technician.png', height: 190,),
                            //const SizedBox(height: 100,),
                            SizedBox(
                              height: 35,
                              child: Text(
                                "TECHNICIAN",
                                textAlign: TextAlign.center,
                                softWrap: true,
                                strutStyle: const StrutStyle(
                                    fontSize: 15,
                                    height: 1.0
                                ),
                                style: GoogleFonts.oswald(
                                  fontSize: 20,
                                  fontWeight: FontWeight.w600,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
                // const SizedBox(height: 75.0,),
              ],
            ),
        ),
      ),
    );
  }
}
