<h2>Projeto Movieflix  - Desenvolvimento de testes unitários para validar uma API REST de gerenciamento de catálogo de filmes.</h2>

Neste repositório foi implementada uma pequena API REST para o gerenciamento de catálogo de filmes. 
Foram utilizadas ferramentas/frameworks de testes unitários para validar o sistema de gerenciamento de catálogo de filmes, 
e os principais conceitos e vantagens de criar testes unitários com JUnit e Mockito. 
Além disso, foi emprega a técnica/prática do TDD para desenvolver funcionalidades da API.

Nesse projeto, foram abordados os seguintes tópicos:

* Utilização do Git/Github para o versionamento do projeto e seus testes unitários. 
* Pirâmide dos tipos de testes, e também a importância de cada tipo de teste durante o ciclo de desenvolvimento.
* Foco nos testes unitários: mostrar o porque é importante o desenvolvimento destes tipos de testes como parte do ciclo de desenvolvimento de software.
* Principais frameworks para testes unitários em Java: JUnit, Mockito e Hamcrest. 
* Desenvolvimento de testes unitários para validação de funcionalides básicas: criação, listagem, consulta por nome e exclusão de filmes.
* TDD: incremento do número likes/dislikes.

Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Para executar a suíte de testes implementada no projeto, basta executar o seguinte comando:

```shell script
mvn clean test
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/movies
```

Para visualizar a documentação das apis:

```
http://localhost:8080/api-docs
```

De forma gráfica via swagger-ui:

```
http://localhost:8080/swagger-ui/index.html
```

Para criar/executar seus scripts de testes com http client no intellij veja a documentação:

```
https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html
```


Foram utilizadas as seguintes ferramentas para desenvolver o projeto:

* Java 16 ou versões superiores.
* Maven 3.6.3 ou versões superiores.
* Intellj IDEA Community Edition ou sua IDE favorita.
* Controle de versão GIT.
* Spring Boot

Abaixo, seguem links bem bacanas, sobre tópicos mencionados durante a aula:

* [SDKMan! para gerenciamento e instalação do Java e Maven](https://sdkman.io/)
* [Referência do Intellij IDEA Community, para download](https://www.jetbrains.com/idea/download)
* [Palheta de atalhos de comandos do Intellij](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)
* [Site oficial do Spring](https://spring.io/)
* [Site oficial JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
* [Site oficial Mockito](https://site.mockito.org/)
* [Site oficial Hamcrest](http://hamcrest.org/JavaHamcrest/)
* [Referências - testes em geral com o Spring Boot](https://www.baeldung.com/spring-boot-testing)
* [Referência para o padrão arquitetural REST](https://restfulapi.net/)
* [Referência pirâmide de testes - Martin Fowler](https://martinfowler.com/articles/practical-test-pyramid.html#TheImportanceOftestAutomation)



