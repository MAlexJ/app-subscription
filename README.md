### Subscription service

### Description

Subscription service

#### Projects:

* Message templates service: https://github.com/MAlexJ/app-message-templates
* Subscription service: https://github.com/MAlexJ/app-subscription
* Telegram bot service: https://github.com/MAlexJ/springboot-telegram-bot
* Rss service:
* Publisher service:

### Springboot project setup

create .env file with properties:

```
SERVICE_MONGODB_DATABASE=app-rss-db
SERVICE_MONGODB_URI=uri_to_mongo_db
SERVICE_JWT_SECRET_KEY=secred_key
```

### Gradle Versions Plugin

Displays a report of the project dependencies that are up-to-date, exceed the latest version found, have upgrades, or
failed to be resolved, info: https://github.com/ben-manes/gradle-versions-plugin

command:

```
gradle dependencyUpdates
```

