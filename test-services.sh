#!/bin/bash

echo "🚀 Content Organizer Services Test Script"
echo "=========================================="

# Kill any existing processes on our ports
echo "🛑 Stopping any existing processes on ports 8080-8085..."
pkill -f "java.*8080" || true
pkill -f "java.*8081" || true
pkill -f "java.*8082" || true
pkill -f "java.*8083" || true
pkill -f "java.*8084" || true
pkill -f "java.*8085" || true

sleep 2

# Start services in background
echo "🔧 Starting services..."

echo "📝 Starting AI Chat Service (port 8081)..."
./gradlew :services:ai-chat-service:bootRun -Dspring.profiles.active=local > logs/ai-chat-service.log 2>&1 &
AI_CHAT_PID=$!

echo "🎵 Starting Music Creator Service (port 8082)..."
./gradlew :services:music-creator-service:bootRun -Dspring.profiles.active=local > logs/music-creator-service.log 2>&1 &
MUSIC_PID=$!

echo "🔄 Starting Flow Runner Service (port 8080)..."
./gradlew :services:flow-runner:bootRun -Dspring.profiles.active=local > logs/flow-runner.log 2>&1 &
FLOW_PID=$!

echo "📺 Starting YouTube Service (port 8084)..."
./gradlew :services:youtube-service:bootRun -Dspring.profiles.active=local > logs/youtube-service.log 2>&1 &
YOUTUBE_PID=$!

echo "🐦 Starting Twitter Service (port 8085)..."
./gradlew :services:twitter-service:bootRun -Dspring.profiles.active=local > logs/twitter-service.log 2>&1 &
TWITTER_PID=$!

# Wait for services to start
echo "⏳ Waiting for services to start..."
sleep 10

# Check if services are running
echo "🔍 Checking service status..."

check_service() {
    local name=$1
    local port=$2
    local pid=$3
    
    if curl -s http://localhost:$port/actuator/health > /dev/null 2>&1; then
        echo "✅ $name is running on port $port (PID: $pid)"
        return 0
    else
        echo "❌ $name is not responding on port $port"
        return 1
    fi
}

# Create logs directory if it doesn't exist
mkdir -p logs

# Check each service
check_service "AI Chat Service" 8081 $AI_CHAT_PID
check_service "Music Creator Service" 8082 $MUSIC_PID
check_service "Flow Runner Service" 8080 $FLOW_PID
check_service "YouTube Service" 8084 $YOUTUBE_PID
check_service "Twitter Service" 8085 $TWITTER_PID

echo ""
echo "📊 Service URLs:"
echo "   AI Chat Service:     http://localhost:8081/api/v1/ai-chat/health"
echo "   Music Creator:       http://localhost:8082/api/v1/music/health"
echo "   Flow Runner:         http://localhost:8080/actuator/health"
echo "   YouTube Service:     http://localhost:8084/api/v1/youtube/health"
echo "   Twitter Service:     http://localhost:8085/api/v1/twitter/health"
echo ""
echo "📝 Logs are available in the logs/ directory"
echo ""
echo "🛑 To stop all services, run: pkill -f 'gradle.*bootRun'"
echo ""

# Keep script running
echo "🔄 Services are running. Press Ctrl+C to stop all services..."
trap 'echo "🛑 Stopping all services..."; pkill -f "gradle.*bootRun"; exit' INT
wait 