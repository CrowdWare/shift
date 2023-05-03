# Create an maintain a websercice

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
