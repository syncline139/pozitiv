![Pozitiv Logo](src/main/resources/static/images/icon/oblozhka_pozitiv.png)

Добро пожаловать в проект "Pozitiv"! Это веб-приложение на основе Spring Boot, предназначенное для управления заказами на технику и оборудование. Проект включает интернет-магазин, личный кабинет, админ-панель и панель основателя с аутентификацией через Spring Security.

## Функционал
- **Интернет-магазин**: Просмотр и заказ товаров (требуется регистрация).
- **Личный кабинет**: Управление профилем, смена пароля, просмотр заказов, выход из аккаунта.
- **Админ-панель**: Добавление товаров и управление статусами заказов.
- **Панель основателя**: Назначение и удаление администраторов.
- **Дизайн**: Адаптивный интерфейс с фиолетовой темой и золотыми акцентами.

## Технологии
- **Backend**: Java 21, Spring Boot, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, Bootstrap Icons, Animate.css
- **База данных**: PostgreSQL
- **Сборка**: Maven

## Требования
- **JDK**: 21 или выше
- **Maven**: 3.9.9 или выше
- **PostgreSQL**: 12 или выше
- **Git**: Для клонирования репозитория

## Установка и запуск
Следуйте этим шагам, чтобы настроить и запустить проект на своей машине:

### 1. Клонирование репозитория
Откройте терминал и выполните команду:
```bash
git clone https://github.com/0-Luntik-0/pozitiv.git
cd ВАША ПАПКА
```

### 2. Настройка PostgreSQL
1. Установите PostgreSQL, если он ещё не установлен, и запустите сервер.
2. Создайте базу данных:
   ```sql
   CREATE DATABASE pozitiv_db;
   ```
4. Переименуйте файл `src/main/resources/application.properties.origin` в `application.properties` и укажите свои данные для подключения к PostgreSQL:
   ```properties
   spring.application.name=positiv
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.datasource.url=jdbc:postgresql://localhost:5432/pozitiv_db
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD

   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.show-sql=true



### 3. Сборка и запуск приложения
1. Убедитесь, что вы находитесь в директории проекта:
   ```bash
   cd ВАША ПАПКА
   ```
2. Соберите проект и установите зависимости:
   ```bash
   mvn clean install
   ```
3. Запустите приложение:
   ```bash
   mvn spring-boot:run
   ```

### 4. Доступ к приложению
- Откройте браузер и перейдите на `http://localhost:8080`.

## Дополнительные заметки
- **Роли**: После регистрации пользователь получает роль `USER`. Для доступа к `/admin` или `/owner` вручную добавьте роли `ADMIN` или `OWNER` в таблицу `users` через SQL:
  ```sql
  UPDATE users SET role = 'ADMIN' WHERE login = 'ВАШ ЛОГИН';
  ```
- **Spring Security**: Настроена базовая аутентификация через `SecurityConfig.java`.
