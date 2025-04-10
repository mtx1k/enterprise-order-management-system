
# Enterprise Order Management System

A full-featured internal order processing system for medium-to-large enterprises. Designed to streamline multi-department product ordering, approval workflows, and procurement operations with external suppliers.

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.0-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![DigitalOcean](https://img.shields.io/badge/S3--Compatible-DigitalOcean-orange)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

## 📦 Features

- 🔐 **Role-based access system** with multiple user types:
  - `USER` – creates internal product requests
  - `HEAD` – approves department requests
  - `FINCO` – financial approval for finalized orders
  - `SUPPLIER` – forms and manages supplier orders
  - `ADMIN` – manages users and product catalog
- 📁 **Product catalog import via DigitalOcean Spaces**  
  REST endpoint to import price lists in CSV format from an S3-compatible bucket
- 🛒 **Multi-stage internal order flow**  
  Creation → Department Head Approval → Financial Approval → Supplier Processing
- 📧 **Automated supplier communication**  
  Email delivery of supplier orders with CSV attachments
- 📊 **Supplier order history**  
  Tracks all external supplier orders with filtering and pagination
- 🔄 **Extensible architecture** based on Spring and JPA

## 🧠 Tech Stack

- **Backend:** Java 17, Spring Boot, Spring MVC, Spring Data JPA, Spring Security
- **Database:** MySQL
- **Frontend:** Thymeleaf, Bootstrap
- **Cloud & Email:**
  - AWS SDK (S3) with DigitalOcean Spaces
  - JavaMailSender for email automation
- **Other:** Lombok, Maven, Git, GitHub, REST, HTML/CSS

## ⚙️ Setup Instructions

### 1. Set up environment variables

Create a `.env` file based on `example.env` with your database and S3 credentials:

\`\`\`
DB_URL=jdbc:mysql://localhost:3306/your_database_name
DB_USERNAME=your_mysql_user
DB_PASSWORD=your_mysql_password

DO_SPACES_KEY=your_digitalocean_access_key
DO_SPACES_SECRET=your_digitalocean_secret_key
DO_SPACES_REGION=nyc3
DO_SPACES_BUCKET=your_bucket_name
\`\`\`

### 2. Configure database (MySQL)

- Make sure **MySQL Server** is running locally or remotely.
- Create the database schema manually or allow Hibernate to auto-generate.
- Ensure `.env` contains correct database connection info.

### 3. Build and run the project

Using Maven Wrapper:

\`\`\`bash
./mvnw spring-boot:run
\`\`\`

Or with Maven installed:

\`\`\`bash
mvn spring-boot:run
\`\`\`

The app will be available at: [http://localhost:8080](http://localhost:8080)

## 🔐 User Roles

| Role       | Permissions                                                   |
|------------|---------------------------------------------------------------|
| `USER`     | Create internal orders                                        |
| `HEAD`     | Approve orders from department members                        |
| `FINCO`    | Final financial approval                                      |
| `SUPPLIER` | View approved orders, generate supplier requests, send emails |
| `ADMIN`    | Manage users and trigger product imports                      |

## 📂 Project Structure

\`\`\`
src/
├── main/
│   ├── java/com/example/order/
│   │   ├── config/         # Security & S3 config
│   │   ├── controllers/    # MVC and REST controllers
│   │   ├── models/         # JPA entities
│   │   ├── repositories/   # Spring Data interfaces
│   │   ├── services/       # Business logic
│   │   └── utils/          # Email & parsing helpers
│   └── resources/
│       ├── templates/      # Thymeleaf HTML views
│       └── application.properties
\`\`\`

## 🧑‍💻 Author Contribution

This project was developed by a 3-person team as a final Java course project.  
Key functionality (DigitalOcean integration, product import, Supply Manager flow, email automation) was implemented by [Vitalii Shekhovtsov (mtx1k)](https://github.com/mtx1k).

## 📜 License

This project is licensed under the MIT License.
