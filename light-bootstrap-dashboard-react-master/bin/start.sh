#!/bin/bash
​[ -s "/.nvm/nvm.sh" ] && \. "/.nvm/nvm.sh"
cd /var/apps/devicemanager
tar -zxvf light-bootstrap-dashboard-react-master.tar.gz
npm install