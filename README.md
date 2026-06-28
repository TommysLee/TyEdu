<h1 align="center" style="font-weight: bold;">K12 精准教学系统</h1>
<h4 align="center">让因材施教从理念走向实践 —— 基于数据驱动的个性化学习平台</h4>
<p align="center">
    <img alt="Build Passing" src="https://img.shields.io/badge/build-passing-brightgreen.svg">
    <img alt="TyEdu v1.0" src="https://img.shields.io/badge/TyEdu-v1.0-brightgreen.svg?logo=github">
    <img alt="Apache License" src="https://img.shields.io/badge/license-apache-brightgreen.svg">
</p>

**K12精准教学系统** 是一款面向K12教育阶段的智能化教学辅助平台，致力于解决大班教学背景下**“因材施教”难以落地**的现实困境。系统通过深度数据采集与智能分析，为每一位学生绘制精准的学情画像，帮助教师、学生和家长三方共同实现“**精准学**、**精准补**、**精准练**”，彻底告别低效的题海战术。

我们相信，真正的教育不是让所有孩子追逐同一个终点，而是帮助每个孩子找到最适合自己的成长路径。

## 🎯 核心功能

#### 📚 题库管理

- 支持多学科、多年级试题的分类管理
- 试题标签化（知识点、难度、题型、章节标）
- 智能组卷与手动组卷双模式

#### 📝 考试与考试分析

- 在线考试与线下成绩导入双支持
- 多维度考试报告
- 知识点掌握度分析，直观定位薄弱环节

#### ❌ 我的错题集

- 自动收录考试与练习中的错题
- 支持按知识点、时间、学科筛选复习
- 错题变式推荐，举一反三巩固训练

#### 📊 学情分析

- 个人学情总览：各学科能力雷达图、进步曲线
- 偏科预警与归因分析（基础不牢 / 当前知识未掌握 / 学习习惯等）
- 班级/年级对比分析，辅助教师调整教学策略

#### 💡 学习技巧库

- 分学科、分知识点的学习方法指导
- 基于学情智能推送针对性学习建议
- 名师微课与经典例题解析

## 🧠 产品价值

| 角色           | 核心获益                                                     |
| :------------- | :----------------------------------------------------------- |
| **学生**       | 告别盲目刷题，只练该练的题；清晰了解自己的强弱项，学习更有方向感和成就感 |
| **教师**       | 实时掌握全班学情分布，精准调整教学进度与重难点，实现“以学定教” |
| **家长**       | 获得可视化学情报告，了解孩子偏科详情，为选择补习班提供数据支撑，告别“盲报” |
| **教育管理者** | 宏观把握年级/学校整体学情，优化教研资源配置                  |

> **典型案例**：当学生在某个科目长期难以提升时，系统会智能诊断是“当前知识点未掌握”还是“前置基础存在断层”。若为后者，系统会推荐优先补足基础内容，避免“跟跑”式学习导致的厌学情绪，真正做到循序渐进、因材施教。

## 🛠 技术栈

> 基于 **[TyFast](https://github.com/TommysLee/TyFast)** 开源项目开发。

- **数据库**：SQLite

- 使用 **Shawl** 将应用安装为Windows系统服务，便于开机即可使用。、
- 试题推荐核心基础引擎：**Apache Lucene**

## UI界面

![](https://raw.githubusercontent.com/TommysLee/TyStudy/refs/heads/main/src/main/resources/assets/ty-edu/home.jpg)

![](https://raw.githubusercontent.com/TommysLee/TyStudy/refs/heads/main/src/main/resources/assets/ty-edu/exam_analysis.jpg)

## 项目源码结构

![](https://raw.githubusercontent.com/TommysLee/TyStudy/refs/heads/main/src/main/resources/assets/ty-edu/struct.jpg)

**说明**：

- 项目是一个标准的多模块Maven项目，技术架构详情可参考开源项目**[TyFast](https://github.com/TommysLee/TyFast)** 
- 部署时：
  - 若你想复用已有的SQLite数据库，可以执行 `init.sql` 脚本进行数据库初始化；
  - 使用本项目提供的 `data.db` 数据库，可直接Run项目，记住：`data.db` 默认放置目录为：`${user.home}/tyedu`

------

> **教育不是注满一桶水，而是点燃一把火。** —— 让数据照亮每一把火的燃烧方向。