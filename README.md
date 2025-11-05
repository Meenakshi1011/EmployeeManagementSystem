👨‍💼 Employee Management System

A Spring Boot–based backend project designed to efficiently manage employees, departments, salaries, and leaves with secure role-based authentication.

⚙️ Features

Role-Based Access Control:

Admin: Can manage employees, departments, and salaries.

Employee: Can log in and view personal details, salary, and leave status.

User Authentication:

Uses Basic Authentication with email and password.

Passwords are encrypted using BCrypt for security.

Automated Email Notifications:

When an admin creates a new employee, the system automatically sends the employee their login credentials via SMTP email.

Clean Project Architecture:

Well-structured with Controller, Service, Repository, DTO, and Model layers.

Database:

Uses MySQL for persistent data storage and Hibernate for ORM.

🛠️ Tech Stack

Backend: Spring Boot, Hibernate

Database: MySQL

Security: Spring Security (Basic Auth), BCrypt

Email Service: SMTP (JavaMailSender)

This project demonstrates robust backend design, modular architecture, and secure user management with automated communication workflows.
