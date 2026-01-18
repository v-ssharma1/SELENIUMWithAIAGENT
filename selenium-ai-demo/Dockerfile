# Use Maven with OpenJDK 17
FROM maven:3.9.6-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy project files
COPY . /app

# Build the project and run tests using TestNG config
RUN mvn clean install
CMD ["mvn", "test", "-DsuiteXmlFile=testng.xml"]
