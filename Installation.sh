#!/bin/bash
export DEBIAN_FRONTEND=noninteractive
export LC_ALL=C

LOGFILE='/var/log/installation.log'

# Variables
DBUSER='dbuser'
DBPASSW='password'
DBNAME='library_db'

# Updating the server
echo "Starting Installation"
apt-get -y update >> $LOGFILE 2>&1
apt-get -y upgrade >> $LOGFILE 2>&1

# Installing requirements for server
echo "Installing Requirements"

	# Installing MySQL
	echo "Installing MySQL"
	apt-get -y install mysql-server >> $LOGFILE 2>&1

	# Configuring MySQL
	echo "mysql-server mysql-server/root_password password $DBPASSW" | debconf-set-selections >> $LOGFILE 2>&1
	echo "mysql-server mysql-server/root_password_again password $DBPASSW" | debconf-set-selections >> $LOGFILE 2>&1

	# Creating database, granting access to root
	mysql -u root -p$DBPASSW -e "CREATE DATABASE IF NOT EXISTS $DBNAME;" >> $LOGFILE 2>&1
	mysql -u root -p$DBPASSW -e "GRANT ALL ON $DBNAME.* TO '$DBUSER'@'%' IDENTIFIED BY '$DBPASSW';" >> $LOGFILE 2>&1
	mysql -u root -p$DBPASSW -e "FLUSH PRIVILEGES;" >> $LOGFILE 2>&1


# Configuring the server
echo "Configure server"
sudo sed -i 's/bind-address/# bind-address/g' /etc/mysql/mysql.conf.d/mysqld.cnf >> $LOGFILE 2>&1
sudo service mysql restart >> $LOGFILE 2>&1


echo "Installation is done."