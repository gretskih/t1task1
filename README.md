# Rest Code Api Service
Cервис выполняет преобразование переданной ссылки в уникальный код, по которому сервис выдает ассоциированную ссылку.
В сервисе можно регистрировать ваши ссылки и в ответ получать ссылки на сервис в виде уникальных кодов.
Затем использовать уникальные коды для получения ссылок.
Сервис предоставляет API для получения статистики работы сервиса.

## Стек технологий
- ![Java 17](https://img.shields.io/badge/Java-17-blue)
- ![Spring 3.2.5](https://img.shields.io/badge/Spring%20Boot%203.2.5-white?style=flat&logo=Spring)
- ![Liquibase 4.24.0](https://img.shields.io/badge/Liquibase_4.24.0-white?style=flat&logo=Liquibase&logoColor=blue
  )
- ![PostgreSQL 42.6.2](https://img.shields.io/badge/PostgreSQL_42.3.8-white?style=flat&logo=PostgreSQL&logoColor=blue
  )
- ![Maven 4.0.0](https://img.shields.io/badge/Maven%204.0.0-white?style=flat&logo=Apache%20Maven&logoColor=red
  )
- ![Lombok 1.18.32](https://img.shields.io/badge/Lombok%201.18.26-white?style=flat
  )

## Окружение
- Java 17
- PostgreSQL 16
- Apache Maven 4.0.0

## Запуск проекта
1. Скачать архив проекта или клонировать.
2. Создать локальную базу данных url_code, используя интерфейс PostgreSQL 16 или команду:

   ```create database url_code```

3. В файл конфигурации application.properties внести логин и пароль пользователя для доступа к базе данных url_code.
4. Для запуска на локальной машине скомпилировать и запустить проект в командной строке

   ```mvn spring-boot:run```

   или после сборки проекта с использованием maven выполнить в командной строке

   ``` java -jar target/aophome-0.0.1-SNAPSHOT.jar```

## Описание CODE API

### GET /swagger-ui/index.html - Swagger.

### GET /v3/api-docs - описание API для Swagger.

### POST /url/code - регистрация ссылки.

структура запроса:
```
{
  "url": "string"
}
```
структура ответа:
```
{
  "url": "string",
  "code": "string"
}
```

### GET /url/{code} - переадресация.
структура ответа:
```
{
  "url": "string",
  "code": "string"
}
```

### GET /url/all - список всех ссылок.
структура ответа:
```
[
  {
    "url": "string",
    "code": "string"
  }
]
```

### DELETE /url/delete/{code} - удаление ссылки по коду

### DELETE /url/delete/all - удаление всех ссылок


## Описание STATISTIC API

### POST /time/sum - суммарное время выполнения метода за все время.
структура запроса:
```
{
  "name": "string"
}
```
структура ответа:
```
{
  "nameMethod": "string",
  "sumExecutionTime": 0
}
```

### POST /time/method - время выполнения метода, списком.
структура запроса:
```
{
  "name": "string"
}
```
структура ответа:
```
[
  {
    "nameMethod": "string",
    "executionTime": 0
  }
]
```

### POST /time/count - число вызовов метода.
структура запроса:
```
{
  "name": "string"
}
```
структура ответа:
```
{
  "nameMethod": "string",
  "countExecution": 0
}
```

### POST /time/avg - среднее время выполнения метода.
структура запроса:
```
{
  "name": "string"
}
```
структура ответа:
```
{
  "nameMethod": "string",
  "avgExecutionTime": 0
}
```

### POST /time/avg/group - среднее время выполнения группы методов.
структура запроса:
```
{
  "name": "string"
}
```
структура ответа:
```
{
  "nameMethod": "string*",
  "avgExecutionTime": 0
}
```

## Контакты

email: gretskih@mail.ru <br/>
telegram: @Anatolij_gr