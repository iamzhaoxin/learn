package com.example.ssm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ssm.controller.utils.R;
import com.example.ssm.domain.User;
import com.example.ssm.service.IUserService;
import com.example.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/15 18:46
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping
    public R getAll(){
        return new R(true,iUserService.list());
    }

    @PostMapping
    // notice @RequestBody用于接收json数据, @RequestParam接收application/x-www-form-urlencoded键值对参数
    public R save(@RequestBody User user){
        if(iUserService.count(new QueryWrapper<User>().eq("id",user.getId()))>0){
            return new R(true,"id已存在");
        }
        return new R(true,iUserService.save(user));
    }

    //http://localhost:80/1
    // notice Mapping加上{id} 接收参数时加上@PathVariable从路径中接收参数
    @GetMapping("{id}")
    public R getById(@PathVariable Integer id){
        return new R(true,iUserService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    public R getById(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        return new R(true,iUserService.getPage(currentPage, pageSize));
    }

}
