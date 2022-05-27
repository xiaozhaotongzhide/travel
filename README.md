突然想起刚开始学习JavaEE的跟着视频做了一个黑马旅游网的小项目,项目本身没啥特点,之后自己部署到了服务器的tomcat上,但是由于里面用到了redis缓存,他的配置文件一直报错,最近寻思这用Spring Boot把他集成进去
这是我们现在的目录,新建一个Spring Boot的项目
![在这里插入图片描述](https://img-blog.csdnimg.cn/f655ecab42e8445f8daf0c4ec7afdc43.png)
改吧改吧,把原来的dao层用mybatis-plus
通过一上午的努力
![在这里插入图片描述](https://img-blog.csdnimg.cn/8cc99d5a043542e99b3a284151597fb8.png)
现在已经初见雏形
在此期间我遇到了一个问题,由于之前是javaee的项目用的是Servlet
所以在启动类上加上了
```
@ServletComponentScan(basePackages = "com.wangbing.springboottravel.web")
```
开启mvc对于原生Servlet的支持
现在又遇到问题了,被@Mapper注解的Dao通过 @Autowired注入但是并没有注入
这时考虑到@Mapper注解的应该是有实现类的所以自动注入的话会报错,于是我用到了
extends ServiceImpl<UserDao,User>
来解决这个问题
记得要在他的继承类上
extends IService<User>
还是不行后来深入的了解才发现,原来是原项目中使用
```
        //3.调用service查询
        UserService service = new UserServiceImpl();
 ```
 这种方式获取Uservice而不是交给spring管理
 之后经过全部把new的方式改为@Autowired完成了这个项目的重写
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/ab794e8173fa41d6ae0aa754a6376112.png)
[新版黑马旅游网](https://github.com/xiaozhaotongzhide/SpringBoot-Travel)
[旧版黑马旅游网](https://github.com/xiaozhaotongzhide/travel)
