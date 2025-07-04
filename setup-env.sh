#!/bin/bash

# Content Organizer Environment Setup Script
# This script sets up environment variables from the provided credentials

echo "🎯 Setting up Content Organizer Environment Variables"
echo "====================================================="

# Extract credentials from the provided CHANNEL_CONFIGS
# Using the default channel configuration

# YouTube Credentials
export GOOGLE_CLIENT_ID="your-google-client-id"
export GOOGLE_CLIENT_SECRET="your-google-client-secret"
export GOOGLE_REFRESH_TOKEN="your-google-refresh-token"
export YOUTUBE_STREAM_KEY="your-youtube-stream-key"

# Twitter Credentials
export TWITTER_API_KEY="your-twitter-api-key"
export TWITTER_API_SECRET="your-twitter-api-secret"
export TWITTER_ACCESS_TOKEN="your-twitter-access-token"
export TWITTER_ACCESS_TOKEN_SECRET="your-twitter-access-token-secret"
export TWITTER_CLIENT_ID="your-twitter-client-id"
export TWITTER_CLIENT_SECRET="your-twitter-client-secret"
export TWITTER_AUTH_CODE="your-twitter-auth-code"
export TWITTER_CODE_VERIFIER="your-twitter-code-verifier"
export TWITTER_USERNAME="your-twitter-username"

# OpenAI Credentials
export OPENAI_API_KEY="your-openai-api-key"

# RunwayML Credentials
export RUNWAY_API_KEY="your-runway-api-key"

# Jamendo Credentials (from previous setup)
export JAMENDO_CLIENT_ID="your-jamendo-client-id"
export JAMENDO_CLIENT_SECRET="your-jamendo-client-secret"

# Optional configurations
export OPENAI_MODEL="gpt-4"
export OPENAI_MAX_TOKENS="2000"
export OPENAI_TEMPERATURE="0.7"
export RUNWAY_BASE_URL="https://api.runwayml.com/v1"
export RUNWAY_TIMEOUT="300"
export FILE_UPLOAD_PATH="/tmp/videos"
export FILE_MAX_SIZE="1073741824"
export FILE_ALLOWED_EXTENSIONS="mp4,avi,mov,mkv,webm"

echo "✅ Environment variables set successfully!"
echo ""
echo "📋 Set Variables:"
echo "  YouTube: GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, GOOGLE_REFRESH_TOKEN, YOUTUBE_STREAM_KEY"
echo "  Twitter: TWITTER_API_KEY, TWITTER_API_SECRET, TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET"
echo "  OpenAI: OPENAI_API_KEY"
echo "  RunwayML: RUNWAY_API_KEY"
echo "  Jamendo: JAMENDO_CLIENT_ID, JAMENDO_CLIENT_SECRET"
echo ""
echo "🚀 To start services with these credentials, run:"
echo "  source setup-env.sh"
echo "  ./gradlew :services:ai-chat-service:bootRun"
echo "  ./gradlew :services:music-creator-service:bootRun"
echo "  ./gradlew :services:video-creator-service:bootRun"
echo "  ./gradlew :services:youtube-service:bootRun"
echo "  ./gradlew :services:twitter-service:bootRun"
echo "  ./gradlew :services:flow-runner:bootRun"
echo ""
echo "🧪 To test the setup, run:"
echo "  ./test-flows.sh" 