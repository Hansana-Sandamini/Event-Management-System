# 📅 Event Management System

A web-based Event Management System built using Java Servlets (Jakarta EE), jQuery, AJAX, MySQL, and HTML/CSS. This CRUD-based application allows users to **add**, **view**, **update**, and **delete** event records with real-time interaction and dynamic UI updates.

---

## 🚀 Features

- 🔍 View all events from the database
- ➕ Add new events
- ✏️ Update existing events
- ❌ Delete events
- 📋 Table row selection & form auto-fill
- 🔄 Refresh/reset form
- 🔄 Automatically reloads event list after every change
- 🌐 CORS enabled for cross-origin requests

---

## 🛠️ Technologies Used
- Java Servlet (Jakarta EE)
- MySQL
- JDBC
- Jackson (for JSON parsing)
- HTML5
- CSS3
- jQuery
- AJAX

---

## 🧱 Database Structure

**Database:** `eventdb`  
**Table:** `event`

```sql
CREATE TABLE event (
    eid VARCHAR(10) PRIMARY KEY,
    ename VARCHAR(100),
    edescription TEXT,
    edate VARCHAR(50),
    eplace VARCHAR(100)
);
