
# 打包部署

## 编译

mvn clean package

## 打包docker

docker build -t data-mining-1.0.0 .

## 部署

docker run -d --name data-mining \
       -p 8080:8080 \
       -e DB_URL=**** \
       -e DB_USERNAME=**** \
       -e DB_PASSWORD=**** \
       data-mining:1.0.0
