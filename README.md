
# MTG Collection Manager

Aplicativo Android desenvolvido em Kotlin para gerenciamento de coleções e baralhos de Magic: The Gathering, integrando consultas a uma API REST e armazenamento local de dados.

## Informações acadêmicas

- **Instituição:** Unichristus
- **Disciplina:** Desenvolvimento para Dispositivos Móveis
- **Professor:** Tiago Guimarães Sombra
- **Semestre:** 2026.2
- **Trabalho:** NP2 — Projeto Integrador Mobile

## Integrantes

- Tomaz Galileu Pessoa Nogueira Silva do Nascimento — [TomazGalileu](https://github.com/TomazGalileu)
- Maria Clara Sousa Freitas — [maria-clara67](https://github.com/maria-clara67)

## 1. Problema e proposta

Jogadores e colecionadores de Magic: The Gathering podem encontrar dificuldades para consultar informações sobre cartas, organizar suas coleções pessoais e selecionar cartas para a construção de baralhos.

O MTG Collection Manager foi desenvolvido com o objetivo de centralizar essas atividades em um aplicativo móvel, permitindo a consulta de cartas por meio de uma API REST, o gerenciamento de coleções pessoais e a organização de baralhos.

A proposta consiste em oferecer uma interface simples e funcional, integrando informações obtidas remotamente com dados armazenados localmente no dispositivo.

## 2. Público-alvo

Jogadores, colecionadores e entusiastas de Magic: The Gathering interessados em consultar informações sobre cartas, organizar suas coleções pessoais e gerenciar baralhos.

## 3. Principais funcionalidades

### 3.1. Tela inicial

A tela inicial apresenta três opções principais de navegação:

- Pesquisar Cartas.
- Minha Coleção.
- Meus Baralhos.

### 3.2. Pesquisa de cartas

- Pesquisa de cartas por meio da API Scryfall.
- Exibição dos resultados com nomes e imagens.
- Seleção de cartas para consulta detalhada.
- Navegação para a tela de detalhes.
- Tratamento dos estados de carregamento, sucesso e erro.
- Tratamento de pesquisas sem resultados.
- Tratamento de falhas de conexão e carregamento de imagens.

### 3.3. Detalhes da carta

- Consulta individual de cartas pelo identificador.
- Exibição da imagem, nome e tipo da carta.
- Alternância entre as faces de cartas compatíveis.
- Adição de cartas à coleção pessoal.
- Incremento da quantidade de cartas já cadastradas.
- Feedback sobre o resultado da operação de armazenamento.
- Possibilidade de repetir uma consulta em caso de falha.

### 3.4. Minha Coleção

- Listagem das cartas cadastradas na coleção pessoal.
- Exibição de nomes, imagens e quantidades das cartas.
- Aumento e redução da quantidade de cópias.
- Controle para impedir quantidades inferiores a uma unidade.
- Acesso à tela de detalhes da carta selecionada.
- Exclusão de cartas mediante confirmação.
- Proteção contra exclusão de cartas utilizadas em baralhos.
- Tratamento de estados de carregamento e erro.
- Feedback sobre as operações realizadas.
- Persistência local utilizando Room.

### 3.5. Meus Baralhos

- Criação de baralhos com nome.
- Validação para impedir a criação de baralhos com nomes vazios.
- Listagem dos baralhos cadastrados.
- Abertura de baralhos para edição.
- Exclusão de baralhos mediante confirmação.
- Persistência local dos baralhos.

### 3.6. Editor de Baralho

- Exibição do nome do baralho selecionado.
- Listagem das cartas pertencentes ao baralho.
- Inclusão de cartas existentes na coleção pessoal.
- Remoção de cartas do baralho.
- Exibição do total de cartas distintas.
- Controle para impedir a inclusão repetida da mesma carta.
- Persistência das alterações realizadas.

A inclusão ou remoção de cartas de um baralho não altera a quantidade de cartas armazenadas na coleção pessoal.

A exclusão de uma carta da coleção é impedida quando ela pertence a algum baralho. Para excluí-la da coleção, o usuário deve primeiro removê-la dos baralhos em que está presente.

## 4. API REST utilizada

O aplicativo utiliza a API pública Scryfall, especializada em informações sobre cartas de Magic: The Gathering.

A API disponibiliza informações como nomes, identificadores, imagens, tipos, custos de mana e estruturas de cartas com múltiplas faces.

- **Nome:** Scryfall API
- **URL base:** https://api.scryfall.com/
- **Documentação oficial:** https://scryfall.com/docs/api
- **Formato das respostas:** JSON

### 4.1. Endpoints utilizados

| Método HTTP | Endpoint | Finalidade |
| --- | --- | --- |
| GET | `/cards/search?q={query}` | Pesquisar cartas |
| GET | `/cards/{id}` | Consultar uma carta pelo identificador |

### 4.2. Exemplos de requisições

Pesquisa de cartas:

    GET https://api.scryfall.com/cards/search?q=Lightning+Bolt

Consulta individual:

    GET https://api.scryfall.com/cards/{id}

O parâmetro `query` representa o conteúdo da pesquisa informado pelo usuário.

O parâmetro `id` representa o identificador único da carta retornado pela API.

### 4.3. Integração com Retrofit e JSON

O Retrofit é utilizado para realizar as requisições HTTP à API Scryfall.

As respostas são recebidas em formato JSON e convertidas em objetos Kotlin utilizando o conversor Gson.

Entre os principais campos mapeados estão:

| Campo JSON | Finalidade |
| --- | --- |
| `id` | Identificador da carta |
| `name` | Nome da carta |
| `lang` | Idioma da carta |
| `layout` | Estrutura da carta |
| `printed_name` | Nome impresso, quando disponível |
| `mana_cost` | Custo de mana |
| `type_line` | Tipo da carta |
| `oracle_text` | Texto de regras |
| `image_uris` | Endereços das imagens |
| `card_faces` | Informações das diferentes faces da carta |

Os modelos `ScryfallCard`, `ScryfallSearchResponse`, `ScryfallImageUris` e `ScryfallCardFace` representam os dados recebidos da API.

As operações de comunicação remota são executadas de forma assíncrona, evitando bloquear a interface principal do aplicativo.

## 5. Tecnologias e bibliotecas utilizadas

| Tecnologia | Finalidade |
| --- | --- |
| Kotlin | Linguagem de programação |
| Android Studio | Ambiente de desenvolvimento |
| Jetpack Compose | Construção das interfaces declarativas |
| Material 3 | Componentes visuais |
| Navigation Compose | Navegação e passagem de dados entre telas |
| Retrofit | Consumo da API REST |
| Gson | Conversão de respostas JSON em objetos Kotlin |
| Coil | Carregamento de imagens |
| Room | Persistência das cartas da coleção |
| SharedPreferences | Persistência dos baralhos |
| ViewModel | Gerenciamento de estados e operações |
| StateFlow | Observação e atualização dos estados |
| AndroidX Lifecycle | Gerenciamento do ciclo de vida |
| Kotlin Coroutines | Execução de operações assíncronas |
| KSP | Processamento de código utilizado pelo Room |
| Gradle | Gerenciamento da compilação e das dependências |
| Git e GitHub | Controle de versão e colaboração |

## 6. Organização do projeto

O código-fonte está organizado em pacotes de acordo com suas responsabilidades, separando interfaces, navegação, comunicação remota e persistência local.

    com.tomazgalileu.mtgcollectionmanager/
    |
    |-- data/
    |   |-- local/
    |   |   |-- dao/
    |   |   |-- entity/
    |   |   |-- AppDatabase.kt
    |   |
    |   |-- mapper/
    |   |-- model/
    |   |-- remote/
    |   |-- repository/
    |
    |-- ui/
    |   |-- home/
    |   |-- search/
    |   |-- details/
    |   |-- collection/
    |   |-- decks/
    |   |-- navigation/
    |   |-- theme/
    |
    |-- MainActivity.kt

### Responsabilidades dos pacotes

| Pacote | Responsabilidade |
| --- | --- |
| `data/local` | Banco de dados Room, entidades e DAOs |
| `data/mapper` | Conversão dos modelos remotos em modelos locais |
| `data/model` | Representação dos baralhos |
| `data/remote` | Configuração da API, Retrofit e modelos JSON |
| `data/repository` | Acesso e gerenciamento dos dados |
| `ui/home` | Tela inicial |
| `ui/search` | Pesquisa de cartas |
| `ui/details` | Detalhes das cartas |
| `ui/collection` | Gerenciamento da coleção pessoal |
| `ui/decks` | Gerenciamento e edição de baralhos |
| `ui/navigation` | Definição das rotas e navegação |
| `ui/theme` | Configuração visual do aplicativo |

A organização utiliza ViewModels, repositórios e estados observáveis para separar as responsabilidades das interfaces e das operações de acesso aos dados.

Essa estrutura apresenta características do padrão MVVM (Model-View-ViewModel), associado a uma camada de repositórios.

## 7. Navegação e passagem de dados

O aplicativo utiliza Navigation Compose para gerenciar a navegação entre suas seis telas.

    Tela Inicial
    |
    |-- Pesquisar Cartas
    |   |-- Detalhes da Carta
    |
    |-- Minha Coleção
    |   |-- Detalhes da Carta
    |
    |-- Meus Baralhos
        |-- Editor de Baralho

### Rotas utilizadas

| Rota | Tela |
| --- | --- |
| `home` | Tela inicial |
| `search` | Pesquisa de cartas |
| `cardDetails/{cardId}` | Detalhes da carta |
| `collection` | Minha Coleção |
| `decks` | Meus Baralhos |
| `deckEditor/{deckId}` | Editor de Baralho |

Os identificadores das cartas e dos baralhos são transmitidos como argumentos nas respectivas rotas.

O argumento `cardId` identifica a carta que será consultada na API Scryfall.

O argumento `deckId` identifica o baralho que será aberto para edição.

Os eventos da interface são encaminhados aos componentes responsáveis pelas operações e pela navegação por meio de funções de callback.

## 8. Persistência local

O aplicativo utiliza dois mecanismos de armazenamento local: Room e SharedPreferences.

### 8.1. Room — Coleção de cartas

O Room é responsável pelo armazenamento das cartas pertencentes à coleção pessoal.

São armazenadas informações como:

- Identificador da carta.
- Nome.
- Quantidade de cópias.
- Estrutura da carta.
- Endereços das imagens.
- Informações sobre as faces da carta, quando disponíveis.
- Outros atributos obtidos da API.

A comunicação com o banco de dados é realizada por meio de DAOs e do `CollectionRepository`.

A coleção permite operações de inserção, consulta, atualização de quantidade e exclusão de registros.

As informações armazenadas permanecem disponíveis após o fechamento e a reabertura do aplicativo.

### 8.2. SharedPreferences — Baralhos

Os baralhos utilizam SharedPreferences para armazenamento local de suas informações em formato JSON.

Cada baralho possui:

- Identificador.
- Nome.
- Lista de identificadores das cartas que o compõem.

O `DeckRepository` é responsável pela leitura, gravação e atualização desses dados.

As informações completas das cartas são recuperadas da coleção armazenada no Room, evitando a duplicação desnecessária dos dados.

Essa abordagem foi adotada para simplificar a implementação dos baralhos dentro do escopo acadêmico e preservar a estrutura existente do banco de dados da coleção.

### 8.3. Integração entre coleção e baralhos

Somente cartas previamente cadastradas na coleção podem ser adicionadas aos baralhos.

Uma carta pode pertencer a diferentes baralhos sem alterar sua quantidade na coleção.

A exclusão de um baralho não implica a exclusão das cartas da coleção pessoal.

A interface Minha Coleção verifica a utilização das cartas nos baralhos antes de permitir sua exclusão.

## 9. Tratamento de estados e erros

O aplicativo utiliza estados observáveis para representar diferentes situações durante sua execução.

Entre os estados e situações tratados estão:

- Carregamento de informações.
- Conclusão bem-sucedida de consultas.
- Pesquisas sem resultados.
- Falhas de comunicação com a API.
- Ausência de conexão com a internet.
- Falhas no carregamento de imagens.
- Erros nas operações de armazenamento.
- Coleção vazia.
- Ausência de baralhos cadastrados.
- Baralhos sem cartas.
- Tentativa de criação de baralho com nome vazio.
- Tentativa de exclusão de carta utilizada em baralho.

São utilizados indicadores de carregamento, mensagens informativas, diálogos de confirmação e componentes de feedback para comunicar essas situações ao usuário.

## 10. Instruções para execução

### 10.1. Requisitos

- Android Studio compatível com a configuração do projeto.
- Android SDK 37 para compilação.
- Dispositivo físico ou emulador com Android 8.0 (API 26) ou superior.
- Conexão com a internet para consultas à API Scryfall.
- Git para clonagem do repositório.

### 10.2. Configurações do aplicativo

| Configuração | Valor |
| --- | --- |
| Versão mínima do Android | API 26 |
| Target SDK | API 37 |
| Compile SDK | API 37 |
| Version Name | 1.0 |
| Version Code | 1 |

### 10.3. Executando pelo Android Studio

1. Clone o repositório:

       git clone https://github.com/TomazGalileu/mtg-collection-manager.git

2. Abra a pasta do projeto no Android Studio.
3. Aguarde a sincronização das dependências do Gradle.
4. Caso necessário, instale o SDK indicado na configuração do projeto.
5. Configure um emulador ou conecte um dispositivo Android.
6. Selecione a configuração de execução do aplicativo.
7. Execute o projeto utilizando a opção Run.

Não é necessária uma chave de API para utilizar os endpoints públicos da Scryfall empregados no projeto.

## 11. Testes realizados

Foram realizados testes funcionais manuais durante o desenvolvimento e após a integração das funcionalidades na branch principal.

| Funcionalidade testada | Resultado |
| --- | --- |
| Navegação entre as telas | Aprovado |
| Pesquisa de cartas | Aprovado |
| Carregamento de imagens | Aprovado |
| Consulta aos detalhes das cartas | Aprovado |
| Alternância entre faces | Aprovado |
| Adição de cartas à coleção | Aprovado |
| Incremento de cartas já cadastradas | Aprovado |
| Aumento e redução de quantidades | Aprovado |
| Exclusão de cartas da coleção | Aprovado |
| Criação de baralhos | Aprovado |
| Inclusão de cartas nos baralhos | Aprovado |
| Remoção de cartas dos baralhos | Aprovado |
| Exclusão de baralhos | Aprovado |
| Persistência após reiniciar o aplicativo | Aprovado |
| Tratamento de ausência de internet na pesquisa | Aprovado |
| Acesso aos dados locais sem internet | Aprovado |
| Restabelecimento das consultas após recuperar a conexão | Aprovado |

Os testes tiveram como objetivo verificar o funcionamento das principais funcionalidades, a comunicação com a API, a persistência dos dados e a integração entre as telas.

## 12. Limitações conhecidas

O aplicativo é um protótipo acadêmico funcional, desenvolvido com escopo reduzido.

As principais limitações são:

- A pesquisa e a consulta aos detalhes das cartas dependem de conexão com a internet.
- A pesquisa não possui suporte completo à localização dos nomes das cartas em português.
- Não há garantia de disponibilidade das imagens offline.
- A inclusão de cartas pela tela de detalhes acrescenta uma cópia por operação.
- Os baralhos não possuem controle individual de quantidade de cópias de cada carta.
- Não são aplicadas as regras oficiais dos formatos de Magic: The Gathering.
- A persistência da coleção e dos baralhos utiliza mecanismos de armazenamento independentes, sem integridade referencial garantida pelo banco de dados.
- Não há sincronização em nuvem ou sistema de backup próprio do aplicativo.
- Os dados locais podem ser perdidos com a desinstalação ou limpeza dos dados do aplicativo.

Essas limitações representam decisões de escopo adotadas para priorizar o funcionamento das funcionalidades essenciais e o cumprimento dos objetivos acadêmicos.

## 13. Controle de versão e colaboração

O desenvolvimento foi realizado utilizando Git e GitHub.

Foram utilizadas branches específicas para funcionalidades, commits descritivos e Pull Requests para integração das contribuições à branch principal.

Entre as principais atividades realizadas estão:

- Implementação da navegação.
- Desenvolvimento da pesquisa utilizando a API Scryfall.
- Implementação da tela de detalhes.
- Implementação do banco de dados Room.
- Integração da adição de cartas à coleção.
- Desenvolvimento da interface Minha Coleção.
- Implementação do sistema de baralhos.
- Integração e testes das funcionalidades.

O histórico de commits e Pull Requests está disponível no repositório do projeto.

**Repositório:**

https://github.com/TomazGalileu/mtg-collection-manager

## 14. Utilização de inteligência artificial

Durante o desenvolvimento do projeto, agentes de IA foram utilizadas como recurso de apoio técnico, orientação, esclarecimento de dúvidas e auxílio à implementação das funcionalidades do aplicativo.

### 14.1. Apoio aos conteúdos fundamentais da disciplina

- **Organização da arquitetura e estrutura dos pacotes:** auxílio na definição da estrutura do projeto.

- **Kotlin e Jetpack Compose:** apoio na compreensão dos recursos da linguagem Kotlin e dos componentes utilizados na construção das interfaces declarativas do aplicativo.

- **Interfaces e eventos:** auxílio na utilização dos componentes visuais do Jetpack Compose.

- **Integração com Retrofit e API Scryfall:** apoio na compreensão da documentação da API.

- **Modelagem dos dados recebidos em JSON:** auxílio na interpretação da estrutura das respostas fornecidas pela API.

- **Controle de versão com Git e GitHub:** auxílio na compreensão e utilização de branches, commits, operações de integração e Pull Requests, contribuindo para a organização do desenvolvimento colaborativo e a preservação das contribuições individuais.

### 14.2. Apoio às funcionalidades complementares

- **Persistência local dos baralhos:** participação na definição e implementação de uma estratégia simplificada de armazenamento utilizando SharedPreferences.

- **Integração entre coleção e baralhos:** auxílio na definição das regras de associação entre as cartas armazenadas na coleção e os baralhos cadastrados, preservando a independência das quantidades da coleção pessoal.

- **Carregamento de imagens:** auxílio na configuração da biblioteca Coil, no tratamento de imagens de cartas com múltiplas faces e na resolução de problemas relacionados ao carregamento das imagens disponibilizadas pela API.

### 14.3. Apoio ao desenvolvimento e à documentação

- **Geração e revisão de código:** fornecimento de exemplos, sugestões e geração de trechos e arquivos de código Kotlin, utilizados como apoio à implementação das funcionalidades.

- **Identificação e resolução de erros:** auxílio na interpretação de mensagens de erro, identificação de possíveis causas e proposição de soluções durante a configuração das dependências, compilação e execução do aplicativo.

- **Testes funcionais:** auxílio na elaboração de cenários de teste e critérios de validação das funcionalidades implementadas. Os testes foram executados pelos integrantes no ambiente de desenvolvimento.

- **Documentação:** auxílio na organização, elaboração e revisão do README, considerando as características do aplicativo, as tecnologias empregadas, as limitações conhecidas e os requisitos estabelecidos para o trabalho acadêmico.

### 14.4. Responsabilidade dos integrantes

A inteligência artificial foi utilizada como recurso de apoio ao desenvolvimento e à aprendizagem, incluindo orientações técnicas, sugestões de implementação e geração de código.

Os integrantes permanecem responsáveis pelas decisões adotadas, pela integração e geração dos códigos ao projeto, pela execução dos testes, pela validação das funcionalidades e pela compreensão técnica da implementação apresentada.

## 15. Considerações finais

O MTG Collection Manager foi desenvolvido com finalidade acadêmica, demonstrando a integração entre interfaces móveis, eventos, navegação, passagem de dados, consumo de API REST, interpretação de JSON, Retrofit e tratamento dos estados da comunicação remota.

O projeto também incorpora recursos de persistência local, permitindo o gerenciamento de uma coleção pessoal de cartas e a organização de baralhos.

A proposta prioriza uma experiência funcional, integrada e objetiva, respeitando o escopo estabelecido para o trabalho acadêmico.

## 16. Créditos

Magic: The Gathering é uma marca da Wizards of the Coast.

As informações e imagens das cartas são consultadas por meio da API Scryfall.

- Magic: The Gathering: https://magic.wizards.com/
- Scryfall: https://scryfall.com/
- Documentação da API: https://scryfall.com/docs/api

Este aplicativo é um projeto acadêmico independente, sem vínculo oficial com a Wizards of the Coast ou com a Scryfall.
