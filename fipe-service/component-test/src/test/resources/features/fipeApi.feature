# language: pt
@CleanStubby
Funcionalidade: Comunicação com a API pública conhecida como tabela FIPE
  A fim de facilitar o meu contexto de negócio
  Eu quero obter informações da tabela FIPE e selecionar de forma estruturada apenas as informações que preciso

  Cenário: Obtém todas as marcas de carros
    Dado um mock no serviço "obter marcas de carros" da API FIPE com requisição e resposta com os atributos:
      | request  | method: GET | body: get-brands       |                                  |
      | response | status: 200 | body: get-brands-OK    | header: [h1: v1, h1: v1, h1: v1] |
      | response | status: 200 | body: get-brands-OK    | header: [h1: v1, h1: v1]         |
      | response | status: 500 | body: get-brands-ERROR |                                  |
    Quando o serviço "marcas por tipo de veículo" da API deste módulo for chamado com os atributos:
      | method | GET                |
      | body   | get-brands-request |
    Então o serviço "marcas por tipo de veículo" da API deste módulo responde com os atributos:
      | status | 200                 |
      | body   | get-brands-response |