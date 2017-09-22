# ADA-DEV projects

## 1. 鲸语

目前初步定在下周末（10.1）举行我们的第一次研讨会，基本上频率会1~2周一次。

目前鲸语作为多伦多本地的资讯应用，已经积累了接近3万多篇优质攻略.

这个研讨组的目的，也是【基于这些优质数据的基础上探索开发下一阶段的功能】.

预计会在下周中准备好鲸语的攻略文本，在此之前可以先参考我基于`1G reddit comments`来做用户兴趣推荐的repo和笔记来让自己更熟悉相关的概念和开发过程。
在研讨会上可以和大家分享自己的新想法和灵感。

[GitHub - chocoluffy/redditQA: Explore some interesting NLP experiments with reddit comments data.](https://github.com/chocoluffy/redditQA)

同时在app的UI和功能上，也有很多人给出了很多很棒的建议，也希望大家各自有记录，可以也在研讨会上提出来~（尤其是lucas对活动部分的建议）

------

## 2. Project V

U of T有偿帮忙平台(run on android and ios).

### 技术栈
1. Learn Javascript
2. Front End
    - [ReactNative](https://facebook.github.io/react-native/docs/tutorial.html) 
    (Need to learn [React](https://facebook.github.io/react/tutorial/tutorial.html) first)
    - [React-Redux](http://redux.js.org/docs/basics/)
    - [One Javascript testing frameworks(**optional**)]
3. Back End
    - ExpressJS
    - Mongoose(MongoDB)
4. Git/Github

React的学习只要看下官方的tutorial即可，link在上面，BackEnd和Redux可以在项目中学习，很简单的。

了解javascript后，需要看下最新的[javascript es6 feature](https://github.com/lukehoban/es6features),主要了解arrow function的用法(easy!).

### 当前App展示
[![20170922180956.png](https://s26.postimg.org/xelkwlseh/20170922180956.png)](https://postimg.org/image/d7854aux1/)
[![20170922181000.png](https://s26.postimg.org/4b6jruj2h/20170922181000.png)](https://postimg.org/image/4b6jruj2d/)
[![20170922181441.png](https://s26.postimg.org/ud8krvjex/20170922181441.png)](https://postimg.org/image/ymdau1mo5/)
[![20170922181003.png](https://s26.postimg.org/d6bzg3x8p/20170922181003.png)](https://postimg.org/image/gpxx5wzyd/)

------

## 3. Toronto.University
Toronto.University 是整合学校资源的平台。我们希望用手机，平板，和电脑，都能优雅的处理学校有关的事务。

现在网站底层架构(**Angular V4**)，设计组件(material components)和邮件服务已经建好，能够根据building code在google map定位和根据course code查询course info和实时waitlist。未来会根据需求加入各种简单易用的功能。

[![unnamed.png](https://s26.postimg.org/7p6cp0ky1/unnamed.png)](https://postimg.org/image/nnf2f5f5x/)



没有技术要求，根据兴趣和能力参与实现content，design，或者logic。如果你喜欢学习炫酷的最新科技，实现小而美的实用功能，参与面试会被问到的project，欢迎加入！

更多细节请访问 https://toronto.university/about

------

## 4. Unity Game

我们想做一个Unity的2D或者3D的游戏，Idea还在讨论中。

IDE：Unity 5 https://unity3d.com/

语言：C#

或者如果会 画图/三维建模 （也就是美工）当然最好（如果会这个的话就不需要会C#了）

### 自学教程：
下面四个看完三个（四个最好）
- https://unity3d.com/learn/tutorials/projects/roll-ball-tutorial
- https://unity3d.com/learn/tutorials/projects/space-shooter-tutorial
- https://unity3d.com/learn/tutorials/projects/survival-shooter-tutorial
- https://unity3d.com/learn/tutorials/projects/tanks-tutorial 或者这个看完前面四章（五章最好）
- https://www.udemy.com/unitycourse/

------

## 5. Smart Timetable

### Team members should learning following frameworks/languages.
Team member can [choose](/chenj209/timetable/wiki/Team-Member-Info) to focus on either front end or backend or both.
- Front End
  - AngularJS 4
  - Typescript
  - Basic CSS/HTML/JS
  - One Javascript testing frameworks(**optional**)
- Back End
  - ExpressJS
  - Mongoose(MongoDB)
  - Xpath expression(**optional**)
  - Basic Javascript language features
  - Javascript ES6, ES8 language features
- Git/Github
- Markdown
- Docker(**optional**)

#### Javascript Code Style
- Generally reference to code style specifications on [W3C School](https://www.w3schools.com/js/js_conventions.asp).
- Use ES6 style Javascript. Here is a [tutorial](https://webapplog.com/es6/) for some of useful ES6 features.
- Use ES8 style [async/await](https://hackernoon.com/javascript-es8-introducing-async-await-functions-7a471ec7de8a) to handle callbacks.
- Do add semicolons(;) at the end of each statement.
- Use 4 spaces to indent code(Please enable soft-indent in your editor/IDE).
- [WebStorm](https://www.jetbrains.com/webstorm/) is recommended. Activation Key can be instantly applied using utoronto email.

------

## ADACORN

### 技术栈
项目中存在多种现代化技术栈，已有的代码基于时下比较流行的MEAN Stack (MongoDB, Express.js, Angular, Node.js)

#### 前端
- [Angular V4](https://angular.io)
- [Semantic UI](https://semantic-ui.com)
- [ng2-semantic-ui](https://edcarroll.github.io/ng2-semantic-ui/#/getting-started) 一个基于Angular的，剔除JQuery的Semantic UI库
- Angular使用[Typescript](https://www.typescriptlang.org)
- 其他小型库...

#### 后端
- [Node.js](https://nodejs.org/en/)
- [Express.js](http://expressjs.com)
- [MongoDB](https://docs.mongodb.com/?_ga=2.158790522.1370804634.1506121013-809636485.1478106835)
- [Mongoose](http://mongoosejs.com)

*目前团队正在考虑从MongoDB迁移到MySQL，具体结果需要等待第一次组内会议结束。

### 推荐文档

#### 前端
- [Angular官方教程《Tour of Heroes》](https://angular.io/tutorial) （必须）
- [MEAN App with Angular 2 and the Angular CLI](https://scotch.io/tutorials/mean-app-with-angular-2-and-the-angular-cli) （必须）

#### 后端
- 309课上Slides
