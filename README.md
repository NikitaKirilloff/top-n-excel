# top-n-excel
API для обработки файлов формата .xlsx. Реализована функция поиска N-го максимального числа в файле Excel.

## Технологии

- Java 21

- Spring Boot 3

- Apache POI (для работы с XLSX)

- OpenAPI (Swagger)

## Функциональность
- Чтение числовых данных из `.xlsx`
- Поиск N-го максимального числа

# Запуск проекта
### 1. Склонировать репозиторий
git clone https://github.com/NikitaKirilloff/top-n-excel.git

cd top-n-excel
### 2. Собрать проект
mvn clean package
### 3. Запуск приложения
mvn spring-boot:run

#### После запуска приложения API будет доступен в Swagger по адресу:
http://localhost:8080/swagger-ui/index.html


