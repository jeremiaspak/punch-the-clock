DROP TABLE IF EXISTS user CASCADE;
DROP TABLE IF EXISTS time_registry;

CREATE TABLE user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  cpf VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  created_at DATETIME NOT NULL
);

CREATE TABLE time_registry (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  type VARCHAR(250) NOT NULL,
  created_at DATETIME NOT NULL,
  FOREIGN KEY (user_id) references user(id)
);

INSERT INTO user (name, cpf, email, created_at) VALUES
  ('João', '12345678901', 'joao@punchtheclock.com', PARSEDATETIME('01-01-2020 09:25:32.00', 'dd-MM-yyyy hh:mm:ss.SS')),
  ('Maria', '98765432109', 'maria@punchtheclock.com', PARSEDATETIME('02-02-2020 09:18:38.00', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO time_registry (user_id, type, created_at) VALUES
  (1, 'IN', PARSEDATETIME('01-01-2020 09:10:01.00', 'dd-MM-yyyy hh:mm:ss.SS')),
  (1, 'OUT', PARSEDATETIME('01-01-2020 13:10:01.00', 'dd-MM-yyyy hh:mm:ss.SS')),
  (1, 'IN', PARSEDATETIME('01-01-2020 14:10:01.00', 'dd-MM-yyyy hh:mm:ss.SS')),
  (1, 'OUT', PARSEDATETIME('01-01-2020 18:15:06.00', 'dd-MM-yyyy hh:mm:ss.SS'));
