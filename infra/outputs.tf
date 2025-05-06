output "cluster_endpoint" {
  value = module.eks.cluster_endpoint
}

output "cluster_name" {
  value = module.eks.cluster_name
}

output "bastion_public_ip" {
  value = module.bastion.public_ip
}


# output "public_ip" {
#   value = aws_instance.this[0].public_ip
# }

