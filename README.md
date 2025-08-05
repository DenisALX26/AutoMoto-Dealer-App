# DealerApp - JavaFX Vehicle Dealership Management System

DealerApp is a full-stack desktop application built in JavaFX for managing a vehicle dealership. The app supports operations like viewing available vehicles, filtering based on attributes, placing orders, and assigning sales employees. The app is connected to an Oracle database and features a clean UI built using JavaFX Scene Builder.

---

## 📅 Project Status

This project is **fully functional** and actively maintained as a personal portfolio project.

---

## 🔧 Features

- ✅ View all available vehicles (cars and motorcycles)
- 🔍 Filter vehicles by engine type, year, or price
- ✉️ Place customer orders for vehicles
- 💼 Assign employees to orders
- 🔄 Real-time removal of sold vehicles via SQL trigger
- ⚖️ Dynamic JavaFX UI (Scene Builder)
- ✏️ Read-only vehicle details populated from DB
- 🏆 Use of OOP (Vehicle superclass, Car & Motorcycle subclasses)
- ⚖️ Modular structure (MVC design pattern)

---

## 📄 Tech Stack

**Frontend:**
- JavaFX
- Scene Builder (FXML-based layout)

**Backend:**
- Java 11
- JDBC (Oracle Database)

**Database:**
- Oracle 21c XE
- SQL (tables, views, triggers, procedures)

**Build Tool:**
- Maven

**IDE:**
- VS Code

---

## 🔹 Project Structure

```
src/
├── main/
│   ├── java/com/dealerapp/
│   │   ├── controller/          # JavaFX controllers
│   │   ├── db/                 # Database manager (JDBC logic)
│   │   ├── model/              # POJO classes (Vehicle, Car, Motorcycle, etc.)
│   │   └── App.java            # Main launcher
│   └── resources/fxml/         # FXML layouts
│       └── MainView.fxml

tables/
├── Vehicle/
│   ├── create.sql
│   └── insert.sql
├── Car/
│   └── create.sql
├── Motorcycle/
│   └── create.sql
├── Customer/
│   ├── create.sql
│   └── insert.sql
├── Employee/
│   ├── create.sql
│   └── insert.sql
├── Order/
│   ├── create.sql
│   └── insert.sql
├── Order_Details/
│   ├── create.sql
│   └── insert.sql
```

---

## 🤸‍♂️ How It Works

- `Vehicle`, `Car`, and `Motorcycle` are modeled using inheritance in Java.
- Vehicles are stored in the `Vehicle` table, with `Car` and `Motorcycle` holding subtype-specific data.
- A SQL `TRIGGER` automatically removes a vehicle from stock when it's ordered.
- JavaFX TableView displays unified info for all vehicles.
- Data is pulled from Oracle DB using JDBC and displayed dynamically.

---

## 🚀 Getting Started

### Requirements:

- JDK 11+
- Maven
- Oracle XE 21c
- Scene Builder (optional but recommended)

### Setup:

1. Clone repo: `git clone https://github.com/your-username/DealerApp.git`
2. Open the project in VS Code
3. Set up Oracle DB:
   - Run schema.sql to create tables
   - Configure connection in `DatabaseManager.java`
4. Run `App.java` (or use `mvn clean javafx:run`)

---

## 📊 Database Design

Tables:

- `Vehicle`, `Car`, `Motorcycle`
- `Employee`, `Customer`
- `Order`, `Order_Details`

Special Logic:

- Trigger to auto-remove sold vehicles from inventory
- Enums and constraints for engine types, order status, etc.

---

## 🌟 Why This Project

This project was developed as a **personal portfolio app** to demonstrate:

- JavaFX desktop UI design
- Oracle SQL usage with triggers and constraints
- Clean architecture (MVC, OOP, FXML separation)
- Real-world dealership use case

---

## 🙌 Author

**Denis Neacșa**  
Student @ Faculty of Mathematics and Computer Science, University of Bucharest