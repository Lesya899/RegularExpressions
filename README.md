В данном репозитории 2 проекта, в которых применяются регулярные выражения, многопоточность, Stream API, потоки ввода/вывода.

Проект первый: отчеты по курсам.

Задан log-file с описанием четырёхдневных курсов в следующем виде:

  09:20 Введение
  
  11:00 Упражнения
  
  11:15 Перерыв
  
  11:35 Скаляры
  
  12:30 Обеденный перерыв
  
  13:30 Упражнения
  
  14:10 Решения
  
  14:30 Перерыв
  
  14:40 Массивы
  
  15:40 Упражнения
  
  17:00 Решения
  
  17:30 Конец
   
  09:30 Углубленное изучение массивов
  
  10:30 Перерыв
  
  10:50 Упражнения
   
  Каждая строка начинается со времени, за которым следует описание активности. Пустые строки разделяют дни.
  Некоторые активности представляют собой названия лекций, например "Введение", "Скаляры", "Массивы".
  Другие - названия определённых повторяющихся отрезков времени: "Упражнения", "Перерыв", "Решения" и т.д.
  Словом "Конец" отмечается конец дня.
 
  Используя регулярные выражения считать данные из файла, а затем сгенерировать два отчёта в двух разных файлах в следующем виде:
  
 1. В виде временных отрезков:
  
  09:20-11:00 Введение
  
  11:00-11:15 Упражнения
  
  11:15-11:35 Перерыв
 
 2. В виде общего времени, потраченного на активности за день, и детализированного описания лекций:
   
  Лекции: 210 минут 22%
  
  Решения: 95 минут 9%
  
  Перерыв: 65 минут 6%
   
  ...
  Лекции:
  
  Введение: 23 минуты 2%
  
   ------------------------------------------------------------------------------------------------------------------------------------------------------------
   
Второй проект: Эмулятор сервиса поддержки.

Все жалобы клиентов хранятся в хронологическом порядке в виде текстового лог-файла следующего формата (столбцы разделены через запятую):

- Порядковый номер клиента
 
- Дата и время звонка в ISO формате

- Фамилия и Имя клиента
 
- Телефон клиента

- Текст жалобы


Каждая новая жалоба идет с новой строки в лог-файле.

Например:

1, 2021-09-13T10:15:30, Иванов Иван, +375 29 999 78 90, Не включается свет

2, 2021-12-22T11:38:16, Петров Петр, +375257777765, Почему опять не работает интернет?

3, 2021-12-28T06:55:24, Петров Петр, 333652193, Кто-то оборвал мне телефонный кабель

Задача:

С какой-то периодичностью (например, раз в 2 минуты) считывать все новые записи из лог-файла и отправлять их диспетчерам для созвона с клиентами (ограничить количество диспетчеров, например, 2-3).

Созвон длится какое-то фиксированное время (например, 3-5 секунд), после чего он записывается в другой лог-файл в виде:

- Порядковый номер клиента с предыдущего лог файла
 
- Дата и время созвона
 
- Номер телефона клиента

Например:
2, 2022-01-04 04:15, +375 (25) 777-77-65

1, 2022-01-04 04:30, +375 (29) 999-78-90

3, 2022-01-04 04:45, +375 (33) 365-21-93


Номера телефонов могут быть представлены по-разному, поэтому привести их к одному формату: +375 (29) 999-78-90.
 
