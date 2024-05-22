# Final_Boss_Spring

## Descripción

Este proyecto es una aplicación Spring Boot que interactúa con varias APIs de la NASA para proporcionar información y datos sobre imágenes astronómicas, rovers de Marte, asteroides cercanos y vistas de la Tierra desde el espacio.

## Cosas Importantes 

Asegúrarse de que el archivo application.properties contenga las siguientes configuraciones para que la aplicación se ejecute correctamente en el puerto 8080 y con la ruta de contexto /api:
Después de iniciar la aplicación, puedes acceder a los diferentes endpoints para interactuar con las APIs de la NASA. Por ejemplo, para obtener la imagen astronómica del día, accede a http://localhost:8080/api/apod.

# application.properties
server.port=8080
server.servlet.context-path=/api
spring.application.name=Final_Boss_Spring
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration



### Configuración
- `config/`: Archivos de configuración globales accesibles por diferentes clases.
  - `ApplicationConfig`: Configuraciones generales de la aplicación.
  - `NasaApiConfig`: Configuraciones específicas para interactuar con las APIs de la NASA.

### Controladores
- `controller/`: Contiene los controladores para las diferentes opciones.
  - `ApodController`: Endpoints para obtener la imagen astronómica del día.
    - `obtenerImagenDelDia()`: Obtiene la imagen del día.
  - `GalleryController`: Endpoints para buscar en la galería de imágenes de la NASA.
    - `buscarGaleriaPorDefecto()`: Busca la galería por defecto.
    - `buscarGaleria()`: Busca la galería con parámetros específicos.
  - `MarsRoverController`: Endpoints para acceder a fotos y manifiestos de los rovers de Marte.
    - `getMarsRoverPhotos()`: Obtiene fotos de los rovers por parámetros.
    - `getMissionManifest()`: Obtiene los manifiestos de misión.
  - `NeowsController`: Endpoints para obtener información sobre asteroides cercanos.
    - `obtenerAsteroidesCercanos()`: Obtiene asteroides cercanos por fechas.
    - `obtenerAsteroidePorId()`: Obtiene información de un asteroide por su ID.
  - `TierraDesdeEspacioController`: Endpoints para obtener vistas de la Tierra desde el espacio.
    - `getTierraDesdeEspacio()`: Obtiene vistas basadas en latitud, longitud y fecha.

### Excepciones
- `exception/`: Contiene excepciones personalizadas para manejar errores específicos.
  - `DataNotFoundException`: Excepción lanzada cuando los datos no se encuentran.
  - `MyServerErrorException`: Excepción lanzada cuando ocurre un error en el servidor.

### Modelos
- `model/`: Modelos de datos (DTOs) para representar los objetos.
  - `ApodData`: Representa los datos de la imagen astronómica del día.
  - `GalleryData`: Representa los datos de la galería de imágenes de la NASA.
  - `MarsRover`: Representa los datos de los rovers de Marte.
  - `MissionManifest`: Representa los datos de los manifiestos de misión.
  - `NeoWsData`: Representa los datos de los asteroides cercanos.
  - `NeoWsNeoData`: Representa los datos específicos de un asteroide.
  - `TierraDesdeEspacio`: Representa los datos de las vistas de la Tierra desde el espacio.

### Servicios
- `service/`: Contiene los servicios con sus respectivos métodos para interactuar con las APIs.
  - `ApodService`: Métodos para obtener datos de la imagen astronómica del día.
  - `GalleryService`: Métodos para buscar en la galería de imágenes.
  - `MarsRoverService`: Métodos para obtener fotos y manifiestos de los rovers de Marte.
  - `NeoWsService`: Métodos para obtener información sobre asteroides cercanos.
  - `TierraDesdeEspacioService`: Métodos para obtener vistas de la Tierra desde el espacio.

## Requisitos previos
- JDK 11 o superior
- Gradle 6.0+ (si usas Gradle) o Maven 3.6+ (si usas Maven)





Clona el repositorio:



