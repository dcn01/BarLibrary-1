# BarLibrary
使用kotlin造的一个小小的轮子。
一边学习Kotlin一边利刚学的kotlin造个一个小小的轮子，该轮子就是设置StatusBar和NavigationBar的背景颜色/透明和图标颜色,没错就是所谓的浸入式。

### 怎么使用?

   ```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //设置状态栏和虚拟键背景透明,若设置成其他颜色就根据需要给
        //statusBarColor/navigationBarColor/statusIconColor传值就可以了
        BarUtil.setStatusAndNavigationBar(this)
        //BarUtil.setStatusAndNavigationBar(this,statusBarColor = Color.argb(100,10,10,255),statusIconColor = false)
    }
   ```
   
### 感谢 

[StatusBarUtil](https://github.com/laobie/StatusBarUtil)
和各位大侠的帖子

### 不足

1. 4.4的NavigationBar(虚拟键)不道怎么设置背景颜色
2. 只能设置状态栏的图标颜色(黑色/白色)
3. 个人感觉这还是走的java的思想，用kotlin的写的。用kotlin的思想会是什么样子的呢？

### 请各位大侠不吝赐教

### License

 Copyright (C) 2017
 
  by zhuyk
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0
 
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
