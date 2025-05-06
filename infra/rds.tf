resource "aws_db_subnet_group" "ecommerce" {
  name       = "ecommerce-rds-subnet-group"
  subnet_ids = module.vpc.private_subnets

  tags = {
    Name = "ecommerce-rds-subnet-group"
  }
}

resource "aws_security_group" "rds" {
  name        = "ecommerce-rds-sg"
  description = "Allow MySQL traffic from EKS cluster"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description     = "MySQL from EKS"
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [module.eks.cluster_security_group_id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "ecommerce-rds-sg"
  }
}

resource "aws_db_instance" "ecommerce" {
  identifier             = "ecommerce-db"
  allocated_storage      = 20
  engine                 = "mysql"
  engine_version         = "8.0"
  instance_class         = "db.t3.micro"
  db_name                = "product_catalog"
  username               = "admin"
  password               = var.db_password
  parameter_group_name   = "default.mysql8.0"
  skip_final_snapshot    = true
  publicly_accessible    = false
  multi_az               = false
  db_subnet_group_name   = aws_db_subnet_group.ecommerce.name
  vpc_security_group_ids = [aws_security_group.rds.id]
  
  tags = {
    Name = "ecommerce-database"
  }
}

# Add these to your existing variables.tf file
variable "db_password" {
  description = "RDS database password"
  type        = string
  sensitive   = true
}

# Add these to your existing outputs.tf file
output "rds_hostname" {
  description = "RDS instance hostname"
  value       = aws_db_instance.ecommerce.address
  sensitive   = false
}

output "rds_port" {
  description = "RDS instance port"
  value       = aws_db_instance.ecommerce.port
  sensitive   = false
}

output "rds_username" {
  description = "RDS instance root username"
  value       = aws_db_instance.ecommerce.username
  sensitive   = false
}

output "rds_database_name" {
  description = "RDS database name"
  value       = aws_db_instance.ecommerce.db_name
  sensitive   = false
}