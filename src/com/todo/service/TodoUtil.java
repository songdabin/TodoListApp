package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "---------- Create item Section ----------\n"
				+ "Enter the title of the item to create: ");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("Title can't be duplicate. please change the title.");
			return;
		}
		//sc.nextLine();
		System.out.print("Enter the description: ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("Item added.");
	}

	public static void deleteItem(TodoList l) {
		System.out.print("\n"
				+ "---------- Delete Item Section ----------\n"
				+ "Enter the title of the item to remove: ");
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("Item deleted.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.print("\n"
				+ "---------- Edit Item Section ----------\n"
				+ "Enter the title of the item to update: ");
		
		String title = sc.nextLine().trim();

		if (!l.isDuplicate(title)) {
			System.out.println("Title doesn't exist.");
			return;
		}

		System.out.print("Enter the new title of the item: ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("Title can't be duplicate.");
			return;
		}
		
		sc.nextLine();
		System.out.print("Enter the new description: ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("Item updated.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("\n"
				+ "---------- List all item ----------");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.write("practical project" + "##" + "submit lab 1" + "##" + "2021/09/23 23:28:01\n");
			w.close();
			
			System.out.println("File saved.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String oneline;
			while((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				
				System.out.println("title: " + title + " | desc: " + desc + " | current_date: " + current_date);
			}
			br.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("File Not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
