systemctl stop shift.service
cp webservice/main.py /shift/webservice/
systemctl start shift.service