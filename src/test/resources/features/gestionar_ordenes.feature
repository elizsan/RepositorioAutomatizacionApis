Feature: Gestionar orden PetStore

  @test1
  Scenario Outline: Creacion de Order
    Given la url "https://petstore.swagger.io/"
    When creo el order con id "<id>", petId "<petId>", quantity "<quantity>", shipDate "<shipDate>", status "<status>", complete "<complete>"
    Then el código de respuesta es igual a 200
    And body tiene status "<status>"

    Examples:
    | id | petId | quantity | shipDate   | status | complete |
    | 1  | 0     | 0        | 2024-08-23T18:46:40.523Z  | placed | true |
    | 2  | 1     | 1        | 2024-08-23T18:50:40.523Z  | placed | true |


  @test2
  Scenario Outline: Consulta de Order
    Given la url "https://petstore.swagger.io/"
    When consulto el order con id "<id>"
    Then el código de respuesta es igual a "<statusCode>"
    And body tiene status
    And imprimir body

    Examples:
      | id | statusCode  |
      | 1  | 200         |
      | 2  | 200         |
      | 12 | 404         |
