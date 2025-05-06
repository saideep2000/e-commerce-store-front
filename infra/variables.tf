variable "aws_region" {
  default = "us-east-1"
}

variable "cluster_name" {
  default = "ecommerce-cluster"
}

variable "bastion_key_name" {
  description = "SSH key name for bastion host"
  type        = string
}