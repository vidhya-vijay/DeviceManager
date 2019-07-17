#!/bin/bash
​
[ -s "/.nvm/nvm.sh" ] && \. "/.nvm/nvm.sh"
cd /var/www/html/light-bootstrap-dashboard-react-master
npm install
nohup npm start > /dev/null 2>&1 &