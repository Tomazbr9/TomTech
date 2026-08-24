# Requisitos do Sistema

## Requisitos Funcionais (RF)

* **RF01 - Listagem de Artigos (Público):** O sistema deve exibir na página inicial a listagem de artigos publicados, ordenados do mais recente para o mais antigo.
* **RF02 - Leitura de Artigo (Público):** O leitor deve conseguir clicar em um artigo da listagem para visualizar o seu conteúdo completo, suportando formatação de texto/Markdown.
* **RF03 - Autenticação do Administrador:** O sistema deve prover uma tela de login restrita para autenticação do autor/administrador utilizando credenciais seguras (e-mail e senha).
* **RF04 - Proteção de Rotas Administrativas:** As rotas do painel administrativo só devem ser acessadas por usuários autenticados via token/sessão válida.
* **RF05 - Painel Administrativo - Dashboard:** O autor deve ter acesso a um painel que lista seus artigos cadastrados, com status de **rascunho** ou **publicado**.
* **RF06 - Criação de Artigos (Admin):** O autor deve poder criar novos artigos informando título, resumo, conteúdo principal, tags/categorias e status de publicação.
* **RF07 - Edição de Artigos (Admin):** O autor deve poder editar o conteúdo de artigos existentes.
* **RF08 - Exclusão de Artigos (Admin):** O autor deve poder remover artigos do sistema.
* **RF09 - Upload de imagens dos artigos (Admin):** o autor ao inserir imagem no artigo, a primeira imagem será usada automaticamente como banner do artigo.

## Requisitos Não Funcionais (RNF)

* **RNF01 - Responsividade:** A interface do blog (leitura) deve ser totalmente responsiva, adaptando-se bem a dispositivos móveis, tablets e desktops.
* **RNF02 - Segurança de Senhas:** As senhas do administrador devem ser armazenadas de forma criptografada, utilizando, por exemplo, *bcrypt*.
* **RNF03 - Desempenho:** O carregamento das páginas públicas de leitura deve ser otimizado para garantir uma navegação fluida.
* **RNF04 - Controle de Acesso:** A API e as rotas administrativas devem barrar requisições não autorizadas, retornando status HTTP `401` ou `403`.
