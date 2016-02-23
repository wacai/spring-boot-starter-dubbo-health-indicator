# 实现对依赖dubbo服务的可用性检测

## How to use?
### 依赖

```xml
<dependency>
    <groupId>com.wacai</groupId>
    <artifactId>spring-boot-starter-dubbo-health-indicator</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 结果展示
通过jmx 访问时heathcheck 时，可以看到类似下面的结果:
````
"dubbo":{
{
    "newHolidaysService": {
        "interface": "com.wacai.service.holidays.service.HolidaysService",
        "status": "UP"
    },
    "areaService": {
        "interface": "com.wac.common.area.share.AreaService",
        "status": "UP"
    },
    "bankService": {
        "interface": "com.wac.common.bank.share.BankService",
        "status": "UP"
    },
    "status": "UP"
}
````

"UP" 表示所有服务都available。  只要有一个服务不可访问到，最外层的status 就是"DOWN"