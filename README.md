# ENXHome - Teste Recrutamento Desenvolvedor Java Junior

O plugin ENXHome é um sistema simples de homes com conexão a um banco de dados MySQL.

## Arquivo de Configuração:
O arquivo de configuração do plugin é o `config.yml` e está localizado na pasta `ENXHome/config.yml`.
```yaml
database:
  host: 127.0.0.1
  port: 3006
  username: root
  password: pass
  database: server

cooldown-teleport: 5 # Cooldown in seconds

see-particles: true # Show particles when teleporting
particle-type: "HEART" # Particle type
sound-type: "ENTITY_PLAYER_LEVELUP" # Sound when teleporting
```
É possível configurar se deseja ver as partículas e o som ao teleportar, além de configurar o tipo de partícula e som.

## Comandos:
- `/sethome <name>` - Define a home no local atual
- `/home <name>` - Teleporta para a home
- `/delhome <name>` - Deleta a home
