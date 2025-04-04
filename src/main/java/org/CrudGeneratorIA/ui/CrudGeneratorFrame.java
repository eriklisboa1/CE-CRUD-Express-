package org.CrudGeneratorIA.ui;

import org.CrudGeneratorIA.generation.CodeGenerator;
import org.CrudGeneratorIA.model.EntityDefinition;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CrudGeneratorFrame extends JFrame {

    private JTextField tfEntityName;
    private JTextArea taAttributes;
    private JComboBox<String> cbDatabase;
    private JTextField tfDbName;
    private JPasswordField tfDbPassword;
    private JButton btnAddClass;
    private JButton btnGenerate;

    private List<EntityDefinition> entityList = new ArrayList<>();

    public CrudGeneratorFrame() {

        super("CE (CRUD Express)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setNimbusLookAndFeel();
        initComponents();
        pack();
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
    }

    private void setNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {

        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Cabeçalho com o título "CE (CRUD Express)" centralizado
        JLabel headerLabel = new JLabel("CE (CRUD Express)", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(new JLabel("Nome da Entidade:"), gbc);

        tfEntityName = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(tfEntityName, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(new JLabel("Atributos (ex: nome:String, preco:Double):"), gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        taAttributes = new JTextArea(10, 30);
        taAttributes.setLineWrap(true);
        taAttributes.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(taAttributes);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        formPanel.add(scrollPane, gbc);


        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Tipo de Banco de Dados:"), gbc);
        cbDatabase = new JComboBox<>(new String[]{"PostgreSQL", "MySQL"});
        gbc.gridx = 1;
        formPanel.add(cbDatabase, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Nome do Banco de Dados:"), gbc);
        tfDbName = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(tfDbName, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Senha do Banco de Dados:"), gbc);
        tfDbPassword = new JPasswordField(20);
        gbc.gridx = 1;
        formPanel.add(tfDbPassword, gbc);


        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnAddClass = new JButton("Adicionar Classe");
        btnAddClass.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerate = new JButton("Gerar Projeto");
        btnGenerate.setFont(new Font("Arial", Font.BOLD, 14));
        btnPanel.add(btnAddClass);
        btnPanel.add(btnGenerate);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        btnAddClass.addActionListener(e -> addEntity());
        btnGenerate.addActionListener(e -> generateProject());
    }

    private void addEntity() {
        String entityName = tfEntityName.getText().trim();
        String attributesInput = taAttributes.getText().trim();

        if (entityName.isEmpty() || attributesInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome e os atributos da classe!");
            return;
        }

        List<String[]> attributes = new ArrayList<>();
        String[] attrArray = attributesInput.split(",");
        for (String attr : attrArray) {
            String[] parts = attr.split(":");
            if (parts.length == 2) {
                String attrName = parts[0].trim();
                String attrType = parts[1].trim();
                attributes.add(new String[]{attrName, attrType});
            }
        }

        entityList.add(new EntityDefinition(entityName, attributes));
        JOptionPane.showMessageDialog(this, "Classe '" + entityName + "' adicionada com sucesso!");
        tfEntityName.setText("");
        taAttributes.setText("");
    }

    private void generateProject() {
        if (!tfEntityName.getText().trim().isEmpty() || !taAttributes.getText().trim().isEmpty()) {
            int opcao = JOptionPane.showConfirmDialog(this, "Deseja adicionar a classe atual antes de gerar o projeto?", "Atenção", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                addEntity();
            }
        }

        if (entityList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma classe foi adicionada!");
            return;
        }

        String dbType = (String) cbDatabase.getSelectedItem();
        String dbName = tfDbName.getText().trim();
        String dbPassword = new String(tfDbPassword.getPassword());
        if (dbName.isEmpty() || dbPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os dados do banco de dados!");
            return;
        }

        String projectName = (entityList.size() > 1) ? "MultiCrudProject" : entityList.get(0).getEntityName() + "CrudProject";
        String projectPackage = projectName.toLowerCase();

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File projectDir = chooser.getSelectedFile();
        File baseDir = new File(projectDir, projectName);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        try {
            Path srcMainJava = Paths.get(baseDir.getAbsolutePath(), "src", "main", "java", "org", "example", projectPackage);
            Files.createDirectories(srcMainJava);
            Path modelDir = Files.createDirectories(srcMainJava.resolve("model"));
            Path repositoryDir = Files.createDirectories(srcMainJava.resolve("repository"));
            Path serviceDir = Files.createDirectories(srcMainJava.resolve("service"));
            Path controllerDir = Files.createDirectories(srcMainJava.resolve("controller"));
            Path resourcesDir = Paths.get(baseDir.getAbsolutePath(), "src", "main", "resources");
            Files.createDirectories(resourcesDir);

            String pomContent = CodeGenerator.generatePomXml(projectName, dbType);
            Files.writeString(Paths.get(baseDir.getAbsolutePath(), "pom.xml"), pomContent);
            String propertiesContent = CodeGenerator.generateApplicationProperties(dbType, dbName, dbPassword);
            Files.writeString(resourcesDir.resolve("application.properties"), propertiesContent);

            String mainAppContent = CodeGenerator.generateMainApplicationClass(projectPackage, projectName);
            Files.writeString(srcMainJava.resolve(projectName + "Application.java"), mainAppContent);

            for (EntityDefinition entity : entityList) {
                String modelContent = CodeGenerator.generateModelClass(projectPackage, entity.getEntityName(), entity.getAttributes());
                Files.writeString(modelDir.resolve(entity.getEntityName() + ".java"), modelContent);

                String repositoryContent = CodeGenerator.generateRepositoryInterface(projectPackage, entity.getEntityName());
                Files.writeString(repositoryDir.resolve(entity.getEntityName() + "Repository.java"), repositoryContent);

                String serviceContent = CodeGenerator.generateServiceClass(projectPackage, entity.getEntityName());
                Files.writeString(serviceDir.resolve(entity.getEntityName() + "Service.java"), serviceContent);

                String controllerContent = CodeGenerator.generateControllerClass(projectPackage, entity.getEntityName());
                Files.writeString(controllerDir.resolve(entity.getEntityName() + "Controller.java"), controllerContent);
            }

            JOptionPane.showMessageDialog(this, "Projeto gerado com sucesso em:\n" + baseDir.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao gerar o projeto: " + ex.getMessage());
        }
    }
}
