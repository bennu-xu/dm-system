
# 登录

## From登录：POST /login

登录页面：login.html  页面地址：GET /login  登录出错：GET /login?error

## 获取当前登录用户信息：GET /userinfo
Response:
```
{
    "username": "账号",
    "name": "姓名",
    "branch": "所属机构编码",
    "branchName": "机构名称"
}
```

# 血液采集统计

## 按年统计：GET /collection/year-rpt?year=yyyy-MM-dd

接口逻辑：统计指定年份及其前两年的数据

Response:
* status:200 成功
* status:400 查询参数不对
* status:500 内部异常

成功返回数据：
```
[{
    "time": "年份",
    "times": 0,             //采集人次
    "timesPercent": 0,      //涨幅 %
    "conversion": 0,        //采集量
    "conversionPercent": 0  //涨幅 %
}]
```

## 按年和地点统计：GET /collection/place-year-rpt?year=yyyy-MM-dd

接口逻辑：统计指定年份及其前两年的数据
           
Response:
* status:200 成功
* status:400 查询参数不对
* status:500 内部异常

成功返回数据：
```
[{
    "time": "年份",
    "place": "采集地址",
    "times": 0,             //采集人次
    "timesPercent": 0,      //涨幅 %
    "conversion": 0,        //采集量
    "conversionPercent": 0  //涨幅 %
}]
```