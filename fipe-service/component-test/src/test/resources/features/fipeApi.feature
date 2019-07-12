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
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço        | Acionamentos no mock |
      | fipe               | obter marcas de carros | 1                    |

  Cenário: Usuário tenta obter todas as marcas de carros e recebe resposta vazia na integração com a Fipe
    Dado um mock no serviço "obter marcas de carros" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros] |  |
      | response | status: 200 |                                  |  |
    Quando o serviço "marcas por tipo de veículo" da API deste módulo for chamado com os atributos:
      | method | GET |
    Então o serviço "marcas por tipo de veículo" da API deste módulo responde com os atributos:
      | status | 500                                    |
      | body   | getBrands-IntegrationError-EmptyResult |
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço        | Acionamentos no mock |
      | fipe               | obter marcas de carros | 1                    |

  Cenário: Usuário tenta obter todas as marcas de carros e ocorre erro na integração com a FIPE
    Dado um mock no serviço "obter marcas de carros" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros] |  |
      | response | status: 500 |                                  |  |
    Quando o serviço "marcas por tipo de veículo" da API deste módulo for chamado com os atributos:
      | method | GET |
    Então o serviço "marcas por tipo de veículo" da API deste módulo responde com os atributos:
      | status | 500                            |
      | body   | getBrands-IntegrationError-500 |
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço        | Acionamentos no mock |
      | fipe               | obter marcas de carros | 1                    |

  Cenário: Usuário obtém todos os modelos de uma marca
    Dado um mock no serviço "obter veículos de uma marca" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros, brandId: 21] |                                                         |
      | response | status: 200 | body: getVehicles-OK                          | header: [Content-Type: application/json; charset=utf-8] |
    Quando o serviço "modelos de uma marca" da API deste módulo for chamado com os atributos:
      | method    | GET         |
      | pathParam | brandId: 21 |
    Então o serviço "modelos de uma marca" da API deste módulo responde com os atributos:
      | status | 200          |
      | body   | getModels-OK |
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço             | Acionamentos no mock |
      | fipe               | obter veículos de uma marca | 1                    |

  Cenário: Usuário tenta obter todos os modelos de uma marca e recebe resposta vazia na integração com a Fipe
    Dado um mock no serviço "obter veículos de uma marca" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros, brandId: 21] |  |
      | response | status: 200 |                                               |  |
    Quando o serviço "modelos de uma marca" da API deste módulo for chamado com os atributos:
      | method    | GET         |
      | pathParam | brandId: 21 |
    Então o serviço "modelos de uma marca" da API deste módulo responde com os atributos:
      | status | 500                                    |
      | body   | getModels-IntegrationError-EmptyResult |
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço             | Acionamentos no mock |
      | fipe               | obter veículos de uma marca | 1                    |

  Cenário: Usuário tenta obter todos os modelos de uma marca e ocorre erro na integração com a FIPE
    Dado um mock no serviço "obter veículos de uma marca" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros, brandId: 21] |  |
      | response | status: 500 |                                               |  |
    Quando o serviço "modelos de uma marca" da API deste módulo for chamado com os atributos:
      | method    | GET         |
      | pathParam | brandId: 21 |
    Então o serviço "modelos de uma marca" da API deste módulo responde com os atributos:
      | status | 500                            |
      | body   | getModels-IntegrationError-500 |
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço             | Acionamentos no mock |
      | fipe               | obter veículos de uma marca | 1                    |

  Cenário: Usuário obtém os detalhes de um modelo
    Dado um mock no serviço "obter modelos de um veículo" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros, brandId: 21, vehicleId: 515] |                                                         |
      | response | status: 200 | body: getVehicleModels-OK                                     | header: [Content-Type: application/json; charset=utf-8] |
    Dado um mock no serviço "obter detalhes de um modelo" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros, brandId: 21, vehicleId: 515, modelId: 1999-1] |                                                         |
      | response | status: 200 | body: getPrice-OK                                                              | header: [Content-Type: application/json; charset=utf-8] |
    Quando o serviço "detalhes de um modelo" da API deste módulo for chamado com os atributos:
      | method    | GET          |
      | pathParam | brandId: 21  |
      | pathParam | modelId: 515 |
    Então o serviço "detalhes de um modelo" da API deste módulo responde com os atributos:
      | status | 200           |
      | body   | getDetails-OK |
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço             | Acionamentos no mock |
      | fipe               | obter modelos de um veículo | 1                    |
      | fipe               | obter detalhes de um modelo | 1                    |

  Esquema do Cenario: Usuário tenta obter os detalhes de um modelo e recebe resposta vazia na integração com a Fipe
    Dado um mock no serviço "obter modelos de um veículo" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros, brandId: 21, vehicleId: 515] |                                                         |
      | response | status: 200 | <body de "obter modelos de um veículo">                       | header: [Content-Type: application/json; charset=utf-8] |
    Dado um mock no serviço "obter detalhes de um modelo" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET | pathParam: [vehicleType: carros, brandId: 21, vehicleId: 515, modelId: 1999-1] |                                                         |
      | response | status: 200 | <body de "obter detalhes de um modelo">                                        | header: [Content-Type: application/json; charset=utf-8] |
    Quando o serviço "detalhes de um modelo" da API deste módulo for chamado com os atributos:
      | method    | GET          |
      | pathParam | brandId: 21  |
      | pathParam | modelId: 515 |
    Então o serviço "detalhes de um modelo" da API deste módulo responde com os atributos:
      | status | 500                                     |
      | body   | getDetails-IntegrationError-EmptyResult |
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço             | Acionamentos no mock                    |
      | fipe               | obter modelos de um veículo | <hits de "obter modelos de um veículo"> |
      | fipe               | obter detalhes de um modelo | <hits de "obter detalhes de um modelo"> |
    Exemplos:
      | body de "obter modelos de um veículo" | body de "obter detalhes de um modelo" | hits de "obter modelos de um veículo" | hits de "obter detalhes de um modelo" |
      |                                       | body: getPrice-OK                     | 1                                     | 0                                     |
      | body: getVehicleModels-OK             |                                       | 1                                     | 1                                     |

  Esquema do Cenario: Usuário tenta obter os detalhes de um modelo e ocorre erro na integração com a FIPE
    Dado um mock no serviço "obter modelos de um veículo" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET                               | pathParam: [vehicleType: carros, brandId: 21, vehicleId: 515] |                                                         |
      | response | <status de "obter modelos de um veículo"> | <body de "obter modelos de um veículo">                       | header: [Content-Type: application/json; charset=utf-8] |
    Dado um mock no serviço "obter detalhes de um modelo" da integração "fipe" com requisição e resposta com os atributos:
      | request  | method: GET                               | pathParam: [vehicleType: carros, brandId: 21, vehicleId: 515, modelId: 1999-1] |                                                         |
      | response | <status de "obter detalhes de um modelo"> | <body de "obter detalhes de um modelo">                                        | header: [Content-Type: application/json; charset=utf-8] |
    Quando o serviço "detalhes de um modelo" da API deste módulo for chamado com os atributos:
      | method    | GET          |
      | pathParam | brandId: 21  |
      | pathParam | modelId: 515 |
    Então o serviço "detalhes de um modelo" da API deste módulo responde com os atributos:
      | status | 500                             |
      | body   | getDetails-IntegrationError-500 |
    E apenas os mocks de integrações abaixo foram chamados:
      | Nome da integração | Nome do serviço             | Acionamentos no mock                    |
      | fipe               | obter modelos de um veículo | <hits de "obter modelos de um veículo"> |
      | fipe               | obter detalhes de um modelo | <hits de "obter detalhes de um modelo"> |
    Exemplos:
      | status de "obter modelos de um veículo" | body de "obter modelos de um veículo" | status de "obter detalhes de um modelo" | body de "obter detalhes de um modelo" | hits de "obter modelos de um veículo" | hits de "obter detalhes de um modelo" |
      | status: 500                             |                                       | status: 200                             | body: getPrice-OK                     | 1                                     | 0                                     |
      | status: 200                             | body: getVehicleModels-OK             | status: 500                             |                                       | 1                                     | 1                                     |
