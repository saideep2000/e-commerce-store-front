Since your Docker images are now in Docker Hub, here’s the correct next-step sequence to move from infrastructure to deployment.




cd infra/
terraform init
terraform plan
terraform apply
terraform destroy -auto-approve



-----------------------------------------------

 Configure kubectl for EKS
Once Terraform is done, configure your local kubectl:

aws eks --region us-east-1 update-kubeconfig --name ecommerce-cluster

(If access fails due to private subnets → ✅ SSH into bastion host)

see if you've bastion so that you can access the eks from your local


------------------------------


You'll need a bastion for you to access your eks,

you'll first get your ipv4 by :

curl -4 ifconfig.me

Once you get it, you'll update your Terraform security group rule to allow SSH access from your IP

------------------------------

ssh -i /path/to/your-key.pem ec2-user@<BASTION_PUBLIC_IP>

ssh -i ../key-pair/aws_login.pem ec2-user@54.163.197.243


# Install AWS CLI
sudo yum update -y
sudo yum install -y aws-cli

# Install kubectl for EKS 1.27
curl -LO "https://dl.k8s.io/release/v1.29.0/bin/linux/amd64/kubectl"
chmod +x kubectl
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
kubectl version --client

------------------------------

aws eks --region us-east-1 update-kubeconfig --name ecommerce-cluster

aws configure

You can test with:
kubectl get nodes

-----------------------------------------------

Install ArgoCD in the Cluster
Install ArgoCD into the Kubernetes cluster:

kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml



Then port-forward the UI:
kubectl port-forward svc/argocd-server -n argocd 8080:443

-----------------------------------------------

after forwarding you need SSH tunneling

ssh -i ../key-pair/aws_login.pem -L 8080:localhost:8080 ec2-user@54.163.197.243

Update your local kubeconfig:
aws eks update-kubeconfig --name ecommerce-cluster --region us-east-1



Visit: https://localhost:8080
Default login:

username: admin

password:

kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath=\"{.data.password}\" | base64 -d

-------------------------------

cat > ecommerce-app-of-apps.yaml << 'EOF'
apiVersion: argoproj.io/v1alpha1
kind: Application
metadata:
  name: ecommerce
  namespace: argocd
spec:
  project: default
  source:
    repoURL: https://github.com/saideep2000/e-commerce-store-front.git
    targetRevision: main
    path: argocd
  destination:
    server: https://kubernetes.default.svc
    namespace: argocd
  syncPolicy:
    automated:
      prune: true
      selfHeal: true
EOF





kubectl apply -f ecommerce-app-of-apps.yaml

No need to do anything from here in the argocd UI 

bastion_public_ip = "54.163.197.243"
cluster_endpoint = "https://1EA1F3911E69185016465066EC54CB98.sk1.us-east-1.eks.amazonaws.com"
cluster_name = "ecommerce-cluster"
db_connection_string = "jdbc:mysql://ecommerce-db.cru4uuao25en.us-east-1.rds.amazonaws.com:3306/product_catalog"
rds_database_name = "product_catalog"
rds_hostname = "ecommerce-db.cru4uuao25en.us-east-1.rds.amazonaws.com"
rds_port = 3306
rds_username = "admin"



chmod +x db-secret-generation.sh


terraform apply
./db-secret-generation.sh


-------------------------------


--------------------------------



------------------------------



