# This script retrieves an access token from Keycloak using the Resource Owner Password Credentials Grant (password flow).
$realm = "tickets"
$keycloakUrl = "http://localhost:9090"
$clientId = "api-gateway"

$username     = Read-Host "Enter Keycloak username (admin@tickets.local)"
$plainPassword = Read-Host "Enter password(Password123)"
$clientSecret = Read-Host "Enter client secret (from Keycloak client 'api-gateway')"


try {
    $response = Invoke-RestMethod -Method Post `
        -Uri "$keycloakUrl/realms/$realm/protocol/openid-connect/token" `
        -ContentType "application/x-www-form-urlencoded" `
        -Body @{
        client_id     = $clientId
        client_secret = $clientSecret
        grant_type    = "password"
        username      = $username
        password      = $plainPassword
    }

    Write-Host "`nAccess Token retrieved successfully!"
    $accessToken = $response.access_token
    $accessToken | clip

    Write-Host "`nToken has been copied to clipboard."
    Write-Host "`nFirst 100 characters of the token:"
    Write-Host ($accessToken.Substring(0, 100) + "...")

} catch {
    Write-Host "`nError retrieving access token:"
    Write-Host $_.Exception.Message
}