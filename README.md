# CS310 Team Project - Clock-In Clock-Out Machine

## Introduction
This repository, "cs310-teamproject-fa22," represents a collaborative effort by a team of Software Engineering I students, including Suneet Sharma (myself), Kennedy Blanks, Cole Massey, Jailon Lawrence, and Chloe Renfroe. The primary objective of this project was to design and develop a clock-in clock-out machine. The machine's purpose was to record employee clock-in and clock-out times and calculate their pay based on the hours worked, using a database system that stored employee information, shift details, pay rates, and timestamps.

## Methodology
### Program Overview
The clock-in clock-out machine is a software application that receives various inputs and produces corresponding outputs. It involves a complex data flow and a variety of methods and variables to handle the process effectively. The main components of the program include:

- **Inputs**:
  - Employee Information: Employee data such as name, employee ID, and position.
  - Shift Details: Information about the work shifts, including start time, end time, and any break times.
  - Pay Rates: Details regarding the pay rate for each employee, which may vary by position or shift.
  - Timestamps: Records of clock-in and clock-out times for each employee.

- **Outputs**:
  - Timestamped Punch Cards: A record of all clock-in and clock-out events, including the employee, timestamp, and shift information.
  - Adjusted Punch Cards: The system may adjust punch cards to account for any discrepancies or missing clock-in/out events.
  - Calculated Pay: The system calculates the total pay for each employee based on the hours worked, accounting for overtime, shift differentials, and other pay rate factors.

### Data Flow
The program's data flow involves the collection of inputs, processing of data, and generation of outputs. The program utilizes a mySQL over database system to store and retrieve employee information, shift data, pay rates, and timestamps. These data are processed using various algorithms to create timestamped punch cards and calculate employee pay.

### Methods and Variables
The project employs several methods and variables to handle tasks like timestamp recording, punch card adjustments, and pay calculations. It also includes error-handling mechanisms to ensure data integrity.

### Calculations
Pay calculations are a significant part of the program, as they involve complex mathematical operations to determine employee pay based on hourly rates, overtime hours, and special considerations for different shifts.

## Results
The clock-in clock-out machine successfully accomplishes its objectives. The results are significant because the program demonstrates how real-life scenarios in software engineering involve teamwork, data management, and complex calculations to meet the needs of a client. The system's ability to accurately record clock-in/out times, adjust punch cards as needed, and calculate employee pay showcases the practical application of software engineering principles.

## Conclusions
This project provided invaluable insights into software development in a team setting. Key takeaways include:

- The importance of effective collaboration and communication within a development team.
- The significance of data management and accuracy when dealing with employee records and pay calculations.
- The complexity of pay calculations, especially when factoring in overtime and shift differentials.
- The need for robust error handling to ensure the integrity of recorded data.

