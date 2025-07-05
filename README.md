# 📅 Event Management System

A web-based Event Management System built using Java Servlets (Jakarta EE), jQuery, AJAX, MySQL, and HTML/CSS. This CRUD-based application allows users to **add**, **view**, **update**, and **delete** event records with real-time interaction and dynamic UI updates.

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
