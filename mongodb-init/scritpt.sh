mongodump --host localhost --port 27017 --username root --password test --out /var/lib/minikube/data/mongodb/data/backup-18-06-2023

mongorestore  /var/lib/minikube/data/mongodb/data/backup-18-06-2023 --username root --password test