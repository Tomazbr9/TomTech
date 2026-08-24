# TomTech

O **TomTech** é um blog técnico voltado para a publicação e compartilhamento de artigos, tutoriais e conhecimentos sobre desenvolvimento de sistemas, engenharia de software e tecnologias em geral.

## Sobre o Projeto

Desenvolvido com uma arquitetura moderna e desacoplada, o TomTech é estruturado para oferecer uma experiência de leitura rápida e direta ao público, acompanhada de um painel administrativo protegido para o gerenciamento de conteúdo.

### Principais Características
* **Área Pública (Leitores):** Acesso livre e sem necessidade de login para leitura dos artigos publicados.
* **Painel Administrativo (Autor):** Área restrita protegida por autenticação JWT para criação, edição, gerenciamento de status e exclusão de posts.
* **Armazenamento em Nuvem:** Gerenciamento de imagens integrado com o AWS S3.

## Tecnologias Utilizadas

* **Frontend:** Angular, Tailwind CSS (hospedado na Vercel)
* **Backend:** Java com Spring Boot, Spring Security, Spring Data JPA (hospedado em VPS via Docker)
* **Banco de Dados:** PostgreSQL
* **Armazenamento de Mídia:** AWS S3