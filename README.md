# Aluno CRUD Application

This project is a terminal-based CRUD application for managing student records (Alunos). It uses JPA, Hibernate, and a simple command-line menu to perform operations like creating, reading, updating, and deleting students. The application also provides an interactive selection method that helps users choose a student from a list instead of manually entering a UUID.

## Features

- **Create:** Add new students with the following fields:
  - Nome
  - RA
  - Email
  - Nota1, Nota2, Nota3
- **Read:**
  - List all students
  - List only approved students (students with an average grade above a set threshold)
  - Search students by name (supports partial matching)
  - Interactive selection: list students with an index for easy selection
- **Update:** Modify existing student records.
- **Delete:** Remove student records.

## Prerequisites

- Java 17 (or higher)
- Maven or Gradle (depending on your build tool)
- A configured database in `persistence.xml` (default settings often use H2 for in-memory or file-based persistence)

## Installation and Running

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ericksgmes/pw3
   cd pw3
   ```

2. **Build the project:**

   - **Using Maven:**
     ```bash
     mvn clean install
     ```

3. **Run the application:**

   - **Using Maven:**
     ```bash
     mvn exec:java -Dexec.mainClass="Main"
     ```

## Usage

After launching the application, you'll see a terminal menu similar to this:

```
** CADASTRO DE ALUNOS **
1 - Cadastrar aluno
2 - Excluir aluno
3 - Alterar aluno
4 - Buscar aluno pelo nome
5 - Listar alunos aprovados
6 - Listar todos os alunos
7 - Fim
Digite a opção desejada:
```

- **Cadastrar aluno:** Follow the prompts to enter student details.
- **Excluir aluno:** Enter the full UUID of the student you wish to delete.
- **Alterar aluno:** Provide the UUID and update desired fields.
- **Buscar aluno pelo nome:** Enter a full or partial name to search for matching students.
- **Listar alunos aprovados:** Display students who meet the approval criteria.
- **Listar todos os alunos:** Show a complete list of students.
- **Fim:** Exit the application.

## Reducing Hibernate Logs

If you find that Hibernate logs are too verbose in the terminal, you can adjust the logging level:
- **In `persistence.xml`:**
  ```xml
  <property name="hibernate.show_sql" value="false"/>
  <property name="hibernate.format_sql" value="false"/>
  ```
- **Using your logging framework (e.g., Logback or Log4j):**  
  Set the log level for `org.hibernate` to `ERROR` or `WARN`.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests with improvements or bug fixes.
