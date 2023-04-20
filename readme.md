Кінотеатр
____
ОПИС ПРОЕКТУ
____
Завдання фінального проекту - розробити веб-застосунок, що підтримує функціональність відповідно до
варіанту завдання.
____
Вимоги до реалізації:
____
1. На основі сутностей предметної області створити класи, які їм відповідають.
2. Класи і методи повинні мати назви, що відображають їх функціональність, і повинні бути
   рознесені по пакетам.
3. Оформлення коду має відповідати Java Code Convention.
4. Інформацію щодо предметної області зберігати у реляційній базі даних (в якості СУБД
   рекомендується використовувати MySQL або PostgreSQL).
5. Для доступу до даних використовувати JDBC API із застосуванням готового або ж
   розробленого самостійно пулу з'єднань.
___
   НЕ допускається використання ORM фреймворків
____
6. Застосунок має підтримувати роботу з кирилицею (бути багатомовним), в тому числі при
   зберіганні інформації в базі даних:
   a. повинна бути можливість перемикання мови інтерфейсу;
   b. повинна бути підтримка введення, виведення і зберігання інформації (в базі даних),
   записаної на різних мовах;
   c. в якості мов обрати мінімум дві: одна на основі кирилиці (українська або російська),
   інша на основі латиниці (англійська).
7. Архітектура застосунка повинна відповідати шаблону MVC.
___
   НЕ допускається використання MVC-фреймворків
___
8. При реалізації бізнес-логіки необхідно використовувати шаблони проектування: Команда,
   Стратегія, Фабрика, Будівельник, Сінглтон, Фронт-контролер, Спостерігач, Адаптер та ін.
   Використання шаблонів повинно бути обґрунтованим
9. Використовуючи сервлети і JSP, реалізувати функціональність, наведену в постановці
   завдання.
10. Використовувати Apache Tomcat у якості контейнера сервлетів.
11. На сторінках JSP застосовувати теги з бібліотеки JSTL та розроблені власні теги (мінімум: один
    тег custom tag library і один тег tag file).
12. Реалізувати захист від повторної відправки даних на сервер при оновленні сторінки
    (реалізувати PRG).
13. При розробці використовувати сесії, фільтри, слухачі.
14. У застосунку повинні бути реалізовані аутентифікація і авторизація, розмежування прав
    доступу користувачів системи до компонентів програми. Шифрування паролів заохочується.
15. Впровадити у проект журнал подій із використанням бібліотеки log4j.
16. Код повинен містити коментарі документації (всі класи верхнього рівня, нетривіальні методи
    і конструктори).
17. Застосунок має бути покритим модульними тестами (мінімальний відсоток покриття 40%).
    Написання інтеграційних тестів заохочуються.
18. Реалізувати механізм пагінації сторінок з даними.
19. Всі поля введення повинні бути із валідацією даних.
20. Застосунок має коректно реагувати на помилки та виключні ситуації різного роду (кінцевий
    користувач не повинен бачити stack trace на стороні клієнта).
21. Самостійне розширення постановки задачі по функціональності заохочується! (додавання
    капчі, формування звітів у різних форматах, тощо)
22. Використання HTML, CSS, JS фреймворків для інтерфейсу користувача (Bootstrap, Materialize,
    ін.) заохочується!
___
Деталі проекту
___
Система інтернет каси кінотеатру з однією залою. В системі має бути розклад фіомів на тиждень з 9:00 до 22:00 (початок останнього сеансу).
* незареєстрований користувач може бачити розклад, вільні місця в залі та має можливість зареєструватися
* користувач повинен мати можливість сортувати записи розкладу за назвою фільму, кількістю доступних місць, датою/часом сеансу (за замовчуванням) і фільтрувати розклад за фільмами, доступними для перегляду.
* зареєстрованний користувач повинен мати можливість придбати кіиток на обраний сеанс
* адміністратор може додати у розклад новий фільм, скасувати фільм, переглянути відвідуваність залу.
___
Встановлення
___
* Java Oracle OpenJDK Version 11.0.16
* MySQL Workbench 8.0.31 
  * user=root
  * password=1234
* Apache Tomcat/9.0.73
* Адміністратор:
  * ім'я користувача: Admin
  * пароль: Admin
  ____
  Project Description
___
The task of the final project is to develop a web application that supports the functionality according to the given assignment.
___

Requirements for implementation:
___
1. Create classes based on the entities of the subject area that correspond to them.
2. Classes and methods should have names that reflect their functionality and should be distributed across packages.
3. Code formatting should comply with Java Code Convention.
4. Store information about the subject area in a relational database (MySQL or PostgreSQL is recommended as the DBMS).
5. Use JDBC API to access data using a ready-made or self-developed connection pool.
6. The use of ORM frameworks is not allowed.
7. The application should support Cyrillic (multilingual) including when storing information in a database:
   a. there should be the ability to switch the language of the interface;
   b. there should be support for input, output, and storage of information (in the database) written in different languages;
   c. choose at least two languages: one based on Cyrillic (Ukrainian or Russian), and the other based on Latin (English).
8. The application architecture should comply with the MVC pattern.
9. The use of MVC frameworks is not allowed.
10. Business logic should be implemented using design patterns such as Command, Strategy, Factory, Builder, Singleton, Front Controller, Observer, Adapter, etc.
11. The use of patterns should be justified.
12. Using servlets and JSP, implement the functionality provided in the assignment.
13. Use Apache Tomcat as a servlet container.
14. Apply tags from the JSTL library and develop custom tags (minimum: one custom tag library tag and one tag file) on JSP pages.
15. Implement protection against resubmitting data to the server when updating the page (PRG implementation).
16. Use sessions, filters, and listeners when developing.
17. The application should implement authentication and authorization, separation of access rights of system users to program components. Password encryption is encouraged.
18. The application must implement authentication and authorization, separating user access rights to program components. Password encryption is encouraged.
19. Implement event logging using the log4j library in the project.
20. The code must contain documentation comments (for all top-level classes, non-trivial methods, and constructors).
21. The application must have modular tests (minimum coverage percentage 40%). Writing integration tests is encouraged.
22. Implement a pagination mechanism for pages with data.
23. All input fields must have data validation.
24. The application must respond correctly to errors and various exceptional situations (the end user should not see the stack trace on the client side).
25. Independent expansion of the task specification for functionality is encouraged! (adding captchas, generating reports in various formats, etc.)
26. The use of HTML, CSS, JS frameworks for the user interface (Bootstrap, Materialize, etc.) is encouraged!

___
Cinema
___
The system is an Internet showcase of a cinema with one hall. The system has a schedule of films for the week from 9:00 to 22:00 (start of the last film) hours.
An unregistered user can see the schedule, free seats in the hall and has the opportunity to register.
The user should be able to sort the schedule entries by movie title, number of available seats, session date / time (default), and filter the schedule by the movies available for viewing.
The registered user must be able to purchase a ticket for the selected session.
The administrator can schedule a new movie, cancel the movie, view the attendance of the hall.

___
Installation
___
* Java Oracle OpenJDK Version 11.0.16
* MySQL Workbench 8.0.31
    * user=root
    * password=1234
* Apache Tomcat/9.0.73
* Administrator:
  * user name: Admin
  * password: Admin
___