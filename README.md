# 📦 Gestion de Stock - JavaFX

A desktop inventory and stock management application built with JavaFX and a MySQL database, designed with a focus on data security and architectural integrity.

## 🎯 Features

- ✅ **Controlled CRUD Operations:** Secure, full management (Create, Read, Update, Delete) of products and categories through the UI.
- ✅ Dynamic keyword-based search.
- ✅ Real-time low stock alerts (Ensuring Data Availability).
- ✅ Dashboard with live visual statistics and pie charts.
- ✅ Automated report exporting to PDF format.
- ✅ Built using a robust **MVC + DAO** architecture.

## 🛡️ Security & Data Integrity (Application Security)

While the user interface allows seamless data modification, addition, and deletion for authorized operations, the backend infrastructure is fully hardened against malicious exploits:

- **SQL Injection Defenses:** Implemented system-wide parameterized queries via **`PreparedStatement`**. Any user input from the UI text fields is treated strictly as a string literal data value, never as executable code. This completely neutralizes *SQL Injection (SQLi)* vectors.
- **Structural Integrity:** The strict separation of concerns using the **DAO (Data Access Object) Pattern** guarantees that the MySQL database (`magasin_bd`) only interacts with structured, legitimate transactions, preventing direct or unauthorized table tampering.
- **Operational Availability:** The automated minimum threshold flag (*Stock Bas*) acts as a defensive monitoring control, ensuring the availability of critical asset metrics before inventory levels are disrupted.

## 🛠️ Technologies & Skills

- **Languages:** Java, SQL
- **UI Framework:** JavaFX 25
- **Database & Driver:** MySQL / JDBC (via Secure `PreparedStatements`)
- **Reporting Tool:** iText (PDF)

## 📁 Project Structure

```plain
src/
├── app/        → Application entry point (MainApp)
├── controller/ → Business logic and input control flow
├── dao/        → Secure data access layer (SQL mapping & parameterization)
├── model/      → Domain models (Object definitions & Singleton connection)
└── view/       → Graphical user interfaces (FXML layouts)


🚀 Setup & Launch
1- Import the magasin_bd.sql script into your local MySQL instance.

2- Configure your database credentials inside model/SingletonConnection.java (username/password).

3- Run app/MainApp.java ensuring the JavaFX VM arguments are properly configured.

👤 Author
Ayoub Khachane
