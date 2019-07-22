#!/bin/bash
​[ -s "/.nvm/nvm.sh" ] && \. "/.nvm/nvm.sh"
cd /var/apps/devicemanager
npm install
nohup npm start > /dev/null 2>&1 &