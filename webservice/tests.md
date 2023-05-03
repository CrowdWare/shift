# unit tests for the webservice

## message of the day
curl http://128.140.48.116:8080/message?name=Art


## register account
curl -d '{"key":"1234", "name":"Artanidos", "uuid":"00.00.00", "ruuid":"11.11.11"}' -H "Content-Type: application/json" -X POST http://128.140.48.116:8080/register


## friendlist
curl http://128.140.48.116:8080/friendlist