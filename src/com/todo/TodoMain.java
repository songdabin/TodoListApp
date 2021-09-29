package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todoList.txt");
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "find":
				String keyword = sc.next();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate_keyword = sc.next();
				TodoUtil.findCate(l, cate_keyword);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("List name by ascending order.");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("List name by descending order.");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				System.out.println("List by date.");
				isList = true;
				break;
				
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				System.out.println("List date by descending order.");
				isList = true;
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("Please enter one of the above mentioned command. (If you need help, use 'help' command.)");
				break;
			}
			
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		TodoUtil.saveList(l, "todoList.txt");
	}
}
