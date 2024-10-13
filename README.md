---

# ePJ2: E-Mobility Rental System  

**Elektrotehnički fakultet, Banja Luka**  
**Programski jezici 2 – Projektni zadatak**  
**Maj 2024**

ePJ2 is a rental system for electric cars, bicycles, and scooters designed to simulate the company’s operations in the city of Java. It manages vehicle rentals, monitors vehicle status, and generates financial reports based on predefined data.  

---

## 📋 Project Overview  

The system tracks three types of vehicles:  
- **Electric Cars**  
- **Electric Bicycles**  
- **Electric Scooters**

The following data is managed for each vehicle:  
- **Cars**: ID, purchase date, cost, manufacturer, model, battery level, passenger capacity.  
- **Bicycles**: ID, manufacturer, model, battery level, cost, range per charge.  
- **Scooters**: ID, manufacturer, model, battery level, cost, maximum speed.  

Vehicles may break down, and the system records breakdown details such as date, time, and description. Users can rent vehicles, and the system calculates rental costs, applies discounts, and generates invoices in text format.  

---

## 🛠 Features  

1. **Vehicle Management**  
   - Track battery levels and recharge vehicles.  
   - Simulate battery drain during rides.  
   - Record and manage breakdowns.  

2. **Rental System**  
   - Track rentals with user information, pickup and drop-off locations, and duration.  
   - Apply special conditions for rentals:
     - **Wide/Narrow Area Rates**: Different rates based on location.
     - **Breakdowns**: Rental cost = 0 if a breakdown occurs.
     - **Discounts**: Every 10th rental applies a discount.
     - **Promotions**: Apply special discounts during promotions.  
   - Generate invoices in `.txt` format with detailed itemization.  

3. **Real-Time Simulation**  
   - Simulate vehicle movement on a **20x20 grid map**.  
   - Display vehicle position, ID, and battery level during movement.  
   - Manage simulations with **threads** for each rental.  
   - Execute rentals in **chronological order** with a 5-second pause between batches.

4. **Financial Reports**  
   - Generate **daily** and **summary** reports showing:  
     - Total revenue, discounts, and promotions.  
     - Maintenance and repair costs.  
     - Company expenses and taxes.  

---

## 🎛 Graphical User Interfaces (GUI)  

The program uses **JavaFX** or **Swing** to display:  
- **Main Map View**: Shows vehicle movements in real-time.  
- **Vehicle Overview**: Displays all available vehicles in a tabular format.  
- **Breakdown Log**: Logs all breakdowns with time, type, and description.  
- **Business Results**: Displays financial reports (daily and summary).  

---

## 📁 Data Management  

1. **Configuration Files**  
   - Rental rates, discounts, and promotions are stored in **properties files**.  
   - Example: [Java Properties](https://www.baeldung.com/java-properties)  

2. **Binary Serialization**  
   - The project includes additional data-saving features:
     1. Vehicles with the **highest revenue**.
     2. Vehicles with the **most losses**.
     3. Vehicles with **breakdowns and repair costs**.
   - Serialized data is saved as binary files, and the GUI provides an option to **deserialize and display** them.  

3. **Test Data**  
   - Rental data is provided through the **Moodle** platform.  
   - Rentals are processed sequentially, simulating operations in real-time.

---

## 📊 Reports  

### Summary Report  
The summary report includes the following data:
1. **Total Revenue**: Sum of all payments.  
2. **Total Discounts**: Sum of all applied discounts.  
3. **Total Promotions**: Sum of promotional values.  
4. **Revenue by Area**: Revenue for the narrow and wide city areas.  
5. **Maintenance Cost**: 20% of total revenue.  
6. **Repair Costs**: Based on vehicle type:
   - Cars: 7% of purchase cost.
   - Bicycles: 4% of purchase cost.
   - Scooters: 2% of purchase cost.  
7. **Company Expenses**: 20% of total revenue.  
8. **Taxes**: 10% of the net profit.

### Daily Reports  
The daily reports include the same metrics but grouped by **rental date**.

---

## 🎯 Additional Functionality  

Based on your **student index**, one of the following features must be implemented:  
1. **Highest Revenue Vehicles**: Identify the vehicle with the highest revenue for each type.  
2. **Highest Loss Vehicles**: Identify the vehicle with the most significant losses.  
3. **Breakdown and Repair Costs**: List vehicles with breakdowns and their repair costs.  

---

## 🚀 How to Run  

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/epj2-rental-system.git
   cd epj2-rental-system
   ```

2. **Setup Properties File**:
   Ensure the **properties file** is correctly configured with rental rates, discounts, and promotions.

3. **Compile and Run the Program**:
   ```bash
   javac -d bin src/*.java
   java -cp bin Main
   ```

4. **Use the GUI**: Navigate through the main map, vehicle list, breakdown logs, and financial reports.

---

## 📑 Project Structure  

```
epj2-rental-system/
│
├── src/                     # Source code
│   ├── vehicles/            # Vehicle classes (Cars, Bikes, Scooters)
│   ├── rental/              # Rental processing logic
│   ├── reports/             # Report generation logic
│   ├── gui/                 # JavaFX/Swing interfaces
│   └── Main.java            # Application entry point
│
├── properties/              # Configuration files (rental rates, discounts)
├── invoices/                # Generated invoices (TXT format)
└── README.md                # Project documentation
```

---

## 💡 Best Practices  

- Add **JavaDoc comments** to all classes and methods and generate documentation.  
- Use **packages** for better code organization.  
- Avoid code duplication and ensure efficient performance.  
- Follow proper naming conventions for classes, methods, and variables.

---

## 🛡 License  

This project is developed as part of the **Programski jezici 2** course at the **Elektrotehnički fakultet, Banja Luka**.

---

## 👥 Contributors  

- **Andrej Trožić** – Student at Elektrotehnički fakultet, Banja Luka  

---
