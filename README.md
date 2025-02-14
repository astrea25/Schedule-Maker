# 📅 Schedule Maker  

A **full-stack scheduling application** that integrates **Spring Boot** (backend) with **Vue.js** (frontend). The backend establishes a **retrofit connection** between three core services:  
- 🕒 **Timeslot** – Manages available time slots  
- 👤 **User** – Handles user data and authentication  
- 📚 **Subject** – Stores subject/course information  

The **Vue.js frontend** enhances functionality by providing a dynamic and interactive user interface for managing schedules efficiently.  

## 🚀 Features  
✅ **Seamless backend integration** using Spring Boot  
✅ **Interactive scheduling system** with Vue.js  
✅ **RESTful API communication** with Retrofit  
✅ **User-friendly interface** for managing schedules  

## 🏗️ Tech Stack  
- ☕ **Spring Boot** – Backend framework  
- 🔄 **Retrofit** – API communication  
- 🎨 **Vue.js** – Frontend framework  
- 💾 **PostgreSQL/MySQL** – Database  

## 📥 Installation & Setup  

### 🔧 1. Clone the Repository  
```sh
git clone https://github.com/astrea25/Schedule-Maker.git
cd schedule-maker
```
### 🛠 2. Set Up MySQL (XAMPP)

  1. Open XAMPP Control Panel and start MySQL.
  2. Open phpMyAdmin (http://localhost/phpmyadmin/).
  3. Create a new database:
      * Database name: csmaker_db
### 🏗️ 3. Backend (Spring Boot) Setup
📦 Open in Eclipse
  1. Import the following projects into Eclipse as Maven Projects:
     - `ClassScheduleMakerSubject`
     - `ClassScheduleMakerTimeSlot`
     - `ClassScheduleMakerUser`
  2. Modify application.properties in each project to match your MySQL credentials:
     ```sh
     spring.datasource.url=jdbc:mysql://localhost:3306/csmaker_db
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     spring.jpa.hibernate.ddl-auto=update
  ▶️ Run the Backend Services
  Start each Spring Boot application one by one in Eclipse:
  ```sh
  Run ClassScheduleMakerSubject
  Run ClassScheduleMakerTimeSlot  
  Run ClassScheduleMakerUser
  ```
### 🌐 4. Frontend (Vue.js) Setup
📦 Install Dependencies
Navigate to the Vue project folder and install dependencies:
```sh
cd scheduleMaker
npm install
```
▶️ Run the Frontend
```sh
npm run dev
```
### 🔗 5. Access the Application
Once all services are running:
- Frontend (Vue.js): `http://localhost:5173`
- Backend API (Spring Boot Services):
  - Subject Service: `http://localhost:8081/api/subjects`
  - Timeslot Service: `http://localhost:8082/api/timeslots`
  - User Service: `http://localhost:8083/api/users`
