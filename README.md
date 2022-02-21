# 员工考勤系统

## 引言

### 1. 开发目的

​	便于企业管理员工的出勤情况以及方便员工进行打卡出勤的记录

### 2. 背景

​	系统名称：员工考勤系统

​	任务提出者：相关需求员工考勤系统的企业

​	开发者：俞一璇

​	用户：企业经理，部门主管，员工

### 3. 运行环境

​	带浏览器的电脑，使用Chrome浏览器为佳。

​	1G以上内存

### 4. 参考资料

​	项目需求文档，项目系统原型，项目分析原型，测试计划

### 5. 软件概述

​	该软件能够为企业管理员工进行服务，企业经理，主管，员工各自有不同的功能。经理能够管理所有部门的员工，能够修改所有用户的信息，查看所有用户的排班，出勤。还能设置临时加班功能，需全体员工都参与；部门主管主要负责该部门的排班，加班、请假的审批管理以及出勤管理。员工主要能够通过该系统查询自己的排班班次。发起请假和加班的申请，以及需要在系统内进行按时打卡。



## 一、登录与注册

### 1. 登录
- 输入工号密码进行登录
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622043742149.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 输入错误工号或密码会报错
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062204391025.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 2. 注册
- 填写注册信息
- 要求：两次输入密码一致，需要邮箱进行验证码验证
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622043940900.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
## 二、经理用户
### 1. 个人信息查看与修改
- 个人信息可以直接在该处修改，点击“更新”按钮保存
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622044250621.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

### 2. 查看企业员工信息
- 点击查看企业所有员工信息
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622044437418.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
#### 2.1 按部门查看员工
- 在下拉列表中选择需要查看的部门
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622044547118.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击查看显示该部门的所有员工
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622044638406.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

#### 2.2 更新用户
- 选中需要修改的用户信息，直接在该条输入框内更改，修改完成后点击上方的“更新选中”按钮完成修改
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622044844398.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

#### 2.3 新增用户
- 点击新增用户，跳转到用户新增的页面![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622045239551.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 输入新增用户的信息，点击“确认新增”
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622045412705.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
#### 2.4 删除用户
- 选中用户，点击上方的按钮“删除选中”，即可删除选中的用户
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622045607583.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

#### 2.5 批量导入员工信息
- 员工信息的界面点击“批量导入员工信息”按钮，弹出文件选择框
![在这里插入图片描述](https://img-blog.csdnimg.cn/202006220458177.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击文件选择框选择文件
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062204592882.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 选好后点击确认即可新增
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622045958504.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

### 3. 查看公司信息
- 点击“公司信息”，可查看公司信息
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622050123968.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

### 4. 管理班次
- 点击“班次信息”查看所有排班
![在这里插入图片描述](https://img-blog.csdnimg.cn/202006220503234.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 在上方选择`月份`，`部门`，输入`员工工号`即可查看相应的排班
- 按`月份`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622050556638.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 按`月份`，`部门`
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062205065490.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 按`工号`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622050739602.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 5. 查看出勤信息
- 同样也可以按照关键词：`月份`，`部门`，`员工工号`进行缩小查询范围，跟排班一样
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622050942336.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 6. 临时加班管理
- 同样也可以按照关键词：`月份`，`部门`，`员工工号`进行缩小查询范围，跟排班一样
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622051117167.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 可以创建全体的临时加班活动，作用于临时加班时间段内没有请假的所有员工
- 点击“创建临时加班”，弹出加班信息输入框，输入后点击确定即可添加
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622051319742.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

## 三、主管用户
- 登录用户为部门主管时若有需要批准的加班或者请假，会弹窗提醒
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622051548943.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 1. 个人信息修改
- 个人信息可以直接在该处修改，点击“更新”按钮保存
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622051701316.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

### 2. 部门信息查看
- 点击“部门信息”，查看当前部门名称，主管，电话，总员工数
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062205183563.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

### 3. 请假审批
- 进入审批界面后显示本部门所有需要审批的请假
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062205222284.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击批准，如果请假期间有排班，提醒主管可以去修改被影响的排班
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622005133169.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 显示被请假影响的排班
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622052358656.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 可以选中排班点击“删除选中”删除，或者点击“更新选中”重新规划排班
- 点击“更新排班”，输入排班信息，点击“确定”即可更新排班
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622052645193.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 3. 销假审批
- 点击进入销假界面，选中申请点击“批准”即可批准 
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622052833274.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 4. 加班审批
- 点击进入加班审批界面，选中申请点击“批准”进行批准；“驳回”进行驳回申请
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622052944756.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 5. 班次安排
- 点击进入班次安排界面，选中申请点击“批准”进行批准；“驳回”进行驳回申请
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622053051296.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 可以选择`工号`和`月份`进行搜索
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622053243679.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 选中排班点击“删除选中”即可删除该排班
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622053336776.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 选中某一个排班，点击“更新选中”会弹出修改框，可以在上面修改排班信息
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622053454947.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击“增加排班”，上方弹出排班信息输入框，在上方输入排班信息即可
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622053812733.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击“导出当前”即导出当前显示的排班信息到一个xls文件中
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622053926313.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击“批量添加”，跳转到批量添加页面
- 多选员工
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622054033488.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 连续选择天数， 点击添加后，所有选择的的员工都会在这连续的几天中排相同的班 
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622054059256.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击“导入排班”，弹出文件选择框
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062205421165.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 选择文件上传，即可添加相应的排班
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622054347474.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

### 6. 出勤信息
- 点击“出勤信息进入到“出勤信息”页面，可以按照`工号`，`月份`进行查找
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622054546242.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

## 四、员工用户
### 1. 个人信息与照片上传
- 个人信息可直接在上面修改，新用户必须上传照片到服务器，以进行打卡：点击文件选择框，选择照片之后点击“上传照片”将照片上传到后台
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622054915405.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

### 2. 查看本人班次安排
- 点击查看本人所有班次安排，可以按月份进行检索
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622055023560.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 3. 查看本人出勤信息
- 点击查看本人所有出勤信息，可以按月份进行检索
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622055104116.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 4. 请假申请
- 点击进入请假申请，可以看到自己申请请假的审批状态，选中后点击“取消申请”即可取消
- 对“已批准”的请假，选中后点击“销假”即可申请销假
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622055214175.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击“申请请假”，弹出申请请假输入框，输入请假的时间理由，点击确定即可
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622055425651.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
### 5. 加班申请
- 点击进入加班申请，可以看到自己申请加班的审批状态，选中后点击“取消申请”即可取消
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062205563347.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击“加班申请”，弹出加班申请输入框，输入加班的时间理由，点击确定即可
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622055655235.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

### 6. 员工人脸识别打卡
- 进入到打卡页面，若用户设置开启提醒，会在进入该页面时提醒员工的下一条排班的上下班时间，可以点击选框关闭通知提醒
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622055855896.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 如果超过下班时间还没打卡，会提醒员工进行加班申请
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622060630552.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击“开始打卡”，再点击开启摄像头，即可开启摄像头
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622060107859.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)
- 点击拍照打卡，若识别失败，则提示“不是本人，打卡失败”
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622060342586.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

- 若人脸识别成功，提示“打卡成功”，
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622060442742.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQzNDEwMDQ4,size_16,color_FFFFFF,t_70)

