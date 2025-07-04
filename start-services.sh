#!/bin/bash

echo "🚀 Starting Content Organizer Services..."

# Start AI Chat Service
echo "Starting AI Chat Service on port 8081..."
./gradlew :services:ai-chat-service:bootRun > ai-chat.log 2>&1 &
AI_CHAT_PID=$!

# Start Music Creator Service
echo "Starting Music Creator Service on port 8082..."
./gradlew :services:music-creator-service:bootRun > music-creator.log 2>&1 &
MUSIC_PID=$!

# Start Flow Runner Service
echo "Starting Flow Runner Service on port 8086..."
./gradlew :services:flow-runner:bootRun > flow-runner.log 2>&1 &
FLOW_PID=$!

# Start YouTube Service
echo "Starting YouTube Service on port 8084..."
./gradlew :services:youtube-service:bootRun > youtube.log 2>&1 &
YOUTUBE_PID=$!

# Start Twitter Service
echo "Starting Twitter Service on port 8085..."
./gradlew :services:twitter-service:bootRun > twitter.log 2>&1 &
TWITTER_PID=$!

echo "✅ All services started!"
echo "PIDs: AI Chat: $AI_CHAT_PID, Music: $MUSIC_PID, Flow: $FLOW_PID, YouTube: $YOUTUBE_PID, Twitter: $TWITTER_PID"

# Wait for services to start
echo "⏳ Waiting for services to start..."
sleep 30

# Check service health
echo "🔍 Checking service health..."

echo "AI Chat Service (8081):"
curl -s http://localhost:8081/api/v1/ai-chat/health || echo "Not ready"

echo "Music Creator Service (8082):"
curl -s http://localhost:8082/api/v1/music/health || echo "Not ready"

echo "Flow Runner Service (8086):"
curl -s http://localhost:8086/api/v1/flows/health || echo "Not ready"

echo "YouTube Service (8084):"
curl -s http://localhost:8084/api/v1/upload/health || echo "Not ready"

echo "Twitter Service (8085):"
curl -s http://localhost:8085/api/v1/tweet/health || echo "Not ready"

echo "🎉 Services startup complete!" 