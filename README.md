# Order Service
This is a dummy order service. User can create, confirm or cancel the order using this service.
Let's say, user wants to buy something from bol.com, then this service will be used to create and confirm the order.

## End points
1. `create_order` : based on account number & card number this end point, creates an order by, 
    1. Validate if account number & card number and valid amount is present in the request
    2. Get card details (Name, address, gender etc.) from external `card-service`
    3. Update name, address, gender in Order table
    4. Set order status to `CREATED`
2. `confirm_order` : once user wants to proceed, this service confirms the order by 
    1. Validate the order id
    2. Validate the access-code from external service `access-code-verifier`
    3. Call `balance-service` service to get balance for this user
    4. Validate if user has enough balance to proceed
    5. If access-code is valid & balance is available then change the order status to `CONFIRMED`
3. `cancel_order` : if user wants to cancel order by order id then,
    1. Validate the order id
    2. If order exist, then hange the order status to `CANCELLED`
    

## Assignment
- Write jUnit tests
- Write component integration tests
- Write system integration tests
- Find out the bugs developers has made and if possible, suggest improvement or fixes
- Bonus points if you find out security related bugs or improvements
- Bonus point if you can automate the system integration part (using any containers or cicd tools available or any other way )

_You can make assumptions when and where needed and please inform those assumptions in the solution_

## To start on local 
1. `mvn spring-boot:run`
2. Using Postman, request body for 
    1. create_order : 
       
       `{
            "cardNumber":"ssss",
            "passNumber":"1111",
            "amount":10
        }`
    2. confirm_order :
        `
        {
            "orderId":1,
            "accessCode":12345
        }
        `
    3. cancel_order : 
        `
        {
            "orderId":1
        }
        `
3. swagger-ui is available here : `http://localhost:8080/swagger-ui.html`    
