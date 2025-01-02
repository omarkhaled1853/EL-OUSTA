# ELOUSTA

## Overview

**ELOUSTA** is a platform designed to simplify the connection between clients and technicians. It enables clients to easily request services, communicate with technicians, and manage their requests efficiently. Technicians can manage client requests, accept or decline jobs, and ensure smooth interactions through notifications and feedback mechanisms. The application also includes an admin interface to oversee and manage the platform's operations.

---

## Key Features

### For Clients

- **Sign Up/Login**:
  - Clients can create an account or log in.
  - Google authentication is supported.
- **Forgot Password**:
  - Password recovery using an OTP-based system.
- **Home Page**:
  - Browse various service domains.
  - View technicians in each domain.
  - Add service requests or preview technician details.
- **Requests Management**:
  - Requests are categorized into three sections:
    1. **Pending**:
       - Requests not yet accepted or declined by the technician.
       - Clients can cancel requests in this section.
    2. **In Progress**:
       - Requests accepted by the technician and currently running.
       - Clients can cancel or mark requests as completed.
    3. **Completed**:
       - All requests successfully completed by the client.
       - Clients can:
         - Rate and provide feedback on the technician.
         - Submit complaints against the technician.
- **Notifications**:
  - Technicians are notified when clients:
    - Cancel a request.
    - Submit a rating or feedback.
    - add a complaint.
- **Profile Page**:
  - Clients can view their personal details.
- **Search, Sort, and Filter**:
  - Clients can search by description, sort by start or end date, and filter by location in his/her requests page.

### For Technicians

- **Sign Up/Login**:
  - Technicians can create an account or log in.
  - Google authentication is supported.
- **Requests Management**:
  - Requests are categorized into three sections:
    1. **Pending**:
       - New client requests awaiting action.
       - Technicians can accept or refuse requests.
       - If accepted, a payment form is displayed for the technician.
       - Clients are notified about the acceptance.
    2. **In Progress**:
       - Requests accepted by the technician and currently ongoing.
    3. **Completed**:
       - Requests marked as completed by the client.
       - Feedback and ratings can be viewed.
- **Notifications**:
  - Receive notifications when clients:
    - Cancel a request.
    - Provide feedback or ratings.
    - File complaints.
- **Search, Sort, and Filter**:
  - Technicians can search by description, sort by start or end date, and filter by location in his/her requests page.

### For Admins

- **Request Management**:
  - Review all client requests with statuses: Pending, In Progress, and Completed.
- **Complaints Management**:
  - Review and manage complaints submitted by clients against technicians.
- **User Management**:
  - Remove clients or technicians based on complaint reviews.
- **Dashboard**:
  - View key statistics including:
    - Number of requests made.
    - Number of clients and technicians.
    - Number of complaints submitted.
- **Profession Management**:
  - Add or remove professions (domains).
- **Admin Management**:
  - Add new admin users.
  - Send OTP-based credentials to new admins via email.
  - Assign specific privileges to new admins, such as:
    - Manipulate professions.
    - Access complaints.
    - Access technician and client data.
    - Hire other admins.
  - Only the main admin has all privileges.
- **Search, Sort, and Filter**:
  - Admins can search, sort, or filter requests, complaints, and user data.

---

## Pages Overview

### Client Pages

1. **Home Page**:
   - View service domains and available technicians.
   - Request technician for service or preview technician profiles.
2. **Requests Page**:
   - Manage requests under Pending, In Progress, and Completed sections.
   - Cancel or mark requests as completed for the running requests.
   - Rate, provide feedback, or file complaints for completed requests.
3. **Profile Page**:
   - View personal details.

### Technician Pages

1. **Requests Page**:
   - Manage requests under Pending, In Progress, and Completed sections.
   - Accept or decline requests.
   - Handle payments for accepted requests.
2. **Notifications**:
   - View client actions such as cancellations, feedback, and complaints.

### Admin Pages

1. **Requests Management**:
   - Review all requests with their statuses.
2. **Complaints Management**:
   - Review and manage complaints submitted by clients.
3. **User Management**:
   - Remove clients or technicians based on complaint reviews.
4. **Dashboard**:
   - View statistics and manage professions.
5. **Admin Management**:
   - Add or remove admin users and assign privileges.

---

## Technologies Used

- **Backend**: Spring Boot
- **Frontend**: Flutter
- **Database**: MySQL
- **Authentication**: Spring Authentication with JWT Security
- **Transaction Tokens**: Tokens are used in every transaction performed by clients, technicians, or admins.
- **Notifications**: Implemented using sockets between the backend and all users.
- **Database Queries**: JPA for query implementation.
- **Payments**: Stripe integration.
- **OTP**: Twilio for OTP management.

---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/elousta.git
   ```
2. Navigate to the project directory:
   ```bash
   cd elousta
   ```

### Backend Installation

3. Navigate to the backend directory:
   ```bash
   cd backend
   ```
4. Set up environment variables for Google OAuth, Twilio, Stripe, and other services.
5. Build and run the backend application using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

### Database Installation

6. Create a schema in MySQL with the following details:
   - Schema Name: `elousta`
   - Username: `elousta`
   - Password: `_elousta123`

### Frontend Installation (Client and Technician Applications)

7. Navigate to the Flutter project directory:
   ```bash
   cd ELOUSTA App/ELOUSTA User Frontend
   ```
8. Install Flutter dependencies:
   ```bash
   flutter pub get
   ```
9. Run the Flutter application:
   ```bash
   flutter run
   ```

### Frontend Installation (Admin Application)

10. Navigate to the admin Flutter project directory:
    ```bash
    cd ELOUSTA App/ELOUSTA Admin Frontend
    ```
11. Install Flutter dependencies:
    ```bash
    flutter pub get
    ```
12. Run the Flutter admin application:
    ```bash
    flutter run
    ```

---

## Contributing

We welcome contributions! Please submit a pull request or open an issue for any bugs or feature requests.

---

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details

