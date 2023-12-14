Para garantir que nossa aplicação funcione sem perrengues, é só criar o arquivo application.properties na pasta de recursos (src\main\resources).

Durante a construção, usei as seguintes configurações para esse arquivo:

spring.datasource.url=jdbc:mysql://localhost:3306/NomeDoSchemaNoMySQL?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=CredencialParaUsername
spring.datasource.password=CredencialParaSenha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

Não precisa se preocupar, é só trocar esses detalhes conforme necessário.
