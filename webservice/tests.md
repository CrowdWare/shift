# unit tests for the webservice

## message of the day
curl http://artanidosatcrowdwareat.pythonanywhere.com/message?name=Art


## register account
curl -d '{"key":"1234", "name":"Artanidos", "uuid":"00.00.00", "ruuid":"11.11.11"}' -H "Content-Type: application/json" -X POST http://artanidosatcrowdwareat.pythonanywhere.com/register
