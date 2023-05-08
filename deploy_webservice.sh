systemctl stop shift.service
cp webservice/main.py ../webservice
systemctl start shift.service 