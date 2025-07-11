# run.ps1
# This script is used to run a Docker Compose setup for a project.
docker compose down -v

docker compose build

docker compose up -d

Write-Host "Project run" -ForegroundColor Green
