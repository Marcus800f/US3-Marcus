CREATE DATABASE IF NOT EXISTS école;
USE école;


CREATE TABLE etudiants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    moyenne DOUBLE,
    avis VARCHAR(20)
);

CREATE TABLE notations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_etudiant INT,
    note DOUBLE,
    coef INT,
    FOREIGN KEY (id_etudiant) REFERENCES etudiants(id)
);
