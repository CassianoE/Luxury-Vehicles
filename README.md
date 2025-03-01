# 🚗 Luxury-Vehicles

Luxury-Vehicles é uma API REST para gerenciamento de veículos de luxo, permitindo operações CRUD em diferentes tipos de veículos (carros, barcos, aviões, motos) e seus respectivos proprietários.

---

## 📌 **Tecnologias Utilizadas**
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL / PostgreSQL
- Lombok
- Jakarta Validation
- Postman (para testes)

---

## ⚙️ **Configuração do Ambiente**

### **1️⃣ Pré-requisitos**
- [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://www.mysql.com/downloads/) ou [PostgreSQL](https://www.postgresql.org/download/)
- [Postman](https://www.postman.com/downloads/) (para testes de API)

### **2️⃣ Configuração do Banco de Dados**
No arquivo **`application.properties`**, configure as credenciais do seu banco:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/luxury_vehicles
spring.datasource.username=seu-username
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

⚠️ **Se estiver usando PostgreSQL**, troque `jdbc:mysql://` por `jdbc:postgresql://` e ajuste as credenciais.

### **3️⃣ Como Executar**
1. Clone o repositório:

2. Entre no diretório do projeto:
   ```sh
   cd luxury-vehicles
   ```
3. Compile e execute:
   ```sh
   mvn spring-boot:run
   ```
4. A API estará rodando em:
   ```
   http://localhost:8080
   ```

---

## 📖 **Estrutura do Projeto**
```
Luxury-Vehicles/
│── src/
│   ├── main/
│   │   ├── java/app/
│   │   │   ├── controller/   # Controladores REST
│   │   │   ├── entity/       # Entidades JPA
│   │   │   ├── repository/   # Repositórios JPA
│   │   │   ├── service/      # Serviços de regras de negócio
│   │   │   ├── messages/     # Mensagens de erro
│   ├── resources/
│   │   ├── application.properties   # Configurações da aplicação
│── pom.xml   # Dependências do projeto
│── README.md
```

---

## 🛠️ **Endpoints da API**
### 🔹 **1. Proprietários (`/api/owners`)**

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| `POST` | `/save` | Cadastra um novo proprietário |
| `GET`  | `/findAll` | Lista todos os proprietários |
| `GET`  | `/findById/{id}` | Busca proprietário por ID |
| `GET`  | `/findByName?name=Pedro` | Busca proprietário por nome |
| `GET`  | `/findByCpf/{cpf}` | Busca proprietário por CPF |
| `DELETE` | `/delete/{id}` | Remove um proprietário |

📌 **Exemplo de criação de proprietário (POST `/api/owners/save`)**
```json
{
  "name": "Pedro Andrade",
  "cpf": "12345678901",
  "email": "pedro@email.com",
  "phone": "11987654321"
}
```

---

### 🔹 **2. Barcos (`/api/boats`)**

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| `POST` | `/save` | Cadastra um barco |
| `GET`  | `/findAll` | Lista todos os barcos |
| `GET`  | `/findById/{id}` | Busca barco por ID |
| `GET`  | `/findByYear?year=2010` | Busca barcos por ano |
| `DELETE` | `/delete/{id}` | Remove um barco |

📌 **Exemplo de criação de barco (POST `/api/boats/save`)**
```json
{
  "brand": "Yamaha",
  "model": "SX190",
  "year": 2020,
  "price": 75000,
  "length": 7.5,
  "hullType": "Monocasco",
  "owner": {
    "id": 2
  }
}
```

---

## 🚀 **Melhorias Futuras**
- 📌 Implementar autenticação com **Spring Security** + JWT
- 📌 Criar logs com **Spring Boot Actuator**
- 📌 Implementar paginação para consultas grandes



