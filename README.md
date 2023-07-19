<h1> Bank_application </h1>
<a href="https://github.com/Filvik/Bank_application">
    <img src="bank.jpg" width="200" alt ="www.ebrd.com" height="200">
  </a>
  <br>
 <h2>Описание приложения:</h2>
 <p>Приложение реализует Rest API по работе с банковским счетом. Это API может быть использовано банкоматом, веб-приложением или мобильным приложением Интернет-банка. </p>
  <h3>В приложении реализованы следующие типы операции:</h3>
  <ul>
     <li>Запроса баланса</li>
     <li>Снятие со счёта денежных средств</li>
     <li>Внесения на счёт денежных средств</li>
     <li>Отображение списка операций за выбранный период</li>
  </ul>
  <h3>Для работы приложения необходимо:</h3>
      <li><a href="https://en.wikipedia.org/wiki/PostgreSQL">Database PostgresSQL</a></li>
       <h3>Запуск ПО:</h3>
       <p>Для запуска программы необходимо прописать настройки подключения в файл <b>external.properties</b> который должен находится в корневой папке проекта, либо после сборки приложения этот файл должен лежать в одной папке с jar файлом приложения.
       В этом файле укажите название базы данных, которая должна быть созданна заранее(
         <code>spring.datasource.url=jdbc:postgresql://localhost:5432/Bank_application</code>).
         При старте приложение, в этой базе данных , автоматически создаются таблицы ACCOUNT, OPERATIONS и TYPE_OPERATION где и будут хранится данные пользователя(<i>номер счёта, баланс, тип валюты</i>), данные выполненных операций(<i>дата, номер счёта, тип операции, сумма операции</i>) и перечнь возможных операций. Обращаясь к
         контроллерам по их адресу реализуется выше указанный типы операции.</p>
         <h3>В приложении так же реализовано:</h3>
    <ul>
       <li>Логгирование с использованием log4j2</li>
          <p>Настройки логирование находятся в файле <b>log4j2-spring.xml</b>.
          Сами логи пишутся в файл <b>application.log</b>, который лежит в папке <code>logs</code>.</p>
       <li><a href="https://swagger.io/solutions/api-documentation/">Swagger</a></li>
            <p>Фреймворк Swagger позволяет разработчикам создавать интерактивную, удобочитаемую для машин и человека документацию по API.
            Swagger доступен при запущенном приложении по адресу:<a href="http://localhost:8080/swagger-ui/index.html#/"> http://localhost:8080/swagger-ui/index.html#/</a>.</p>
       <li>Тестирование</li>
            <p>Тестирование проходит в тестовой базе данных, на тестовых данных. Настройки подключение лежат в файле <b>application.properties</b> по адрессу <code>Bank-application\src\test\resources</code>. В этом файле укажите название базы данных, которая должна быть созданна заранее.</p>
         <li>Система контроля версии БД с использованием <a href="https://www.liquibase.com/">Liquibase</a></li>
           <p>В процессе реализации поставленной задачи было необходимо регулярно дорабатывать БД. Что бы этот процесс был легко отслеживаемым, управляемым и 
               безопасным в проект было внедрено это программное обеспечение. Для легкого создания сценария миграции базы данных для Liquibase использовался плагин 
               <a href="https://jpa-buddy.com/">JPA Buddy</a>.</p>
    </ul>
<h3>Cкриншот структуры базы данных:</h3>
<a><img src="SchemaDb.png" width="200" height="200"></a>
  
 
 

 
