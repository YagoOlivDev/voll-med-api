# API REST de Gestão de Consultas

Esta é uma API REST desenvolvida em Java utilizando o framework Spring. A API permite o cadastro e gerenciamento de médicos, pacientes e consultas, além de oferecer funcionalidades de login com segurança através do Spring Security.

## Funcionalidades

- **Cadastro de Médicos**
    - Permite o registro de novos médicos no sistema.

- **Cadastro de Pacientes**
    - Permite o registro de novos pacientes.

- **Agendamento de Consultas**
    - Possibilita a criação de consultas entre pacientes e médicos.

- **Login Seguro**
    - Implementação de autenticação e autorização utilizando Spring Security.

- **Tratamento de Exceções**
    - A API possui um tratamento de exceções global para lidar com erros de forma eficiente.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security
- JPA (Java Persistence API)
- My SQL

## Endpoints

- **Médicos**
    - `POST /medicos` - Cadastrar um novo médico
    - `GET /medicos` - Listar todos os médicos
    - `GET /medicos/{id}` - Obter detalhes de um médico específico
    - `PUT /medicos/{id}` - Atualizar as informações de um médico específico
    - `DELETE /medicos/{id}` - Remover um médico (soft delete)

- **Pacientes**
    - `POST /pacientes` - Cadastrar um novo paciente
    - `GET /pacientes` - Listar todos os pacientes
    - `GET /pacientes/{id}` - Obter detalhes de um paciente específico
    - `PUT /pacientes/{id}` - Atualizar as informações de um paciente específico
    - `DELETE /pacientes/{id}` - Remover um paciente (soft delete)

- **Consultas**
    - `POST /consultas` - Agendar uma nova consulta
    - `GET /consultas` - Listar todas as consultas
    - `GET /consultas/{id}` - Obter detalhes de uma consulta específica
    - `PUT /consultas/{id}` - Atualizar uma consulta existente
    - `DELETE /consultas/{id}` - Remover uma consulta (soft delete)



