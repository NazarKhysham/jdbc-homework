# jdbc-database-service

Невеличкий навчальний Java-застосунок, який показує, як працювати з базою даних
**H2** через чистий **JDBC**: створити з'єднання, ініціалізувати схему, наповнити
таблиці і виконати SELECT-запити, повертаючи результати у вигляді Java-об'єктів.

Проєкт — це практична робота до теми «Java + SQL», яка готує ґрунт перед
переходом до Hibernate.

---

## Зміст

- [Можливості](#можливості)
- [Стек](#стек)
- [Структура проєкту](#структура-проєкту)
- [Швидкий старт](#швидкий-старт)
- [Як це працює](#як-це-працює)
- [Опис SQL-запитів](#опис-sql-запитів)
- [Приклад виводу](#приклад-виводу)
- [Архітектурні рішення](#архітектурні-рішення)

---

## Можливості

- З'єднання з H2 через JDBC на основі **Singleton**-класу `Database`.
- Ініціалізація схеми з файлу `sql/init_db.sql`.
- Наповнення таблиць з файлу `sql/populate_db.sql`.
- П'ять типових SELECT-запитів, кожен — у власному `.sql`-файлі.
- Усі запити виконуються через `PreparedStatement` (безпечно щодо SQL-ін'єкцій).
- Результати маплять у прості Java-об'єкти (POJO) з `toString()` для зручного друку.

---

## Стек

| Компонент       | Версія              |
|-----------------|---------------------|
| Java            | 11+                 |
| Gradle          | 7+                  |
| H2 Database     | 2.2.224 (embedded)  |
| JUnit Jupiter   | 5.9.1               |

База H2 працює у файловому режимі — дані зберігаються у `./testdb.mv.db`
поряд із проєктом і переживають перезапуски.

---

## Структура проєкту

```
jdbc-database-service/
├── build.gradle                 ← збірка, залежності, плагін application
├── settings.gradle
├── README.md
├── .gitignore
│
├── sql/                         ← усі SQL-запити як окремі файли
│   ├── init_db.sql                 — CREATE TABLE для worker/client/project/…
│   ├── populate_db.sql             — INSERT приклади для кожної таблиці
│   ├── find_max_projects_client.sql
│   ├── find_longest_project.sql
│   ├── find_max_salary_worker.sql
│   ├── find_youngest_eldest_workers.sql
│   └── print_project_prices.sql
│
└── src/main/java/com/goit/jdbc/
    ├── Database.java                  — Singleton, тримає Connection
    ├── DatabaseInitService.java       — main(): виконує init_db.sql
    ├── DatabasePopulateService.java   — main(): виконує populate_db.sql
    ├── DatabaseQueryService.java      — по одному Java-методу на кожний SELECT
    ├── Main.java                      — демо: друкує результати всіх запитів
    │
    ├── MaxProjectCountClient.java     ─┐
    ├── LongestProject.java             │  POJO-моделі,
    ├── MaxSalaryWorker.java            │  у які мапляться рядки з БД
    ├── YoungestEldestWorkers.java      │
    └── ProjectPrices.java             ─┘
```

---

## Швидкий старт

### 1. Клонувати репозиторій

```bash
git clone https://github.com/1neyzer1/jdbc-database-service.git
cd jdbc-database-service
```

### 2. Запустити по черзі три класи

Порядок важливий: спочатку створюємо таблиці, потім наповнюємо, а тоді читаємо.

**З IntelliJ IDEA / іншого IDE:** відкрий проєкт, почекай, поки Gradle
підтягне H2, і по черзі запусти методи `main()` у:

1. `com.goit.jdbc.DatabaseInitService`
2. `com.goit.jdbc.DatabasePopulateService`
3. `com.goit.jdbc.Main` (або власний код, який викликає `DatabaseQueryService`)

**З командного рядка (Gradle):**

```bash
gradle -q run -PmainClass=com.goit.jdbc.DatabaseInitService
gradle -q run -PmainClass=com.goit.jdbc.DatabasePopulateService
gradle -q run -PmainClass=com.goit.jdbc.Main
```

> Якщо хочеш «почати з чистого аркуша» — видали файл `testdb.mv.db`
> у корені проєкту і знову запусти `DatabaseInitService`.

### 3. Використати сервіс у власному коді

```java
DatabaseQueryService queryService = new DatabaseQueryService();

List<MaxProjectCountClient> topClients =
        queryService.findMaxProjectsClient();

topClients.forEach(System.out::println);
```

---

## Як це працює

### `Database` — єдина точка доступу до з'єднання

```java
Connection conn = Database.getInstance().getConnection();
```

- Клас `final`, конструктор `private` — створити ззовні не можна.
- `getInstance()` — `synchronized`, щоб кілька потоків не створили два екземпляри.
- `Connection` зберігається у `final`-полі й віддається тим самим об'єктом
  усім охочим.
- Якщо підключитися не вдалося — летить `RuntimeException` із причиною.

### `DatabaseInitService` і `DatabasePopulateService`

Обидва класи:

1. Читають відповідний `.sql`-файл повністю в рядок.
2. Розбивають його на окремі стейтменти за `;`.
3. Кожен непорожній стейтмент виконують через `PreparedStatement`.

### `DatabaseQueryService`

- Один публічний метод на кожний SELECT-файл.
- Кожен метод:
  1. читає текст запиту з `sql/…`,
  2. готує `PreparedStatement`,
  3. ітерує `ResultSet` і мапить кожний рядок у відповідний POJO,
  4. повертає `List<POJO>`.
- Навіть запити без параметрів використовують `PreparedStatement` —
  щоб додавання параметра в майбутньому не вимагало переробляти код.

---

## Опис SQL-запитів

| Файл                                  | Що повертає                                                   | Метод у `DatabaseQueryService`      | Модель                       |
|---------------------------------------|---------------------------------------------------------------|--------------------------------------|------------------------------|
| `find_max_projects_client.sql`        | клієнт(и) з найбільшою кількістю проєктів                     | `findMaxProjectsClient()`            | `MaxProjectCountClient`      |
| `find_longest_project.sql`            | найдовший(і) проєкт(и) у місяцях                              | `findLongestProject()`               | `LongestProject`             |
| `find_max_salary_worker.sql`          | працівник(и) з максимальною зарплатою                         | `findMaxSalaryWorker()`              | `MaxSalaryWorker`            |
| `find_youngest_eldest_workers.sql`    | наймолодший і найстарший працівники                           | `findYoungestEldestWorkers()`        | `YoungestEldestWorkers`      |
| `print_project_prices.sql`            | ціну кожного проєкту (місяців × сума зарплат команди)         | `printProjectPrices()`               | `ProjectPrices`              |

---

## Приклад виводу

Після запуску `Main` побачиш приблизно таке:

```
=== Max Project Count Clients ===
MaxProjectCountClient{name='LexCorp', projectCount=3}

=== Longest Project(s) ===
LongestProject{name='4', monthCount=17}

=== Max Salary Worker(s) ===
MaxSalaryWorker{name='Tony Stark', salary=10000}

=== Youngest & Eldest Workers ===
YoungestEldestWorkers{type='ELDEST', name='Tony Stark', birthday=1970-05-29}
YoungestEldestWorkers{type='YOUNGEST', name='Barry Allen', birthday=1995-11-25}

=== Project Prices ===
ProjectPrices{name='4', price=249900}
…
```

---

## Архітектурні рішення

- **Singleton для `Database`.** Одне з'єднання на весь застосунок — достатньо
  для навчального прикладу й спрощує код. У справжньому сервісі тут був би
  пул (HikariCP).
- **SQL — у файлах, а не в коді.** Так легше читати, діффити і переписувати
  запити, не перезбираючи Java-код.
- **Тільки `PreparedStatement`.** Навіть для статичних запитів — звичка,
  що рятує від SQL-ін'єкцій, щойно з'являється перший параметр.
- **Окремі POJO на кожний запит.** Модель відображає саме ту форму даних,
  яку повертає конкретний SELECT, без зайвих полів і без прив'язки до
  схеми таблиць.
- **Fail fast.** У разі проблем з БД чи файлами кидається `RuntimeException`
  із зрозумілим повідомленням — помилка не губиться в `printStackTrace`.
