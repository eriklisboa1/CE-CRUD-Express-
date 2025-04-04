package org.CrudGeneratorIA.generation;


import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

public class CodeGenerator {

    public static String generatePomXml(String projectName, String dbType) {
        String dbDependency;
        if ("PostgreSQL".equalsIgnoreCase(dbType)) {
            dbDependency = """
                <dependency>
                    <groupId>org.postgresql</groupId>
                    <artifactId>postgresql</artifactId>
                    <scope>runtime</scope>
                </dependency>
                """;
        } else {
            dbDependency = """
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <scope>runtime</scope>
                </dependency>
                """;
        }

        String pomTemplate = """
            <project xmlns="http://maven.apache.org/POM/4.0.0"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                     http://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <groupId>org.example</groupId>
                <artifactId>%s</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <parent>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-parent</artifactId>
                    <version>2.7.4</version>
                    <relativePath/>
                </parent>
                <dependencies>
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-web</artifactId>
                    </dependency>
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-data-jpa</artifactId>
                    </dependency>
                    %s
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-test</artifactId>
                        <scope>test</scope>
                    </dependency>
                </dependencies>
                <build>
                    <plugins>
                        <plugin>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-maven-plugin</artifactId>
                        </plugin>
                    </plugins>
                </build>
            </project>
            """;
        return String.format(pomTemplate, projectName.toLowerCase(), dbDependency);
    }

    public static String generateApplicationProperties(String dbType, String dbName, String dbPassword) {
        String url;
        String username;
        if ("PostgreSQL".equalsIgnoreCase(dbType)) {
            url = "jdbc:postgresql://localhost:5432/" + dbName;
            username = "postgres";
        } else {
            url = "jdbc:mysql://localhost:3306/" + dbName;
            username = "root";
        }
        return String.format("""
            spring.datasource.url=%s
            spring.datasource.username=%s
            spring.datasource.password=%s
            spring.jpa.hibernate.ddl-auto=update
            spring.jpa.show-sql=true
            """, url, username, dbPassword);
    }

    public static String generateMainApplicationClass(String projectPackage, String projectName) {
        return String.format("""
            package org.example.%s;
            
            import org.springframework.boot.SpringApplication;
            import org.springframework.boot.autoconfigure.SpringBootApplication;
            
            @SpringBootApplication
            public class %sApplication {
                public static void main(String[] args) {
                    SpringApplication.run(%sApplication.class, args);
                }
            }
            """, projectPackage, projectName, projectName);
    }

    public static String generateModelClass(String projectPackage, String entityName, List<String[]> attributes) {
        StringBuilder fields = new StringBuilder();
        StringBuilder gettersSetters = new StringBuilder();

        fields.append("    @Id\n    @GeneratedValue(strategy = GenerationType.IDENTITY)\n    private Long id;\n\n");
        gettersSetters.append("""
                public Long getId() {
                    return id;
                }
                public void setId(Long id) {
                    this.id = id;
                }
                """);

        for (String[] attr : attributes) {
            String name = attr[0];
            String type = attr[1];
            fields.append("    private ").append(type).append(" ").append(name).append(";\n\n");
            gettersSetters.append("    public ").append(type).append(" get").append(capitalize(name))
                    .append("() {\n        return ").append(name).append(";\n    }\n\n");
            gettersSetters.append("    public void set").append(capitalize(name)).append("(")
                    .append(type).append(" ").append(name).append(") {\n")
                    .append("        this.").append(name).append(" = ").append(name).append(";\n    }\n\n");
        }

        return "package org.example." + projectPackage + ".model;\n\n" +
                "import javax.persistence.*;\n\n" +
                "@Entity\n" +
                "public class " + entityName + " {\n\n" +
                fields.toString() +
                gettersSetters.toString() +
                "}\n";
    }

    public static String generateRepositoryInterface(String projectPackage, String entityName) {
        return "package org.example." + projectPackage + ".repository;\n\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n" +
                "import org.example." + projectPackage + ".model." + entityName + ";\n\n" +
                "public interface " + entityName + "Repository extends JpaRepository<" + entityName + ", Long> {\n" +
                "}\n";
    }

    public static String generateServiceClass(String projectPackage, String entityName) {
        return String.format("""
            package org.example.%s.service;
            
            import org.springframework.stereotype.Service;
            import org.example.%s.model.%s;
            import org.example.%s.repository.%sRepository;
            import java.util.List;
            
            @Service
            public class %sService {
            
                private final %sRepository repository;
            
                public %sService(%sRepository repository) {
                    this.repository = repository;
                }
            
                public List<%s> findAll() {
                    return repository.findAll();
                }
            
                public %s save(%s entity) {
                    return repository.save(entity);
                }
            
                public void delete(Long id) {
                    repository.deleteById(id);
                }
            }
            """,
                projectPackage,
                projectPackage, entityName,
                projectPackage, entityName,
                entityName,
                entityName,
                entityName, entityName,
                entityName,
                entityName, entityName);
    }

    public static String generateControllerClass(String projectPackage, String entityName) {
        return "package org.example." + projectPackage + ".controller;\n\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import org.example." + projectPackage + ".model." + entityName + ";\n" +
                "import org.example." + projectPackage + ".service." + entityName + "Service;\n" +
                "import java.util.List;\n\n" +
                "@RestController\n" +
                "@RequestMapping(\"/api/" + entityName.toLowerCase() + "s\")\n" +
                "public class " + entityName + "Controller {\n\n" +
                "    @Autowired\n" +
                "    private " + entityName + "Service service;\n\n" +
                "    @GetMapping\n" +
                "    public List<" + entityName + "> getAll() {\n" +
                "        return service.findAll();\n" +
                "    }\n\n" +
                "    @PostMapping\n" +
                "    public " + entityName + " create(@RequestBody " + entityName + " entity) {\n" +
                "        return service.save(entity);\n" +
                "    }\n\n" +
                "    @PutMapping(\"/{id}\")\n" +
                "    public " + entityName + " update(@PathVariable Long id, @RequestBody " + entityName + " entity) {\n" +
                "        entity.setId(id);\n" +
                "        return service.save(entity);\n" +
                "    }\n\n" +
                "    @DeleteMapping(\"/{id}\")\n" +
                "    public void delete(@PathVariable Long id) {\n" +
                "        service.delete(id);\n" +
                "    }\n" +
                "}\n";
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    // Método auxiliar para escrita de arquivos (se desejado)
    public static void writeFile(Path path, String content) throws IOException {
        Files.writeString(path, content);
    }
}
