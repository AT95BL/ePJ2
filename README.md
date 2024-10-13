---

# ePJ2: E-Mobility Rental System  

> **Elektrotehnički fakultet, Banja Luka**  
> **Programski jezici 2** - Projektni zadatak  
> **Maj 2024**

ePJ2 is an e-mobility rental system designed to manage the rental and simulation of electric cars, bicycles, and scooters in the city of Java. This project simulates real-world rentals using predefined data and generates financial reports, statistics, and status tracking for all available vehicles. 

## 📋 Project Overview  

The system manages three types of vehicles:  
- **Electric Cars**  
- **Electric Bicycles**  
- **Electric Scooters**

Each vehicle type stores specific data such as ID, purchase date, battery level, and more. Rentals are logged with the user’s data, start and end locations, and rental duration, along with generated invoices in text format. The project also simulates vehicle movements in real-time on a map.

---

## 🛠 Features  

### 1. **Vehicle Management**  
- Track all vehicles with individual attributes:
  - **Cars**: Purchase date, battery level, passenger capacity, etc.  
  - **Bicycles**: Battery range, autonomy, etc.  
  - **Scooters**: Maximum speed, etc.  
- Log breakdowns with failure descriptions and timestamps.  
- Recharge vehicles and manage battery usage during rides.

### 2. **Rental and Invoicing System**  
- Log rentals with user information, vehicle type, and locations.  
- Different rates for narrow and wide parts of the city.  
- Track special factors influencing rental cost:
  - **Breakdowns**: Rental cost = 0 in case of breakdowns.  
  - **Discounts**: Every 10th rental gets a discount.  
  - **Promotions**: Apply promotional discounts.
- Generate invoices as `.txt` files containing itemized rental details.  

### 3. **Real-time Simulation on Map**  
- Simulate vehicle movement across a 20x20 grid representing the city.  
- Each cell is marked as either **wide area** (white) or **narrow area** (blue).  
- Display vehicle ID and battery level during movement.  
- Execute rentals in chronological order using separate threads for each.  
- Pause for 5 seconds after completing all rentals for a given time.

### 4. **Financial Reports**  
- Generate **Daily** and **Summary Reports**:
  - Total revenue and discounts applied.
  - Promotional revenue and repair costs.
  - Costs of operation and tax calculations.  
- **Summary Report** shows:
  - **Total Maintenance Cost**: 20% of revenue.
  - **Repair Costs**: Calculated based on vehicle type (0.07 for cars, 0.04 for bicycles, and 0.02 for scooters).  

---

## 🎛 GUI Interfaces  

The program includes multiple graphical interfaces implemented in **JavaFX** or **Swing**:
1. **Main Map View**: Displays the city grid and vehicle movements.
2. **Vehicle Overview**: Tabular view with all vehicle information.
3. **Breakdown Log**: Table with breakdown details (vehicle type, ID, time, and description).  
4. **Business Results**: Displays income, expenses, and other business metrics.  

---

## 📁 Data Management  

- **Properties Files**: All rental rates, discounts, and other constants are stored in external `properties` files ([Learn more](https://www.baeldung.com/java-properties)).
- **Binary Serialization**: Additional functionality (based on student ID) stores and retrieves data about:
  1. Vehicles generating the most revenue.
  2. Vehicles causing the most loss.
  3. Vehicles with breakdowns and their repair costs.
- **Test Data**: Provided through the Moodle platform. Rental data is loaded sequentially and processed in real-time.

---

## 📊 Additional Functionality  

The specific feature to implement depends on the sum of the digits of your student index (e.g., 1234/24 => 1 + 2 + 3 + 4 + 2 + 4 = 16, `(16 % 3) + 1 = 2`):
1. **Revenue Leaders**: Identify the vehicles with the highest revenue for each type.
2. **Loss Leaders**: Identify the vehicles causing the most financial loss.
3. **Breakdown Repairs**: List vehicles with breakdowns and their repair costs.

Serialized data for each category must be saved as binary files, and a GUI option must be provided to deserialize and display this information.

---

## 🚀 How to Run the Project  

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/epj2-rental-system.git
   cd epj2-rental-system
   ```
2. **Setup Properties File**:  
   Ensure the `properties` file with rates and discounts is configured properly.

3. **Compile and Run the Program**:
   ```bash
   javac -d bin src/*.java
   java -cp bin Main
   ```

4. **GUI Navigation**: Use the menu to switch between the map view, vehicle tables, breakdown logs, and business reports.

---

## 📑 Project Structure  

```
epj2-rental-system/
│
├── src/                     # Source code
│   ├── vehicles/            # Classes for Cars, Bikes, and Scooters
│   ├── rental/              # Rental-related logic
│   ├── reports/             # Report generation logic
│   ├── gui/                 # JavaFX/Swing GUI interfaces
│   └── Main.java            # Entry point of the application
│
├── properties/              # Configuration files (rental rates, discounts)
├── invoices/                # Generated invoices (TXT format)
└── README.md                # Project documentation
```

---

## 💡 Notes and Best Practices  

- Use **JavaDoc** comments throughout the code and generate documentation.  
- Follow **coding best practices**: use appropriate naming conventions, avoid code duplication, and ensure performance.  
- Implement modular code using **packages** for better organization.

---

## 🛡 License  

This project is created as part of the **Programski jezici 2** course at the **Elektrotehnički fakultet, Banja Luka**. 

---

## 👥 Contributors  

- **Andrej Trožić 1196/20** - Student at Elektrotehnički fakultet, Banja Luka

---

Feel free to customize this template according to your needs or to match any additional requirements.
