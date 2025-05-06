output "cluster_endpoint" {
  value = module.eks.cluster_endpoint
}

output "cluster_name" {
  value = module.eks.cluster_name
}

output "bastion_public_ip" {
  value = module.bastion.public_ip
}

output "db_connection_string" {
  description = "Spring Boot JDBC connection string"
  value       = "jdbc:mysql://${aws_db_instance.ecommerce.address}:${aws_db_instance.ecommerce.port}/${aws_db_instance.ecommerce.db_name}"
  sensitive   = false
}