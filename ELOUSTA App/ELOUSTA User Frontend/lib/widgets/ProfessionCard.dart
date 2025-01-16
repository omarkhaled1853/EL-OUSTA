import 'package:flutter/material.dart';
import 'package:el_ousta/models/TechCard.dart';
import 'package:el_ousta/widgets/RequestForm.dart';
import 'package:el_ousta/widgets/FilterSection.dart';

class ProfessionCard extends StatelessWidget {
  final TechCard techCard;
  final String token;
  const ProfessionCard(
      {super.key, required this.techCard, required this.token});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        showGeneralDialog(
          context: context,
          barrierDismissible: true,
          barrierLabel: "Technician Details",
          transitionDuration: const Duration(milliseconds: 500),
          pageBuilder: (context, animation, secondaryAnimation) {
            return Center(
              child: Material(
                color: Colors.transparent,
                child: Container(
                  width: MediaQuery.of(context).size.width * 0.8,
                  padding: const EdgeInsets.all(16.0),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(16.0),
                    boxShadow: [
                      BoxShadow(
                        color: Colors.black.withOpacity(0.2),
                        blurRadius: 10.0,
                        spreadRadius: 2.0,
                      ),
                    ],
                  ),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            techCard.techName,
                            style: const TextStyle(
                              fontSize: 24,
                              fontWeight: FontWeight.bold,
                              color: Colors.black87,
                            ),
                          ),
                          IconButton(
                            onPressed: () {
                              Navigator.of(context).pop();
                            },
                            icon:
                                const Icon(Icons.close, color: Colors.black87),
                          ),
                        ],
                      ),
                      const SizedBox(height: 16.0),
                      Row(
                        children: [
                          const Icon(Icons.location_on, color: Colors.blue),
                          const SizedBox(width: 8.0),
                          Text(
                            "Location: ${techCard.location}",
                            style: const TextStyle(fontSize: 16),
                          ),
                        ],
                      ),
                      // const SizedBox(height: 8.0),
                      // Row(
                      //   children: [
                      //     const Icon(Icons.work, color: Colors.green),
                      //     const SizedBox(width: 8.0),
                      //     Text(
                      //       "Experience: ${techCard.experienceYears} years",
                      //       style: const TextStyle(fontSize: 16),
                      //     ),
                      //   ],
                      // ),
                      const SizedBox(height: 8.0),
                      Row(
                        children: [
                          const Icon(Icons.star, color: Colors.orange),
                          const SizedBox(width: 8.0),
                          Text(
                            "Rating: ${techCard.rating}",
                            style: const TextStyle(fontSize: 16),
                          ),
                        ],
                      ),
                      // const SizedBox(height: 16.0),
                      // Align(
                      //   alignment: Alignment.centerRight,
                      //   child: ElevatedButton(
                      //     onPressed: () {
                      //       Navigator.of(context).pop();
                      //     },
                      //     style: ElevatedButton.styleFrom(
                      //       backgroundColor: Colors.blue,
                      //       shape: RoundedRectangleBorder(
                      //         borderRadius: BorderRadius.circular(8.0),
                      //       ),
                      //     ),
                      //     child: const Text(
                      //       "Close",
                      //       style: TextStyle(color: Colors.white),
                      //     ),
                      //   ),
                      // ),
                    ],
                  ),
                ),
              ),
            );
          },
          transitionBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(0.0, -1.0);
            const end = Offset(0.0, 0.0);
            const curve = Curves.easeInOut;

            var tween =
                Tween(begin: begin, end: end).chain(CurveTween(curve: curve));
            var offsetAnimation = animation.drive(tween);

            return SlideTransition(
              position: offsetAnimation,
              child: FadeTransition(
                opacity: animation,
                child: child,
              ),
            );
          },
        );
      },
      child: Container(
        margin: const EdgeInsets.all(4.0),
        decoration: BoxDecoration(
          color: Colors.white,
          border: Border.all(
              color: const Color.fromARGB(255, 1, 32, 76), width: 1.5),
          borderRadius: BorderRadius.circular(12),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              blurRadius: 8,
              spreadRadius: 1,
              offset: const Offset(0, 5),
            ),
          ],
        ),
        child: Column(
          children: [
            // Avatar Section
            Stack(
              alignment: Alignment.center,
              children: [
                Container(
                  padding: const EdgeInsets.all(8),
                  decoration: BoxDecoration(
                    color: Colors.grey[300],
                    shape: BoxShape.circle,
                    boxShadow: [
                      BoxShadow(
                        color: Colors.grey.withOpacity(0.3),
                        blurRadius: 8,
                        spreadRadius: 1,
                        offset: const Offset(0, 5),
                      ),
                    ],
                  ),
                  child: const CircleAvatar(
                    radius: 30,
                    backgroundColor: Colors.white,
                    child: Icon(
                      Icons.person,
                      size: 40,
                      color: Color.fromARGB(255, 8, 5, 5),
                    ),
                  ),
                ),
                Positioned(
                  bottom: 2,
                  right: 2,
                  child: Container(
                    padding: const EdgeInsets.all(2),
                    decoration: BoxDecoration(
                      color: Colors.blueAccent,
                      shape: BoxShape.circle,
                      border: Border.all(color: Colors.white, width: 1),
                    ),
                    child: const Icon(
                      Icons.star,
                      size: 16,
                      color: Colors.white,
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            // Rating Section
            Text(
              "Rating: ${techCard.rating}",
              style: const TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 12,
              ),
            ),
            const SizedBox(height: 4),
            // Name Section
            Text(
              techCard.techName,
              style: const TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 14,
              ),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 4),
            const Spacer(),

            ElevatedButton(
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (context) {
                    // Retrieve the instance of FilterSection's state.
                    final filterSectionState =
                        context.findAncestorStateOfType<FilterSectionState>();

                    // Use the selected profession from the state.
                    final selectedProfession =
                        filterSectionState?.getselectedprofession() ??
                            'Profession';

                    return RequestPopupDialog(
                      professionName:
                          selectedProfession, // Pass the selected profession
                      techName: techCard.techName, // Pass variables directly
                      rating: double.parse(techCard.rating),
                      techid: techCard.techId, // Pass variables directly
                      token: token,
                    );
                  },
                );
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.deepPurple,
                padding: const EdgeInsets.symmetric(horizontal: 10),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(6),
                ),
              ),
              child: const Text(
                "Request",
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.white,
                ),
              ),
            ),
            // Buttons Section
            // Padding(
            //   padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
            //   child: Row(
            //     mainAxisAlignment: MainAxisAlignment.spaceBetween,
            //     children: [
            //       // Request Button
            //       Flexible(
            //         child: ElevatedButton(
            //           onPressed: () {
            //             showDialog(
            //               context: context,
            //               builder: (context) {
            //                 // Retrieve the instance of FilterSection's state.
            //                 final filterSectionState = context
            //                     .findAncestorStateOfType<FilterSectionState>();

            //                 // Use the selected profession from the state.
            //                 final selectedProfession =
            //                     filterSectionState?.getselectedprofession() ??
            //                         'Profession';

            //                 return RequestPopupDialog(
            //                   professionName:
            //                       selectedProfession, // Pass the selected profession
            //                   techName:
            //                       techCard.techName, // Pass variables directly
            //                   rating: double.parse(techCard.rating),
            //                   techid: this
            //                       .techCard
            //                       .techId, // Pass variables directly
            //                   token: this.token,
            //                 );
            //               },
            //             );
            //           },
            //           style: ElevatedButton.styleFrom(
            //             backgroundColor: Colors.deepPurple,
            //             padding: const EdgeInsets.symmetric(horizontal: 8),
            //             shape: RoundedRectangleBorder(
            //               borderRadius: BorderRadius.circular(6),
            //             ),
            //           ),
            //           child: const Text(
            //             "Request",
            //             style: TextStyle(
            //               fontSize: 10,
            //               color: Colors.white,
            //             ),
            //           ),
            //         ),
            //       ),
            // const SizedBox(width: 4),
            // Profile Button
            // Flexible(
            //   child: ElevatedButton(
            //     onPressed: () {
            //     //omar word
            //     // Navigator.push(
            //     //     context,
            //     //     MaterialPageRoute(builder: (context) => const ProfilePage(techCard: this
            //     //             .techCard
            //     //             .techId)),
            //     //   );
            //     },
            //     style: ElevatedButton.styleFrom(
            //       backgroundColor: Colors.deepPurple,
            //       padding: const EdgeInsets.symmetric(horizontal: 8),
            //       shape: RoundedRectangleBorder(
            //         borderRadius: BorderRadius.circular(6),
            //       ),
            //     ),
            //     child: const Text(
            //       "Profile",
            //       style: TextStyle(
            //         fontSize: 10,
            //         color: Colors.white,
            //       ),
            //     ),
            //   ),
            // ),
            // ],
            // ),
            // ),
          ],
        ),
      ),
    );
  }
}
