# Model

## Account
| Name | Sample| Size |   
| --- | --- |  --- |
| ipaddress | 555.555.555.555 | 15 | 
| uuid |dc7785a3-7f30-445a-ac28-d34f64206541 | 36 |   


## Refer
| Name | Sample | Size |
| --- | --- | --- |
| source | dc7785a3-7f30-445a-ac28-d34f64206541| 36 |
| refers | dc7785a3-7f30-445a-ac28-d34f64206541| 36 |


## Sum
Account 51 bytes
Refer 72 bytes

10.000.000 user * account 51 = 510.000.000 bytes, 510.000 KB, 510 MB
10.000.000 user refer 10 other users * refer 72 bytes = 7.200.000.000 bytes, 7.200.000 KB, 7.200 MB  

We need about 10 GB database space plus 10 GB for an index