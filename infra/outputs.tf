output "springboot_server_ip" {
  value = aws_instance.springboot_server.public_ip
}
