package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
public class Controller {

	Map<String, List<UserDetails>> map = new HashMap<>();

	@GetMapping("/")
	public String showHome(Model model) {
		return "index";
	}

	@GetMapping("/checkout")
	public String makePayment(Model model) {
		UserDetails user = new UserDetails();
		model.addAttribute("checkout", user);
		return "checkout";
	}

	@PostMapping("/checkout")
	public String onSuccess(@ModelAttribute UserDetails user, Model model) {
		model.addAttribute("checkout", user);

		if (map.containsKey(String.valueOf(user.getUserId()))) {
			List<UserDetails> list = map.get(String.valueOf(user.getUserId()));
			list.add(user);
			map.put(String.valueOf(user.getUserId()), list);
		} else {
			List<UserDetails> list = new ArrayList<>();
			list.add(user);
			map.put(String.valueOf(user.getUserId()), list);
		}

		return "successPage";
	}

	@GetMapping("/getUserId")
	public String getTransactions(Model model) {
		GetUserId on = new GetUserId();
		model.addAttribute("getUserId", on);
		return "getUserId";
	}

	@PostMapping("/getUserId")
	public String getTransactionDetails(@ModelAttribute GetUserId userId, Model model) {

		if (map.containsKey(userId.getId())) {
			model.addAttribute("getUserIdList", map.get(userId.getId()));
			return "validUser";
		} else
			return "invalidUser";

	}

}
