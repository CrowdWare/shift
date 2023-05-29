# Create and maintain a ipv8 tracker
The tracker is a node which will be installed on a few server nodes to be used to bootstrap teh SHIFT network.  
It is used by devices to have a first connection point to gather other online nodes and devices.  

This guide is for internal usage only. You may change it for your specific needs.

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
