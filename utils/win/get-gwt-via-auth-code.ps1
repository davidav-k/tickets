# This script retrieves a JWT access token from Keycloak using the Authorization Code Flow.
$realm = "tickets"
$keycloakUrl = "http://localhost:9090"
$clientId = "vue-frontend"
$redirectUri = "http://localhost:5173"

$authUrl = "$keycloakUrl/realms/$realm/protocol/openid-connect/auth?client_id=$clientId&redirect_uri=$redirectUri&response_type=code&scope=openid"

Write-Host "A browser will open to log into Keycloak and receive an authorization code..."
Start-Process $authUrl
Write-Host "After logging in, copy the code parameter from the browser address bar."

$code = Read-Host "Insert the code from the browser address bar"

try {
    $response = Invoke-RestMethod -Method Post `
        -Uri "$keycloakUrl/realms/$realm/protocol/openid-connect/token" `
        -ContentType "application/x-www-form-urlencoded" `
        -Body @{
        client_id    = $clientId
        grant_type   = "authorization_code"
        code         = $code
        redirect_uri = $redirectUri
    }

    Write-Host "`nAccess Token retrieved successfully!"
    $accessToken = $response.access_token
    $accessToken | clip

    Write-Host "`nToken has been copied to clipboard."
    Write-Host "`nFirst 100 characters of the token:"
    Write-Host ($accessToken.Substring(0, 100) + "...")

} catch {
    Write-Host "`nError retrieving token:"
    Write-Host $_.Exception.Message
}