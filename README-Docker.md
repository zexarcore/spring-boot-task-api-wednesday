# 🐳 TaskAPI - Docker Setup

Esta aplicación Spring Boot con CRUD completo está dockerizada junto con MySQL para facilitar el despliegue y desarrollo.

## 🚀 Inicio Rápido

### Prerrequisitos
- Docker
- Docker Compose

### Ejecutar la aplicación completa

```bash
docker-compose down --rmi all

docker system prune -f

docker-compose build --no-cache
docker compose up
docker-compose logs -f

# Construir y ejecutar todos los servicios
docker-compose up --build

# Ejecutar en segundo plano
docker-compose up -d --build
```

### Acceder a los servicios

- **API REST**: http://localhost:8082
- **phpMyAdmin**: http://localhost:8081
- **MySQL**: localhost:3306

## 📋 Servicios Incluidos

### 1. **MySQL Database** (`mysql`)
- **Imagen**: mysql:8.0
- **Puerto**: 3306
- **Base de datos**: taskdb
- **Usuario**: taskuser
- **Contraseña**: taskpassword
- **Root password**: rootpassword

### 2. **Spring Boot App** (`app`)
- **Puerto**: 8082 (externo) -> 8080 (interno)
- **Health check**: http://localhost:8082/actuator/health
- **Depende de**: MySQL (espera a que esté saludable)

### 3. **phpMyAdmin** (`phpmyadmin`)
- **Puerto**: 8081
- **Acceso**: http://localhost:8081
- **Usuario**: root / taskuser
- **Contraseña**: rootpassword / taskpassword

## 🔧 Comandos Útiles

### Gestión de contenedores
```bash
# Ver logs
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f app
docker-compose logs -f mysql

# Detener servicios
docker-compose down

# Detener y eliminar volúmenes
docker-compose down -v

# Reconstruir solo la app
docker-compose up --build app

# Ejecutar comandos en contenedores
docker-compose exec app bash
docker-compose exec mysql mysql -u root -p
```

### Desarrollo
```bash
# Reconstruir solo cuando cambies código
docker-compose up --build app

# Ver estado de servicios
docker-compose ps

# Ver health checks
docker-compose ps --format "table {{.Name}}\t{{.Status}}\t{{.Ports}}"
```

## 🧪 Probar la API

### Crear una tarea
```bash
curl -X POST http://localhost:8082/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mi primera tarea",
    "description": "Esta es una tarea de prueba",
    "status": false
  }'
```

### Obtener todas las tareas
```bash
curl http://localhost:8082/api/v1/tasks
```

### Obtener tarea por ID
```bash
curl http://localhost:8082/api/v1/tasks/1
```

### Actualizar tarea
```bash
curl -X PUT http://localhost:8082/api/v1/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Tarea actualizada",
    "description": "Descripción actualizada",
    "status": true
  }'
```

### Eliminar tarea
```bash
curl -X DELETE http://localhost:8082/api/v1/tasks/1
```

## 🔍 Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/tasks` | Crear tarea |
| GET | `/api/v1/tasks` | Obtener todas las tareas |
| GET | `/api/v1/tasks/{id}` | Obtener tarea por ID |
| GET | `/api/v1/tasks/status/{status}` | Obtener tareas por estado |
| GET | `/api/v1/tasks/search/name?name=...` | Buscar por nombre |
| GET | `/api/v1/tasks/search/keyword?keyword=...` | Buscar por palabra clave |
| GET | `/api/v1/tasks/count/status/{status}` | Contar tareas por estado |
| PUT | `/api/v1/tasks/{id}` | Actualizar tarea completa |
| PATCH | `/api/v1/tasks/{id}/status?status=...` | Actualizar solo estado |
| DELETE | `/api/v1/tasks/{id}` | Eliminar tarea |
| DELETE | `/api/v1/tasks` | Eliminar todas las tareas |

## 🗄️ Base de Datos

### Estructura de la tabla `tasks`
```sql
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    status BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);
```

### Conectar directamente a MySQL
```bash
# Usando Docker
docker-compose exec mysql mysql -u taskuser -p taskdb

# Usando cliente externo
mysql -h localhost -P 3306 -u taskuser -p taskdb
```

## 🐛 Troubleshooting

### Problemas comunes

1. **Puerto 3306 ya en uso**
   ```bash
   # Cambiar puerto en docker-compose.yml
   ports:
     - "3307:3306"  # Cambiar 3306 por 3307
   ```

2. **App no puede conectar a MySQL**
   ```bash
   # Verificar que MySQL esté saludable
   docker-compose ps
   
   # Ver logs de MySQL
   docker-compose logs mysql
   ```

3. **Reconstruir desde cero**
   ```bash
   docker-compose down -v
   docker-compose up --build
   ```

### Logs útiles
```bash
# Ver todos los logs
docker-compose logs -f

# Ver logs de la app
docker-compose logs -f app

# Ver logs de MySQL
docker-compose logs -f mysql
```

## 📁 Estructura del Proyecto

```
taskapi/
├── Dockerfile                 # Imagen de la aplicación
├── docker-compose.yml         # Orquestación de servicios
├── .dockerignore             # Archivos a ignorar en Docker
├── README-Docker.md          # Esta documentación
├── pom.xml                   # Dependencias Maven
└── src/
    └── main/
        ├── java/
        │   └── com/taskapi/taskapi/
        │       ├── TaskapiApplication.java
        │       └── tasks/
        │           ├── controller/TaskController.java
        │           ├── domain/
        │           │   ├── models/entity/Task.java
        │           │   └── repository/ITaskRepository.java
        │           └── services/TaskService.java
        └── resources/
            └── application.properties
```

¡Tu aplicación CRUD con MySQL está completamente dockerizada y lista para usar! 🎉
