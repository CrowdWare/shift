systemctl stop shift.service
cp webservice/main.py /home/shift/webservice/
systemctl start shift.service