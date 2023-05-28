# Create an maintain a ipv8 tracker

## Creation
- Create or copy the file tracker.service to /etc/systemd/system



## Start the service
```console
sudo systemctl daemon-reload
sudo systemctl enable tracker.service
sudo systemctl start tracker.service
```

## Stop the service
```console
sudo systemctl daemon-reload
sudo systemctl stop tracker.service
```

## Reload the service
```console
sudo systemctl daemon-reload
sudo systemctl restart tracker.service
```

## truncate the log file
```console
sudo truncate -s 0 /var/log/tracker.log
```

## Check status
```console
sudo systemctl status tracker.service
```