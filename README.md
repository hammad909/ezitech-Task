# Attendance Management System

This repository contains the code for an Attendance Management System, designed to streamline attendance tracking for students and provide administrative oversight. The system features separate user and admin panels with distinct functionalities.

## Features

**User Panel:**

*   **Registration and Login:** Secure user authentication.
*   **Mark Attendance:** Allows users to mark their attendance as present once per day. Prevents duplicate entries and deletions.
*   **Mark Leave:** Enables users to submit leave requests to the admin.
*   **View Attendance:** Displays a history of the user's marked attendance.
*   **Profile Picture Edit:** Allows users to upload and change their profile picture.

**Admin Panel:**

*   **Login:** Secure admin authentication.
*   **View Student Records:** Displays a comprehensive list of registered students.
*   **Manage Attendance:** Allows admins to add, edit, and delete student attendance records.
*   **Generate User Reports:** Creates attendance reports for specific users within a given date range.
*   **Leave Approval:** Manages and approves/rejects leave requests submitted by users.
*   **Attendance Summary:** Provides a count of present, absent, and leave days for each student.
*   **Generate System Reports:** Creates comprehensive attendance reports for all users within a specified date range.
*   **Grading System:** Automatically assigns grades based on attendance records (e.g., 26 days = A, 10 days = D). Customizable grade thresholds.

## Technologies Used

*   *(List the technologies used in your project. Examples:)*
    *   Frontend: HTML, CSS, JavaScript, React, Vue.js, etc.
    *   Backend: Python (Django/Flask), Node.js (Express), PHP, etc.
    *   Database: MySQL, PostgreSQL, MongoDB, etc.

## Installation

1.  Clone the repository: `git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git`
2.  Navigate to the project directory: `cd YOUR_REPO_NAME`
3.  *(Provide specific installation instructions based on your project's tech stack. Examples:)*
    *   **Python (Django):**
        *   Create a virtual environment: `python3 -m venv venv`
        *   Activate the environment: `source venv/bin/activate` (Linux/macOS) or `venv\Scripts\activate` (Windows)
        *   Install dependencies: `pip install -r requirements.txt`
        *   Migrate the database: `python manage.py migrate`
        *   Run the development server: `python manage.py runserver`
    *   **Node.js (Express):**
        *   Install dependencies: `npm install`
        *   Start the server: `npm start`
4.  *(Database Setup: Provide instructions on setting up the database.)*

## Usage

*   **User Panel:** Access the user panel by navigating to *(URL/port)* in your browser.
*   **Admin Panel:** Access the admin panel by navigating to *(URL/port/admin or a specific route)* in your browser.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## License

*(Specify the license under which your project is distributed. Example:)*

This project is licensed under the MIT License.

## Screenshots (Optional)

*(Include screenshots of the user and admin panels to visually showcase the application.)*

## Future Enhancements (Optional)

*(List any planned future features or improvements.)*

*   Integration with external authentication providers.
*   Real-time attendance tracking.
*   Mobile app development.


## ScreenShots 

 <img src="https://github.com/user-attachments/assets/ea44cf83-4ccc-4b12-9585-bfb24f3fef30" alt="Attendance Management Screenshot" width="400"/>
 
## Student
 <img src="https://github.com/user-attachments/assets/6d50a598-0ef5-4437-b253-b9981364a8ac" alt="Attendance Management Screenshot" width="400"/>
 
  ## Admin
<img src="https://github.com/user-attachments/assets/39c77ab3-281e-4620-b4d8-e3fe4b2c125f" alt="Attendance Management Screenshot" width="400"/>
