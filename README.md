Este projeto faz parte de uma das entregas do programa RECODE PRO 2023. A entrega consistem em desenvolver uma API para uma agência de viagens. A API conta com 4 entidades: Cliente, Viagem, Reserva (Que é a relação entre Cliente e Viagem) e Comentario. As principais entidades são Cliente e Viagem, todas elas possuem seus respectivos CRUDs. Além disso, nos controllers das classes principais, existem também endpoints importantes relacionados aos comentários e às reservas, ajustados conforme necessário.

As ferramentas utilizadas foram: Spring Boot, Spring Data JPA, MySQL, Swagger e Postman.

Para garantir que nossa aplicação funcione sem perrengues, é só criar o arquivo application.properties na pasta de recursos (src\main\resources).

Durante a construção, usei as seguintes configurações para esse arquivo:


***application.properties***
```
spring.datasource.url=jdbc:mysql://localhost:3306/NomeDoSchemaNoMySQL?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=CredencialParaUsername
spring.datasource.password=CredencialParaSenha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

Não precisa se preocupar, é só trocar esses detalhes conforme necessário.
