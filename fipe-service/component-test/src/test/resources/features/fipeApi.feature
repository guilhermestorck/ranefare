# language: pt
@CleanStubby
Funcionalidade: Comunicação com a API pública conhecida como tabela FIPE
  A fim de facilitar o meu contexto de negócio
  Eu quero obter informações da tabela FIPE e selecionar de forma estruturada apenas as informações que preciso

  Cenário: Usuário obtém todas as marcas de carros
    Dado um mock no serviço "obter marcas de carros" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros] |                                                         |
      | response | status: 200 | body: getBrands-OK               | header: [Content-Type: application/json; charset=utf-8] |
    Quando o serviço "marcas por tipo de veículo" da API deste módulo for chamado com os atributos:
      | method | GET |
    Então o serviço "marcas por tipo de veículo" da API deste módulo responde com os atributos:
      | status | 200          |
      | body   | getBrands-OK |

  Cenário: Usuário tenta obter todas as marcas de carros e recebe resposta vazia na integração com a Fipe
    Dado um mock no serviço "obter marcas de carros" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros] |  |
      | response | status: 200 |                                  |  |
    Quando o serviço "marcas por tipo de veículo" da API deste módulo for chamado com os atributos:
      | method | GET |
    Então o serviço "marcas por tipo de veículo" da API deste módulo responde com os atributos:
      | status | 500                                    |
      | body   | getBrands-IntegrationError-EmptyResult |

  Cenário: Usuário tenta obter todas as marcas de carros e ocorre erro na integração com a FIPE
    Dado um mock no serviço "obter marcas de carros" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros] |  |
      | response | status: 500 |                                  |  |
    Quando o serviço "marcas por tipo de veículo" da API deste módulo for chamado com os atributos:
      | method | GET |
    Então o serviço "marcas por tipo de veículo" da API deste módulo responde com os atributos:
      | status | 500                            |
      | body   | getBrands-IntegrationError-500 |