# Hall Booking Management System
## CT038-3-2 Object Oriented Using Java

---

## How to Compile and Run

### Windows (IntelliJ IDEA - Recommended)
1. Open IntelliJ IDEA
2. File > Open > select the `HallBookingSystem` folder
3. Right-click `src/Main.java` > Run 'Main'

### Windows (Command Prompt)
1. Open Command Prompt in the project root folder
2. Run: `compile_and_run.bat`

### Mac / Linux (Terminal)
1. Open Terminal in the project root folder
2. Run: `chmod +x compile_and_run.sh && ./compile_and_run.sh`

### Manual Compile (any OS)
```
javac -d out -sourcepath src src/Main.java src/model/*.java src/util/*.java src/gui/*.java
cd out
java Main
```

---

## Default Login Accounts

| Role          | Username  | Password    |
|---------------|-----------|-------------|
| Administrator | admin     | admin123    |
| Manager       | manager   | manager123  |

> Scheduler accounts are created by the Administrator.
> Customer accounts are registered from the Login screen.

---

## Features by Role

### Scheduler
- Add / Edit / Delete halls
- View and filter halls
- Toggle hall availability
- **Set Hall Availability Schedule** (start date, end date, remarks)
- **Set Hall Maintenance Schedule** (start date, end date, remarks)
- View all bookings

### Customer
- Register a new account
- Login / Logout
- Update profile (username, email, phone, password)
- View available halls
- Book a hall (with payment)
- View booking history — filter by **All / Upcoming / Past**
- Cancel a booking (at least 3 days before event)
- Raise an issue for a booked hall

### Administrator
- Add new Scheduler accounts
- **Edit** Scheduler accounts (email, phone, password)
- Block / Unblock any user
- Delete users
- View all bookings
- Generate booking summary report

### Manager
- View Sales Dashboard (Weekly / Monthly / Yearly)
- View & Manage customer issues
- Assign a Scheduler to handle an issue
- **Change issue status**: In Progress / Done / Closed / Cancelled

---

## OOP Concepts Used

| Concept         | Where Used |
|-----------------|------------|
| Abstraction     | `User` is abstract with abstract method `getDashboardTitle()` |
| Inheritance     | `Customer`, `Scheduler`, `Administrator`, `Manager` all extend `User` |
| Encapsulation   | All model fields are private with public getters/setters |
| Polymorphism    | `getDashboardTitle()` returns different values per subclass |

---

## Data Storage
All data is stored in plain `.txt` files inside the `data/` folder:
- `users.txt` — all user accounts
- `halls.txt` — hall records
- `bookings.txt` — booking records
- `payments.txt` — payment records
- `issues.txt` — customer issues

Format: CSV (comma-separated values), one record per line.
