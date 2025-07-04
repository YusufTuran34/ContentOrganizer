#!/bin/bash

echo "🧪 Testing Content Organizer Service Endpoints"
echo "=============================================="

# Function to test an endpoint
test_endpoint() {
    local name=$1
    local url=$2
    local expected_status=$3
    
    echo -n "Testing $name... "
    
    # Try to connect with timeout
    response=$(curl -s -w "%{http_code}" --connect-timeout 5 --max-time 10 "$url" 2>/dev/null)
    status_code="${response: -3}"
    
    if [ "$status_code" = "$expected_status" ]; then
        echo "✅ OK (Status: $status_code)"
        return 0
    else
        echo "❌ FAILED (Expected: $expected_status, Got: $status_code)"
        return 1
    fi
}

# Wait a bit for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 5

# Test each service
echo ""
echo "🔍 Testing Health Endpoints:"
echo "----------------------------"

test_endpoint "AI Chat Service" "http://localhost:8081/api/v1/ai-chat/health" "200"
test_endpoint "Music Creator Service" "http://localhost:8082/api/v1/music/health" "200"
test_endpoint "Flow Runner Service" "http://localhost:8080/api/v1/flow-runner/flows/health" "200"
test_endpoint "YouTube Service" "http://localhost:8084/api/v1/youtube/health" "200"
test_endpoint "Twitter Service" "http://localhost:8085/api/v1/twitter/health" "200"

echo ""
echo "🔍 Testing Actuator Endpoints:"
echo "------------------------------"

test_endpoint "AI Chat Actuator" "http://localhost:8081/api/v1/ai-chat/actuator/health" "200"
test_endpoint "Music Creator Actuator" "http://localhost:8082/api/v1/music/actuator/health" "200"
test_endpoint "Flow Runner Actuator" "http://localhost:8080/api/v1/flow-runner/actuator/health" "200"
test_endpoint "YouTube Actuator" "http://localhost:8084/api/v1/youtube/actuator/health" "200"
test_endpoint "Twitter Actuator" "http://localhost:8085/api/v1/twitter/actuator/health" "200"

echo ""
echo "🔍 Testing Flow Orchestration:"
echo "------------------------------"

test_endpoint "Flow Test Endpoint" "http://localhost:8080/api/v1/flow-runner/flows/test-flow" "200"

echo ""
echo "🔍 Testing Swagger UI Endpoints:"
echo "--------------------------------"

test_endpoint "AI Chat Swagger" "http://localhost:8081/api/v1/ai-chat/swagger-ui.html" "200"
test_endpoint "Music Creator Swagger" "http://localhost:8082/api/v1/music/swagger-ui.html" "200"
test_endpoint "Flow Runner Swagger" "http://localhost:8080/api/v1/flow-runner/swagger-ui.html" "200"
test_endpoint "YouTube Swagger" "http://localhost:8084/api/v1/youtube/swagger-ui.html" "200"
test_endpoint "Twitter Swagger" "http://localhost:8085/api/v1/twitter/swagger-ui.html" "200"

echo ""
echo "✅ Endpoint testing completed!"
echo ""
echo "📊 Service URLs for manual testing:"
echo "   AI Chat Service:     http://localhost:8081/api/v1/ai-chat"
echo "   Music Creator:       http://localhost:8082/api/v1/music"
echo "   Flow Runner:         http://localhost:8080/api/v1/flow-runner"
echo "   YouTube Service:     http://localhost:8084/api/v1/youtube"
echo "   Twitter Service:     http://localhost:8085/api/v1/twitter"
echo ""
echo "🎯 Flow Orchestration Test:"
echo "   curl -X POST http://localhost:8080/api/v1/flow-runner/flows/execute/test-flow-orchestration" 