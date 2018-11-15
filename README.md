# Desafio Ebix

## Sumário <!-- omit in toc -->

- [Desafio Ebix](#desafio-ebix)
	- [Descrição](#descri%C3%A7%C3%A3o)
	- [Atividades](#atividades)
	- [Tecnologias utilizadas](#tecnologias-utilizadas)
	- [Instalação](#instala%C3%A7%C3%A3o)
		- [Baixando os arquivos da aplicação](#baixando-os-arquivos-da-aplica%C3%A7%C3%A3o)
		- [Aplicação](#aplica%C3%A7%C3%A3o)
			- [Diretamente pelo Maven](#diretamente-pelo-maven)
			- [Gerando um executável java (JAR)](#gerando-um-execut%C3%A1vel-java-jar)
	- [API SOAP](#api-soap)
	- [API REST](#api-rest)

## Descrição

​Construir uma aplicação Java utilizando:

- Spring Core para injeção de dependência
- Spring Jdbc com JDBCTemplate para acesso a dados

## Atividades

1. Criar o server-side de um CRUD utilizando RowMapper para a obtenção dos dados
2. Expor o CRUD como webservice SOAP
3. Consumir o serviço consultaCEP do WSDL https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl utilizando CXF para geração do client
4. Expor o serviço do passo 3 como REST ( utilizando JAX-RS)

## Tecnologias utilizadas

Nessa solução foi utilizada as seguintes tecnologias:

- Java 1.8
- H2 Database
- Maven Warp 3.5
- SpringBoot 2.1.0
- SoapUI para os testes de integração

## Instalação

Antes da instalação se pressupõe que o java 1.8 e o git já estão instalados, se não estiverem devem ser instalados antes de continuar

### Baixando os arquivos da aplicação

Para baixar os arquivos do aplicativo deve-se usar o comando:  

`git clone https://github.com/bozoh/desafio-ebix.git`

Depois entrar na pasta do aplicativo:

`cd desafio-ebix`

Os demais comandos a seguir devem ser feito a partir da pasta do aplicativo

### Aplicação

Para executar a aplicação recomenda-se usar uma das duas formas:

#### Diretamente pelo Maven

Para executar a aplicação diretamente pelo maven use o comando:

>No linux:  
> `./mvnw clean spring-boot:run`  
>
>No windows:  
>`mvnw.cmd clean spring-boot:run`  

#### Gerando um executável java (JAR)

Para gerar um executável java use comando:

>No linux:  
> `./mvnw clean package`  
>
>No windows:  
>`mvnw.cmd clean package`  

Após o executável ser gerado, use o comando para inicializar o aplicativo:  

`java -jar target/desafio-0.0.1-SNAPSHOT.jar`

O aplicativo se inicializará no endereço:  

`http://127.0.0.1:8080/`

## API SOAP

A api SOAP está disponível em `http://127.0.0.1:8080/soapws` e o descritor do serviço em `http://127.0.0.1:8080/soapws/usuarios.wsdl`.

Para uma maior detalhamento da API SOAP veja o [arquivo api-soap.html](api-soap.html)

## API REST

A API REST está disponível em `http://127.0.0.1:8080/api`, e para buscar um endereço pelo cep deve-se enviar um `GET` para o caminho `/api/cep/<CEP>`, onde `<CEP>` 
é o cep do endereço desejado.  

E vai retornar um endereço no formato:

```json
{
	"logradouro":"<NOME DA RUA>",
	"complemento2":"<COMPLEMENTO DO ENDEREÇO>",
	"bairro":"<BAIRRO>",
	"cidade":"<CIDADE>",
	"uf":"<UF>",
	"cep":"<CEP>"
}
```

ou um erro se não achar o endereço, no formato

```json
{
	"msg":"<MENSAGEM DE ERRO>",
	"status":"<STATUS DO ERRO>
}
```
