### Запуск:
#### Вариант 1:
```shell
mvn clean package
cd target
java -jar mab2-helper-{version}.jar
```

#### Вариант 2:
```shell
mvn clean package
cd compose
docker compose -f .\docker-compose.local-db.yml up
```

### Подгрузка данных из примера:
Заходим на
http://localhost:8080/

Выбираем пункт "2: upload city info from json file".

Выбираем файл `./examples/city-info-list.json`

Нажимаем `Upload`