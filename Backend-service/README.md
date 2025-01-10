# Phần Mềm Quản Lý Khách Sạn
Dự án phần mềm quản lý khách sạn nhằm xây dựng một hệ thống thông tin tích hợp, giúp tự động hóa các quy trình vận hành cơ bản của khách sạn, từ đặt phòng, quản lý phòng đến thanh toán và báo cáo. Mục tiêu chính là tối ưu hóa hiệu quả làm việc của nhân viên, nâng cao trải nghiệm khách hàng và tăng cường khả năng quản lý cho ban điều hành khách sạn.

## Giới thiệu

### Lý do thực hiện
- Nhu cầu số hóa quy trình quản lý khách sạn ngày càng tăng.
- Hạn chế sai sót trong quản lý thủ công.
- Tối ưu hóa chi phí vận hành và nhân sự.
- Đáp ứng xu hướng chuyển đổi số trong ngành khách sạn.

### Vấn đề mà dự án giải quyết
- Thay thế hệ thống quản lý thủ công bằng giấy tờ.
- Giảm thiểu sai sót trong đặt phòng và thanh toán.
- Khắc phục tình trạng chồng chéo đặt phòng.
- Tự động hóa việc tạo báo cáo và thống kê.
- Quản lý thông tin khách hàng hiệu quả và bảo mật.
- Tối ưu hóa quy trình làm việc của nhân viên.

---
## Yêu cầu hệ thống
### Backend
- **Ngôn ngữ**: Java 17.
- **Framework**: Spring Boot.
- **Cơ sở dữ liệu**: PostgreSQL.

### Prerequisite
- **Java**: JDK 17+
- **Maven**: 3.5+
- **IDE**: IntelliJ IDEA

## Technical Stacks
***
* Spring Boot 3.2.5
* Java 17
* Redis
* PostgresSQL
* Kafka
* Docker,Dockercompose
* Lombok
* DevTools
* Maven 3.5+

## Documentation
- API Documentation được xây dựng bằng Swagger.
- Đường dẫn: [http://localhost:8000/swagger-ui.html](http://localhost:8000/swagger-ui.html).

---

## Build application
```bash
    mvn clean package -P dev|test|uat|prod
```
## Run application
***
- Maven statement
```bash
   ./mvnw spring-boot:run   
```
- Jar statement
```bash
   Java -jar target/backend-service.jar   
```
- Docker
```bash
    docker build -t backend-service .
    docker run -d backend-service;lastest backend-service
```
### Package application
```bash
    docker build -t backend-service .
```