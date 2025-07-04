# FlowRunner Service - Defined Flows Documentation

## Overview

The FlowRunner service orchestrates complex workflows by coordinating multiple microservices through YAML-based flow definitions. This document provides comprehensive documentation for all defined flows in the system.

## Flow Types and Categories

### 1. Content Creation Flows
Flows designed for automated content generation using AI services.

### 2. Video Processing Flows  
Flows for video creation, processing, and distribution.

### 3. Music Generation Flows
Flows for creating and managing background music.

### 4. Social Media Flows
Flows for automated social media content posting and management.

### 5. Utility Flows
Flows for testing, retry mechanisms, and system maintenance.

---

## Defined Flows

### 1. Content Creation Flow
**File:** `content-creation-flow.yml`  
**Purpose:** Generate AI content, find background music, and create videos automatically.

**Trigger:** Daily at 10:00 AM (CRON: `0 0 10 * * *`)

**Steps:**
1. **Generate AI Content** - Creates comprehensive articles using AI
2. **Find Background Music** - Searches for ambient electronic music
3. **Check Content Quality** - Validates generated content and music
4. **Create Video** - Generates video with AI content and music
5. **Log Success/Error** - Records execution results

**Key Features:**
- Conditional branching based on content quality
- Fallback video creation without music
- Comprehensive error handling
- Configurable retry policies

**Usage:**
```bash
# Execute manually
curl -X POST http://localhost:8084/api/v1/flow-runner/executions/content-creation-flow/execute

# Check status
curl http://localhost:8084/api/v1/flow-runner/executions/running
```

---

### 2. Daily Video Upload Flow
**File:** `daily-video-upload-flow.yml`  
**Purpose:** Automated daily video creation and upload to YouTube with AI-generated content.

**Trigger:** Daily at 9:00 AM (CRON: `0 9 * * *`)

**Steps:**
1. **Generate Music Creation Prompt** - AI creates detailed music generation prompts
2. **Create Main Music** - Generates 1-hour background music
3. **Generate Background Prompt** - Creates complementary background music prompts
4. **Create Background Music** - Generates atmospheric background tracks
5. **Create Combined Video** - Combines audio tracks with visual effects
6. **Generate YouTube Metadata** - Creates engaging titles and descriptions
7. **Parse Metadata** - Extracts and formats YouTube metadata
8. **Upload to YouTube** - Publishes video to YouTube
9. **Generate Tweet** - Creates social media announcement

**Key Features:**
- 1-hour music generation
- Multiple audio track combination
- Visual effects (waveform, particles)
- Automated YouTube upload
- Social media integration

**Configuration:**
- Video resolution: 1920x1080
- Frame rate: 30 FPS
- Duration: 1 hour
- Output format: MP4

---

### 3. Video Stream Flow
**File:** `video-stream-flow.yml`  
**Purpose:** Real-time video streaming and processing workflow.

**Trigger:** Manual execution or event-driven

**Steps:**
1. **Initialize Stream** - Sets up video streaming infrastructure
2. **Process Video Feed** - Real-time video processing
3. **Apply Filters** - Video enhancement and filtering
4. **Stream Output** - Broadcasts processed video
5. **Monitor Quality** - Quality assurance checks
6. **Archive Content** - Stores processed content

**Key Features:**
- Real-time processing
- Quality monitoring
- Content archiving
- Performance optimization

---

### 4. Simple AI Music Flow
**File:** `simple-ai-music-flow.yml`  
**Purpose:** Quick music generation using AI services.

**Trigger:** On-demand execution

**Steps:**
1. **Generate Music Prompt** - AI creates music generation instructions
2. **Create Music** - Generates music based on prompt
3. **Validate Output** - Checks music quality and format
4. **Store Result** - Saves generated music

**Key Features:**
- Simple 2-step process
- Quick execution
- Basic validation
- Result storage

---

### 5. Retry Example Flow
**File:** `retry-example-flow.yml`  
**Purpose:** Demonstrates retry mechanisms and error handling.

**Trigger:** Manual execution

**Steps:**
1. **Attempt Operation** - Performs operation that may fail
2. **Handle Failure** - Implements retry logic
3. **Log Attempts** - Records retry attempts
4. **Final Result** - Returns success or failure

**Key Features:**
- Multiple retry strategies
- Exponential backoff
- Failure logging
- Success tracking

---

## Flow Configuration

### Global Variables
Each flow can define global variables that are available throughout the execution:

```yaml
globalVariables:
  contentTopic: "technology trends 2024"
  musicGenre: "ambient electronic"
  outputFormat: "mp4"
  apiKey: "${API_KEY}"
```

### Retry Policies
Configurable retry mechanisms for failed steps:

```yaml
defaultRetryPolicy:
  maxAttempts: 3
  strategy: EXPONENTIAL_BACKOFF
  initialDelayMs: 1000
  maxDelayMs: 30000
  backoffMultiplier: 2.0
```

**Available Strategies:**
- `FIXED_DELAY` - Constant delay between retries
- `EXPONENTIAL_BACKOFF` - Increasing delay with multiplier
- `LINEAR_BACKOFF` - Linear increase in delay
- `RANDOM_DELAY` - Random delay within range
- `FIBONACCI_BACKOFF` - Fibonacci sequence delays
- `NO_RETRY` - No retry attempts

### Triggers
Flows can be triggered by different mechanisms:

```yaml
trigger:
  type: CRON
  cronExpression: "0 0 10 * * *"  # Daily at 10 AM
  enabled: true
```

**Trigger Types:**
- `CRON` - Scheduled execution using cron expressions
- `MANUAL` - Manual execution via API
- `EVENT` - Event-driven execution
- `WEBHOOK` - HTTP webhook triggers

---

## Step Types

### 1. SERVICE_CALL
Execute HTTP calls to microservices:

```yaml
type: SERVICE_CALL
serviceCall:
  serviceName: "ai-chat-service"
  method: "POST"
  endpoint: "/chat/quick"
  headers:
    Content-Type: "application/json"
  body:
    message: "Generate content about ${topic}"
    maxTokens: 500
```

### 2. CONDITION
Conditional branching based on response data:

```yaml
type: CONDITION
conditions:
  - field: "response.status"
    operator: EQUALS
    expectedValue: "success"
    nextStepIdOnTrue: "success-step"
    nextStepIdOnFalse: "error-step"
```

**Available Operators:**
- `EQUALS` - Exact match
- `NOT_EQUALS` - Not equal
- `GREATER_THAN` - Numeric comparison
- `LESS_THAN` - Numeric comparison
- `CONTAINS` - String contains
- `NOT_EMPTY` - Non-empty value
- `IS_NULL` - Null check
- `IS_NOT_NULL` - Non-null check

### 3. DELAY
Add delays between steps:

```yaml
type: DELAY
variables:
  delayMs: 5000  # 5 seconds
```

### 4. LOG
Log messages with context:

```yaml
type: LOG
variables:
  message: "Processing completed for ${userId}"
```

### 5. TRANSFORM
Transform data using scripts:

```yaml
type: TRANSFORM
variables:
  newField: "${existingField}_transformed"
  timestamp: "${currentTime}"
```

---

## API Endpoints

### Flow Management
```
POST   /api/v1/flow-runner/flows          # Create flow from YAML
GET    /api/v1/flow-runner/flows          # List all flows
GET    /api/v1/flow-runner/flows/{id}     # Get flow by ID
PUT    /api/v1/flow-runner/flows/{id}     # Update flow
DELETE /api/v1/flow-runner/flows/{id}     # Delete flow
POST   /api/v1/flow-runner/flows/{id}/toggle # Enable/disable flow
```

### Flow Execution
```
POST   /api/v1/flow-runner/executions/{flowId}/execute    # Execute flow
GET    /api/v1/flow-runner/executions                     # List executions
GET    /api/v1/flow-runner/executions/{id}                # Get execution details
POST   /api/v1/flow-runner/executions/{id}/cancel         # Cancel execution
GET    /api/v1/flow-runner/executions/running             # Get running executions
```

### Scheduling
```
POST   /api/v1/flow-runner/scheduler/schedule/{flowId}    # Schedule flow
POST   /api/v1/flow-runner/scheduler/unschedule/{flowId}  # Unschedule flow
POST   /api/v1/flow-runner/scheduler/trigger/{flowId}     # Trigger flow manually
GET    /api/v1/flow-runner/scheduler/jobs                 # List scheduled jobs
```

---

## Monitoring and Debugging

### Health Check
```bash
curl http://localhost:8084/api/v1/flow-runner/health
```

### Execution Monitoring
```bash
# List all executions
curl http://localhost:8084/api/v1/flow-runner/executions

# Get specific execution
curl http://localhost:8084/api/v1/flow-runner/executions/{executionId}

# Get running executions
curl http://localhost:8084/api/v1/flow-runner/executions/running
```

### Flow Statistics
```bash
# Get flow statistics
curl http://localhost:8084/api/v1/flow-runner/flows/statistics
```

### Logs
- Application logs: `/app/logs/flow-runner.log`
- Execution logs: Stored in execution records
- Flow definitions: `/app/flows/`

---

## Best Practices

### 1. Flow Design
- Keep flows focused on a single responsibility
- Use meaningful step names and descriptions
- Implement proper error handling and retry logic
- Test flows thoroughly before production deployment

### 2. Performance
- Use appropriate timeouts for long-running operations
- Implement parallel execution where possible
- Monitor resource usage and optimize accordingly
- Use caching for frequently accessed data

### 3. Security
- Validate all input data
- Use secure communication between services
- Implement proper authentication and authorization
- Log security-relevant events

### 4. Monitoring
- Set up comprehensive logging
- Monitor flow execution metrics
- Implement alerting for failures
- Track performance bottlenecks

---

## Troubleshooting

### Common Issues

1. **Flow Execution Fails**
   - Check service availability
   - Verify API endpoints and authentication
   - Review error logs for specific failure reasons
   - Validate input data and parameters

2. **Scheduled Flows Not Running**
   - Verify cron expressions are correct
   - Check if flows are enabled
   - Ensure scheduler service is running
   - Review system time and timezone settings

3. **Performance Issues**
   - Monitor resource usage
   - Check for bottlenecks in service calls
   - Optimize retry policies and timeouts
   - Consider parallel execution where appropriate

### Debug Commands
```bash
# Check flow status
curl http://localhost:8084/api/v1/flow-runner/flows

# View execution history
curl http://localhost:8084/api/v1/flow-runner/executions

# Check scheduler status
curl http://localhost:8084/api/v1/flow-runner/scheduler/jobs
```

---

## Configuration

### Application Properties
```yaml
# Service Registry
services:
  ai-chat-service:
    base-url: http://localhost:8083/api/v1/aichat
  music-creator-service:
    base-url: http://localhost:8082/api/v1/music
  video-creator-service:
    base-url: http://localhost:8081/api/v1/runml

# Flow Runner Configuration
flow-runner:
  execution:
    default-timeout-seconds: 300
    max-concurrent-executions: 10
    enable-retry: true
    default-retry-attempts: 3
  scheduler:
    enabled: true
    auto-start: true
  storage:
    yaml-directory: ./flows
    backup-enabled: true
    backup-directory: ./backups
```

---

## Contributing

When adding new flows:

1. **Follow Naming Conventions**
   - Use descriptive names with hyphens
   - Include version numbers
   - Add appropriate tags and categories

2. **Documentation**
   - Update this documentation
   - Include usage examples
   - Document configuration options
   - Add troubleshooting notes

3. **Testing**
   - Create comprehensive tests
   - Test error scenarios
   - Validate performance impact
   - Test with different configurations

4. **Review Process**
   - Code review for new flows
   - Security review for external integrations
   - Performance review for resource usage
   - Documentation review for completeness

---

## Support

For support and questions:
- Create an issue in the repository
- Check the documentation
- Review example flows in the `/examples` directory
- Contact the development team

---

*Last updated: December 2024*
*Version: 1.0.0* 