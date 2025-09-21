Feature: Flujo completo del sistema bancario

  Background:
    # Define las URLs base de los microservicios
    * url 'http://localhost:8081/api'
    * def msCuentasUrl = 'http://localhost:8082/api'

    # Genera un número de identificación único para el cliente en cada ejecución
    * def uniqueId = 'DNI-' + java.util.UUID.randomUUID()
    * def numeroCuenta = 'ACC-' + uniqueId.substring(4, 12)

  Scenario: Crear un cliente, su cuenta y realizar movimientos exitosos y fallidos

    # 1. Crear un nuevo cliente
    Given path '/clientes'
    And request
      """
      {
        "persona": {
          "nombre": "Cliente de Prueba Karate",
          "identificacion": "#(uniqueId)",
          "genero": "M",
          "edad": 35,
          "direccion": "Avenida Siempre Viva 742",
          "telefono": "555-1234"
        },
        "password": "password123"
      }
      """
    When method POST
    Then status 201
    # Valida la estructura de la respuesta y guarda el clienteId
    And match response == { "clienteId": "#uuid", "nombre": "Cliente de Prueba Karate", "identificacion": "#(uniqueId)", "estado": true }
    * def clienteId = response.clienteId
    * print 'Cliente Creado con ID:', clienteId

    # 2. Crear una cuenta para el cliente recién creado
    Given url msCuentasUrl
    And path '/cuentas'
    And request
      """
      {
        "numeroCuenta": "#(numeroCuenta)",
        "tipo": "AHORROS",
        "saldoInicial": 500.00,
        "clienteId": "#(clienteId)"
      }
      """
    When method POST
    Then status 201
    And match response.numeroCuenta == numeroCuenta
    And match response.saldo == 500.00
    * print 'Cuenta Creada:', response

    # 3. Realizar un depósito de 200
    Given url msCuentasUrl
    And path '/movimientos'
    # La cabecera Idempotency-Key es importante para evitar duplicados
    And header Idempotency-Key = java.util.UUID.randomUUID() + ''
    And request
      """
      {
        "numeroCuenta": "#(numeroCuenta)",
        "tipo": "DEPOSITO",
        "valor": 200.00
      }
      """
    When method POST
    Then status 201
    # El saldo posterior debe ser 500 (inicial) + 200 (depósito) = 700
    And match response.saldoPosterior == 700.00
    * print 'Depósito realizado. Saldo posterior:', response.saldoPosterior

    # 4. Realizar un retiro de 150
    Given url msCuentasUrl
    And path '/movimientos'
    And header Idempotency-Key = java.util.UUID.randomUUID() + ''
    And request
      """
      {
        "numeroCuenta": "#(numeroCuenta)",
        "tipo": "RETIRO",
        "valor": 150.00
      }
      """
    When method POST
    Then status 201
    # El saldo posterior debe ser 700 - 150 = 550
    And match response.saldoPosterior == 550.00
    * print 'Retiro realizado. Saldo posterior:', response.saldoPosterior

    # 5. Intentar realizar un retiro mayor al saldo disponible
    Given url msCuentasUrl
    And path '/movimientos'
    And header Idempotency-Key = java.util.UUID.randomUUID() + ''
    And request
      """
      {
        "numeroCuenta": "#(numeroCuenta)",
        "tipo": "RETIRO",
        "valor": 1000.00
      }
      """
    When method POST
    # Se espera un error de "Bad Request" (400)
    Then status 400
    And match response.error == "BUSINESS_RULE"
    And match response.message == "Saldo no disponible"
    * print 'Prueba de retiro con saldo insuficiente exitosa.'