#!/bin/bash

# Script to automatically generate Kubernetes secrets from Terraform outputs
# This eliminates the need to manually copy/paste database credentials

# Get Terraform outputs
RDS_HOSTNAME=$(terraform output -raw rds_hostname)
RDS_PORT=$(terraform output -raw rds_port)
RDS_USERNAME=$(terraform output -raw rds_username)
RDS_DATABASE=$(terraform output -raw rds_database_name)
DB_PASSWORD=$(grep db_password terraform.tfvars | cut -d '"' -f2)

# Create the namespace if it doesn't exist
kubectl create namespace ecommerce --dry-run=client -o yaml | kubectl apply -f -

# Create the Secret and ConfigMap with dynamic values
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Secret
metadata:
  name: mysql-credentials
  namespace: ecommerce
type: Opaque
stringData:
  username: ${RDS_USERNAME}
  password: ${DB_PASSWORD}
  url: jdbc:mysql://${RDS_HOSTNAME}:${RDS_PORT}/${RDS_DATABASE}
EOF

echo "Database credentials have been applied to Kubernetes."
echo "Your application should now be able to connect to: jdbc:mysql://${RDS_HOSTNAME}:${RDS_PORT}/${RDS_DATABASE}"