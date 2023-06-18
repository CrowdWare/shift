# Install pre
pip install pycryptodome
pip install flask
pip install mysql-connector-python


# Create and maintain a websercice using uswgi

This guide is for internal usage only.  
If you want to run your own webservice then you have to alter some paths.  


## Creation
- Create or copy the file shift.service to /etc/systemd/system
- Create or copy the file uswgi.ini to /home/shift/webservice


## Start the service
```console
sudo systemctl daemon-reload
sudo systemctl start shift.service
```

## Stop the service
```console
sudo systemctl daemon-reload
sudo systemctl stop shift.service
```

## Reload the service
```console
sudo systemctl daemon-reload
sudo systemctl restart shift.service
```

## truncate the log file
```console
sudo truncate -s 0 /var/log/shift.log
```  

## archivate log file
```console
mv /var/log/shift.log /var/log/shift_$(date +%Y%m%d).log
```
