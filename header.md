### 接口返回格式

## 单条数据:
> 异常:
```javascript
{ 
    "status":1,
    "msg":"错误编码",
    "data":{}
}
```
> 正常:
```javascript
{
    "status":0,
    "msg":"",
    "data":{
        "id":"1",
        "name":"wb"
    }
}
```
## 多条数据:
> 异常:
```javascript
{
    "status":1,
    "msg":"错误编码",
    "data":{}
}
```
> 正常:
```javascript
{
    "status":0,
    "msg":"",
    "data":{
        "page":{
            "totalNum":4,
            "totalPage":2,
            "currentPage":1
        },
        "list":[
            {
                "id":267,
                "city":"Wuhan"
            },
            {
                "id":266,
                "city":"Nanjin"
            }
        ]
    }
}
```

### 错误码
| 解释|错误码 |
| --- | --- | 
|服务器错误|error_0001|
|参数不正确|error_0002|
|没有数据|error_0003|
|验证码不正确|error_0004|
|验证码类型不正确|error_0005|
|手机号码已存在|error_0006|
|已经关注过|error_0007|
|没有关注过|error_0008|
|已经收藏过|error_0009|
|没有收藏过|error_0010|
|不能转发自己的日志|error_0011|
|已转发过的日志不能重复转发|error_0012|
|用户被禁用，无法登录|error_0013|
|验证码超时|error_0014|
|用户不存在|error_0015|
|优惠券不存在|error_0016|
|已经领取过优惠券|error_0017|
|手机号码不存在|error_0018|
|手机号或密码错误|error_0019|
|已经点赞过|error_0020|
|没有点赞过|error_0021|
|订单不存在|error_0022|
|收货地址不存在|error_0022|