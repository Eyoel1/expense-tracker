🇪🇹 BirrWise (ብር-ዋይዝ)

The Context-Aware Expense Tracker for Ethiopia


















🌟 Project Overview

BirrWise is a full-stack financial management application tailored for Ethiopia. It provides context-aware personal finance tracking by integrating:

Edir / Idir social contributions

Dual-language support (English & Amharic አማርኛ)

Visual analytics dashboards for income, expenses, and budgets

The project demonstrates professional full-stack development using:

Android Native frontend (Java + XML)

Spring Boot backend with REST API

MySQL database for secure persistence

🛠 Tech Stack
Layer	Technology
Frontend	Android Native (Java & XML), MPAndroidChart, RecyclerView, Retrofit2
Backend	Java 17+, Spring Boot 3.x, Spring Data JPA, Spring Web
Database	MySQL (XAMPP/WAMP)
Build Tools	Gradle, Maven
Version Control	Git + GitHub
📂 Project Structure
BirrWise_Complete_Project/
├─ Backend/
│   ├─ src/main/java/com/example/birrwise/
│   │   ├─ controller/    # API endpoints
│   │   ├─ model/         # Entity classes
│   │   ├─ repository/    # JPA Repositories
│   │   └─ service/       # Business logic (optional)
│   └─ src/main/resources/
│       └─ application.properties
├─ Android_App/
│   ├─ app/src/main/java/
│   │   └─ com.example.birrwise/
│   │       ├─ activities/
│   │       ├─ adapters/
│   │       └─ models/
│   └─ app/src/main/res/
│       ├─ layout/
│       ├─ values/
│       └─ drawable/
└─ README.md

🚀 How to Run
Prerequisites

Android Studio (latest)

IntelliJ IDEA or VS Code

XAMPP (for MySQL)

Git installed

1. Database Setup

Start Apache & MySQL from XAMPP.

Open http://localhost/phpmyadmin.

Create a new database: birrwise_db.

No tables required—Spring Boot will auto-create them.

2. Run the Backend

Open the /Backend folder in IntelliJ.

Ensure Maven dependencies download.

Edit application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/birrwise_db
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update


Run BirrwiseApplication.java.

Wait for: Tomcat started on port 8080.

3. Run the Android App

Open /Android_App in Android Studio.

Let Gradle sync finish.

Start your Emulator (Pixel 6+ recommended) or a real device.

Run the app → Register a new account → Start tracking expenses.

🧪 Test Credentials

Register: Any new user.

Login: Use the credentials you created.

Setup: Set salary in Profile (e.g., 20,000 Br) to see net balance calculations.

💡 Architecture Overview
[ Android App ] <--Retrofit2--> [ Spring Boot REST API ] <--JPA--> [ MySQL Database ]


Android App: UI, charts, local validation

Spring Boot: Business logic, REST endpoints, salary/expense calculations

MySQL: Stores Users, Expenses, Salaries, Edir payment records

🔧 Deployment Tips

Backend can run standalone on any machine with JDK + MySQL.

Frontend requires Android Studio Emulator or a real device.

For production, consider deploying Spring Boot to Heroku/AWS/GCP and Android App via Google Play Store.

🤝 Contribution

Contributions are welcome via GitHub pull requests.

Coding conventions:

Java: CamelCase classes, lowerCamelCase variables

XML: Consistent indentation and naming

Make sure to update README if adding new features.

👨‍💻 Authors

GROUP MEMBERS:                               ID                                         
 
1. Yosef desta                                      UU92933R
2. Eyoel Goshu                                    UU92968R
3. Kidus fikadu                                    UU93607R
4.Dagmawit Defgefu                         U92975R
5. Tsega Kagnew                                UU94194R 


MAD Course

Focused on full-stack integration, cultural localization, and polished UI/UX

📜 License

This project is licensed under MIT License — free to use, modify, and redistribute.

This README is now:

Badge-rich (professional visual appeal)

Complete full-stack documentation

Step-by-step setup instructions

Architecture & contribution guidelines

GitHub ready
