# 配置SSH
1. 
   

# 账户和代理

1. 

# 基础

## 获得仓库

### 配置SSH

1. 生成SSH（"-C"作为识别密钥的注释，可随意输入）
   `ssh-keygen -t rsa -b 4096 -C "email@example.com_"`

2. `.pub`文件添加到托管平台

3. 配置`config`文件（可选）

   1. 位置：`**~/.ssh/config**`

   2. 常用配置：

      1. `HOST`标识配置区段，可以用通配符`*`表示0~n个非空白符，`？`表示1个非空白符，`！`表示例外通配
      1. `IdentityFile`指定密钥认证使用的私钥文件路径。默认为`~/.ssh/id_rsa`

   3. 示例

      ```cmd
      # github
      Host github.com
      IdentityFile ~/.ssh/id_rsa_github
      
      # gitee
      Host gitee.com
      IdentityFile ~/.ssh/id_rsa_gitee
      ```

4. 测试SSH连接`ssh -T git@github.com`

### 账户和代理

1. 全局/本地`user.name`和`user.email`
   1. 清空：`git config --global --unset user.name`
   1. 设置：`git config --local user.email xxx@xxx.com`
2. 代理ssh：
   1. 需要`connect.exe`（一般在git bash里包含）
   1. `~/.ssh/config`中（可以在首行）插入`ProxyCommand connect -S 127.0.0.1:7890  %h %p`
3. 检查配置`git config --list`



- 克隆远程仓库时，自定义本地仓库名字`git clone [git_url] [dir_name]`

## 记录更新到仓库

### 检查状态

- `git status [-s]|[\-\-short]`得到格式更紧凑的输出

  ```cmd
  $ git status -s
   M README
  MM Rakefile
  A  lib/git.rb
  M  lib/simplegit.rb
  ?? LICENSE.txt
  # README 文件在工作区已修改但尚未暂存
  # lib/simplegit.rb 文件已修改且已暂存
  # Rakefile 文件已修改，暂存后又作了修改
  ```

  - ？？新添加未跟踪
  - 新添加到暂存区的文件前有A标记
  - 修改过的文件用M标记
  - 输出中左侧有两栏，**左栏是暂存区的状态**，**右栏是工作区**状态

### 忽略文件

#### 格式规范

- 所有空行，或以`#`开头的行，都会被git忽略
- 可以使用标准glob模式匹配，递归应用在整个工作区
- 匹配模式以`/`开头防止递归
- 匹配模式以`/`结尾指定目录
- 要忽略指定模式以外的文件或目录，在模式前加`!`取反

glob模式：shell使用的简化了的正则表达式

- 星号（*）匹配零个或多个任意字符
- [abc] 匹配任何一个列在方括号中的字符 （这个例子要么匹配一个 a，要么匹配一个 b，要么匹配一个 c）
-  问号（?）只匹配一个任意字符
- 如果在方括号中使用短划线分隔两个字符， 表示所有在这两个字符范围内的都可以匹配（比如 [0-9] 表示匹配所有 0 到 9 的数字）
- 使用两个星号（*\*）表示匹配任意中间目录，比如 a/**/z 可以匹配 a/z 、 a/b/z 或 a/b/c/z 等

**子目录中的 .gitignore文件中的规则只作用于它所在的目录中**

### 查看已暂存未暂存的更改

- `git diff` 查看工作区和暂存区的差异
- `git diff --[staged]|[cached]` 暂存区和版本库的差异

### 跳过使用暂存区

`git commit -am "xxx"` 自动把所有**已跟踪的文件**暂存并提交,跳过`git add`

### 移除文件

`git rm file_name` 删除工作区文件，并且将这次删除放入暂存区(**要删除的文件**是没有修改过的，就是说**和当前版本库文件的内容相同**)

`git rm -f file_name`删除工作区文件，并且将这次删除放入暂存区(**要删除文件**已修改,**和版本库文件不同**)

`git rm --cached file_name`**从暂存区删除,不从工作目录删除**(常用于后期加入.gitignore文件)

### 移动文件

`git mv old_file_name new_file_name`等于运行了下面三条命令

```
mv old_file_name new_file_name
git rm old_file_name
git add new_file_name
```

## 查看提交历史

- `git log`查看提交历史
- `git reflog`查看本地所有命令历史（可用于使用`git reset --hard`后，在本地找回消失的版本的commit id

## 撤销操作

