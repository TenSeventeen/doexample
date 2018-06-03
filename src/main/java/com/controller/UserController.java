package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.entity.User;

@Controller
@SessionAttributes(value = { "sessionTest" })
public class UserController {

	//国际化+遍历用户信息
	@RequestMapping("/bl")
	public String bl(HttpServletRequest request, String lang, Model model) {
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) request.getSession().getAttribute("ls");
		model.addAttribute("allUser", list);
		if (lang != null) {
			if (lang.equals("zh")) {
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
						new Locale("zh", "CN"));
			} else {
				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
						new Locale("en", "US"));
			}
		}
		return "bluser";
	}

	//跳转到添加页面
	@RequestMapping("/toInsert")
	public String toInsert(HttpServletRequest request, String lang) {
		return "insert";
	}

	//添加
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(User user, Model model, HttpServletRequest request) {

		List<User> list = new ArrayList<>();

		list.add(user);
		if (request.getSession().getAttribute("ls") != null) {
			list.addAll((List<User>) request.getSession().getAttribute("ls"));
		}
		request.getSession().setAttribute("ls", list);
		model.addAttribute("allUser", list);

		return "bluser";
	}

	//根据id在session中查询单个用户信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/findId")
	public String findId(Model model, HttpServletRequest request, User user, Integer id) {
		List<User> list = new ArrayList<>();

		list = (List<User>) request.getSession().getAttribute("ls");
		Iterator<User> it = list.iterator();
		while (it.hasNext()) {
			user = it.next();
			if (user.getId() == id) {
				model.addAttribute("user", user);
			}
		}
		return "findId";
	}

	//更新用户信息
	@SuppressWarnings("unchecked")
	@RequestMapping("update")
	public String update(User user, Model model, HttpServletRequest request) {
		//System.out.println(user);
		List<User> list = new ArrayList<>();
		list = (List<User>) request.getSession().getAttribute("ls");
		for (User user2 : list) {
			if(user2.getId()==user.getId()) {
				list.remove(user2);
				list.add(user);
			}
		}
		request.getSession().setAttribute("ls", list);
		model.addAttribute("allUser", list);
		return "bluser";
	}
	//根据id删除用户信息
	@SuppressWarnings("unchecked")
	@RequestMapping("delId")
	public String delId(User user,Model model, HttpServletRequest request, Integer id) {
		List<User> list = new LinkedList<>();
		list = (List<User>) request.getSession().getAttribute("ls");
		
		for (User user2 : list) {
			if(user2.getId()==id) {
				list.remove(user2);
			}
		}
		model.addAttribute("allUser", list);
		
		return "bluser";
		
	}
}