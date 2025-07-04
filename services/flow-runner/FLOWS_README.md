# FlowRunner - Tanımlı Flowlar Rehberi

Bu dokümantasyon, Content Organizer projesinde tanımlı olan tüm flowları ve bunların kullanımını açıklar.

## 📋 İçindekiler

- [Genel Bakış](#genel-bakış)
- [Flow Türleri](#flow-türleri)
- [Tanımlı Flowlar](#tanımlı-flowlar)
- [Flow Yapısı](#flow-yapısı)
- [Kullanım Örnekleri](#kullanım-örnekleri)
- [API Endpoints](#api-endpoints)
- [Monitoring ve Logging](#monitoring-ve-logging)

## 🎯 Genel Bakış

FlowRunner servisi, Content Organizer mikroservis mimarisinde iş akışlarını (workflow) yönetmek için tasarlanmıştır. Bu servis, farklı mikroservisler arasında koordinasyon sağlar ve otomatik içerik üretimi süreçlerini yönetir.

### Temel Özellikler

- **Otomatik Tetikleme**: Cron tabanlı zamanlanmış görevler
- **Manuel Tetikleme**: API üzerinden manuel çalıştırma
- **Retry Mekanizması**: Hata durumlarında otomatik yeniden deneme
- **Koşullu Akış**: Dinamik karar verme ve yönlendirme
- **Monitoring**: Gerçek zamanlı izleme ve loglama
- **Scalable**: Yüksek performanslı ve ölçeklenebilir mimari

## 🔄 Flow Türleri

### 1. Service Call (Servis Çağrısı)
Mikroservisler arası HTTP çağrıları yapar.

### 2. Condition (Koşul)
Akış içinde karar verme noktaları oluşturur.

### 3. Log (Loglama)
Bilgi, uyarı veya hata mesajları kaydeder.

### 4. Delay (Bekleme)
Belirtilen süre kadar bekler.

### 5. Transform (Dönüştürme)
JavaScript ile veri dönüştürme işlemleri yapar.

## 📁 Tanımlı Flowlar

### 1. Content Creation Flow (`content-creation-flow.yml`)

**Amaç**: AI ile içerik oluşturma, müzik bulma ve video oluşturma

**Tetikleme**: Her gün saat 10:00'da (CRON: `0 0 10 * * *`)

**Adımlar**:
1. **AI Content Generation**: AI Chat Service ile içerik oluşturma
2. **Music Search**: Music Creator Service ile arka plan müziği bulma
3. **Quality Check**: İçerik kalitesini kontrol etme
4. **Video Creation**: Video Creator Service ile video oluşturma
5. **Success/Error Logging**: Sonuçları loglama

**Kullanım Alanları**:
- Günlük içerik üretimi
- Sosyal medya paylaşımları
- Eğitim materyali oluşturma

**Özellikler**:
- Retry mekanizması (5 deneme)
- Koşullu akış (müzik bulunamazsa müziksiz devam)
- Global değişkenler ile özelleştirme

### 2. Daily Video Upload Flow (`daily-video-upload-flow.yml`)

**Amaç**: Günlük otomatik video oluşturma ve YouTube'a yükleme

**Tetikleme**: Her gün saat 09:00'da (CRON: `0 9 * * *`)

**Adımlar**:
1. **Music Prompt Generation**: AI ile müzik oluşturma prompt'u
2. **Main Music Creation**: 1 saatlik ana müzik oluşturma
3. **Background Music Creation**: Tamamlayıcı arka plan müziği
4. **Video Creation**: Müziklerle video oluşturma
5. **YouTube Metadata Generation**: Başlık ve açıklama oluşturma
6. **YouTube Upload**: Video'yu YouTube'a yükleme
7. **Tweet Generation**: Paylaşım tweet'i oluşturma

**Kullanım Alanları**:
- YouTube kanalı yönetimi
- Otomatik içerik üretimi
- Sosyal medya pazarlaması

**Özellikler**:
- 1 saatlik uzun içerik
- Yüksek kaliteli müzik üretimi
- Otomatik metadata oluşturma
- Sosyal medya entegrasyonu

### 3. Simple AI Music Flow (`simple-ai-music-flow.yml`)

**Amaç**: Basit AI içerik ve müzik eşleştirme

**Tetikleme**: Manuel

**Adımlar**:
1. **Story Generation**: AI ile kısa hikaye oluşturma
2. **Music Search**: Hikayeye uygun müzik bulma
3. **Result Logging**: Sonuçları loglama

**Kullanım Alanları**:
- Hızlı prototip oluşturma
- Test amaçlı kullanım
- Basit içerik üretimi

**Özellikler**:
- Minimal adım sayısı
- Hızlı çalışma süresi
- Kolay debug

### 4. Retry Example Flow (`retry-example-flow.yml`)

**Amaç**: Retry mekanizmalarını ve hata yönetimini gösterme

**Tetikleme**: Manuel

**Adımlar**:
1. **AI Service with Retry**: Retry ile AI servis çağrısı
2. **Delay**: 2 saniye bekleme
3. **Music Service**: Müzik servis çağrısı
4. **Success/Error Logging**: Sonuçları loglama

**Kullanım Alanları**:
- Hata yönetimi testleri
- Retry mekanizması öğrenme
- Sistem dayanıklılığı testleri

**Özellikler**:
- Farklı retry stratejileri
- Continue on failure
- Detaylı hata loglama

### 5. Video Stream Flow (`video-stream-flow.yml`)

**Amaç**: Canlı video stream oluşturma ve YouTube Live entegrasyonu

**Tetikleme**: Manuel

**Adımlar**:
1. **Stream Music Creation**: Canlı yayın müziği oluşturma
2. **Stream Video Creation**: 2 saatlik stream videosu
3. **YouTube Stream Start**: YouTube Live başlatma
4. **Tweet Announcement**: Canlı yayın duyurusu
5. **Stream End**: 2 saat sonra yayını sonlandırma
6. **End Tweet**: Teşekkür tweet'i

**Kullanım Alanları**:
- Canlı yayın yönetimi
- YouTube Live entegrasyonu
- Sosyal medya canlı etkinlikleri

**Özellikler**:
- 2 saatlik canlı yayın
- Otomatik başlatma/sonlandırma
- Gerçek zamanlı sosyal medya entegrasyonu

## 🏗️ Flow Yapısı

### Temel YAML Yapısı

```yaml
name: "flow-name"
description: "Flow açıklaması"
version: "1.0"
enabled: true

trigger:
  type: "CRON|MANUAL"
  cronExpression: "0 0 10 * * *"  # CRON için
  enabled: true

globalVariables:
  variable1: "value1"
  variable2: "value2"

defaultRetryPolicy:
  maxAttempts: 3
  strategy: "EXPONENTIAL_BACKOFF"
  initialDelayMs: 1000
  maxDelayMs: 30000
  backoffMultiplier: 2.0

steps:
  - id: "step-id"
    name: "Step Name"
    type: "SERVICE_CALL|CONDITION|LOG|DELAY|TRANSFORM"
    # Step-specific configuration
    nextStepId: "next-step-id"
```

### Step Türleri ve Konfigürasyonları

#### Service Call Step
```yaml
- id: "service-call-step"
  name: "Service Call"
  type: "SERVICE_CALL"
  serviceCall:
    serviceName: "ai-chat-service"
    method: "POST"
    endpoint: "/chat/quick"
    headers:
      Content-Type: "application/json"
    body:
      message: "Hello World"
  nextStepId: "next-step"
  retryPolicy:
    maxAttempts: 3
    strategy: "EXPONENTIAL_BACKOFF"
```

#### Condition Step
```yaml
- id: "condition-step"
  name: "Condition Check"
  type: "CONDITION"
  conditions:
    - field: "response.status"
      operator: "EQUALS"
      value: "200"
      nextStepIdOnTrue: "success-step"
      nextStepIdOnFalse: "error-step"
```

#### Log Step
```yaml
- id: "log-step"
  name: "Log Message"
  type: "LOG"
  variables:
    message: "Flow completed successfully!"
    level: "INFO"
```

#### Delay Step
```yaml
- id: "delay-step"
  name: "Wait 5 seconds"
  type: "DELAY"
  variables:
    delayMs: 5000
```

#### Transform Step
```yaml
- id: "transform-step"
  name: "Transform Data"
  type: "TRANSFORM"
  script: |
    const input = context.getVariable('input');
    const transformed = input.toUpperCase();
    context.setVariable('output', transformed);
```

## 🚀 Kullanım Örnekleri

### Flow Oluşturma

```bash
# Flow oluştur
curl -X POST http://localhost:8080/api/v1/flows \
  -H "Content-Type: application/json" \
  -d @content-creation-flow.yml
```

### Flow Çalıştırma

```bash
# Manuel flow çalıştırma
curl -X POST http://localhost:8080/api/v1/flows/flow-id/execute
```

### Flow Durumu Kontrolü

```bash
# Flow durumunu kontrol et
curl -X GET http://localhost:8080/api/v1/flows/flow-id

# Execution durumunu kontrol et
curl -X GET http://localhost:8080/api/v1/executions/execution-id
```

### Flow İstatistikleri

```bash
# Sistem istatistiklerini al
curl -X GET http://localhost:8080/api/v1/flows/statistics
```

## 📡 API Endpoints

### Flow Management
- `POST /api/v1/flows` - Flow oluşturma
- `GET /api/v1/flows` - Tüm flowları listeleme
- `GET /api/v1/flows/{id}` - Flow detayı
- `PUT /api/v1/flows/{id}` - Flow güncelleme
- `DELETE /api/v1/flows/{id}` - Flow silme
- `PATCH /api/v1/flows/{id}/enable` - Flow etkinleştirme
- `PATCH /api/v1/flows/{id}/disable` - Flow devre dışı bırakma

### Flow Execution
- `POST /api/v1/flows/{id}/execute` - Flow çalıştırma
- `GET /api/v1/flows/{id}/executions` - Flow execution'ları
- `GET /api/v1/executions` - Tüm execution'lar
- `GET /api/v1/executions/{id}` - Execution detayı
- `DELETE /api/v1/executions/{id}` - Execution silme

### Monitoring
- `GET /api/v1/flows/statistics` - Sistem istatistikleri
- `GET /api/v1/executions/recent` - Son execution'lar
- `GET /api/v1/executions?status=RUNNING` - Duruma göre filtreleme

## 📊 Monitoring ve Logging

### Execution Durumları
- **RUNNING**: Çalışıyor
- **COMPLETED**: Tamamlandı
- **FAILED**: Başarısız
- **CANCELLED**: İptal edildi

### Log Seviyeleri
- **INFO**: Bilgi mesajları
- **WARN**: Uyarı mesajları
- **ERROR**: Hata mesajları
- **DEBUG**: Debug mesajları

### Monitoring Metrikleri
- Toplam flow sayısı
- Çalışan execution sayısı
- Tamamlanan execution sayısı
- Başarısız execution sayısı
- Ortalama çalışma süresi

## 🔧 Konfigürasyon

### Environment Variables
```bash
# MongoDB Connection
MONGODB_URI=mongodb://localhost:27017/flowrunner

# Server Configuration
SERVER_PORT=8080
SERVER_HOST=0.0.0.0

# Logging
LOGGING_LEVEL=INFO
LOGGING_PATTERN=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# Retry Configuration
DEFAULT_RETRY_MAX_ATTEMPTS=3
DEFAULT_RETRY_INITIAL_DELAY=1000
DEFAULT_RETRY_MAX_DELAY=30000
```

### Docker Compose
```yaml
flow-runner:
  image: contentorganizer/flow-runner:latest
  ports:
    - "8080:8080"
  environment:
    - MONGODB_URI=mongodb://mongo:27017/flowrunner
  depends_on:
    - mongo
```

## 🛠️ Troubleshooting

### Yaygın Sorunlar

1. **Flow çalışmıyor**
   - Flow'un enabled olduğunu kontrol edin
   - Trigger konfigürasyonunu kontrol edin
   - Logları inceleyin

2. **Service call başarısız**
   - Servis URL'lerini kontrol edin
   - Network bağlantısını kontrol edin
   - Retry mekanizmasını kontrol edin

3. **MongoDB bağlantı hatası**
   - MongoDB servisinin çalıştığını kontrol edin
   - Connection string'i kontrol edin
   - Firewall ayarlarını kontrol edin

### Debug Komutları

```bash
# Flow loglarını görüntüle
docker logs flow-runner

# MongoDB'ye bağlan
docker exec -it mongo mongosh

# Flow collection'ını kontrol et
use flowrunner
db.flows.find()

# Execution collection'ını kontrol et
db.flow_executions.find()
```

## 📚 Ek Kaynaklar

- [FlowRunner API Documentation](../resources/api-docs/flow-runner-openapi.yaml)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [MongoDB Documentation](https://docs.mongodb.com/)
- [Cron Expression Generator](https://crontab.guru/)

## 🤝 Katkıda Bulunma

1. Flow önerilerinizi issue olarak açın
2. Yeni flow örnekleri ekleyin
3. Dokümantasyonu güncelleyin
4. Test coverage'ını artırın

---

**Son Güncelleme**: 2024-12-19
**Versiyon**: 1.0.0
**Yazar**: Content Organizer Team 