
# �������

## ����

mvn clean package

## ���docker

docker build -t data-mining-1.0.0 .

## ����

docker run -d --name data-mining \
       -p 8080:8080 \
       -e DB_URL=**** \
       -e DB_USERNAME=**** \
       -e DB_PASSWORD=**** \
       data-mining:1.0.0
