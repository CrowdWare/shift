systemctl stop tracker.service
mv /var/log/tracker.log /var/log/tracker_$(date +%Y%m%d).log
cp -r tracker /home/shift
systemctl start tracker.service