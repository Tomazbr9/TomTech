# 4. Arquitetura Técnica e Estratégia de Deploy

A arquitetura adota uma abordagem moderna baseada no **desacoplamento total entre o frontend e a API backend**, utilizando serviços em nuvem especializados para hospedagem, persistência e entrega de conteúdo.

## Stack Tecnológica

### Frontend

* **Framework:** **Angular**, modular e tipado, responsável pelas rotas públicas do blog e pelo painel administrativo protegido.
* **Estilização:** **Tailwind CSS**, utilizado para desenvolvimento rápido de uma interface limpa, consistente e responsiva.

### Backend

* **Framework:** **Java com Spring Boot**.
* **Autenticação:** **Spring Security + JWT (JSON Web Token)** para proteção das rotas administrativas e emissão de tokens seguros.
* **Persistência:** **Spring Data JPA / Hibernate** para mapeamento objeto-relacional.
* **Migrações:** **Flyway** para controle versionado do banco de dados.
* **Documentação:** **SpringDoc OpenAPI (Swagger)** para documentação dos endpoints da API REST.

### Banco de Dados

* **SGBD:** **PostgreSQL**.

### Armazenamento de Mídia

* **Cloud Storage:** **AWS S3**, integrado por meio do SDK oficial da AWS para Java, responsável pelo recebimento, armazenamento e gerenciamento das imagens.

## Estratégia de Deploy e Infraestrutura

### Frontend — Vercel

O projeto Angular será compilado e hospedado na **Vercel**, aproveitando sua infraestrutura global para distribuição da aplicação.

Principais benefícios:

* Distribuição por **Edge Network**.
* SSL/HTTPS automático.
* Deploy integrado ao Git.
* Deploys automáticos a cada atualização do repositório.
* Possibilidade de utilização de diferentes ambientes, como *preview* e *production*.

### Backend e Banco de Dados — VPS + Docker

A API Spring Boot e o banco PostgreSQL serão executados em contêineres gerenciados por **Docker Compose** em uma VPS dedicada.

A estrutura será composta, inicialmente, por:

* **Container Spring Boot:** responsável pela execução da API.
* **Container PostgreSQL:** responsável pela persistência dos dados.
* **Docker Network:** responsável pela comunicação interna entre os containers.

A comunicação entre a API e o banco de dados ocorrerá exclusivamente pela rede interna do Docker, evitando a exposição direta do PostgreSQL à internet.

### API — HTTPS e CORS

A API será disponibilizada publicamente por meio de **HTTPS**, utilizando certificado SSL configurado na infraestrutura da VPS.

O backend também deverá possuir uma política de **CORS** restritiva, permitindo requisições somente a partir do domínio oficial do frontend hospedado na Vercel.

Dessa forma, o frontend e o backend permanecem independentes e podem ser implantados, escalados ou modificados separadamente, mantendo uma arquitetura simples e adequada ao escopo do MVP.
